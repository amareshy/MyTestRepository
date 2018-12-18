package com.cassandradb.client.client.datasource;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Class ensuring that any DataStax Cluster class is properly closed, before a
 * new one is assigned. This class is thread-safe, just like the DataStax
 * Cluster class. The DataStax Cluster must be open while all connections
 * (Sessions) to Cassandra is open, but if a new Cluster is created, any old
 * ones need to be closed, or else you will leak a file descriptor.
 * <p>
 * Be aware, however, that the get() method will return null in case the Cluster
 * has not been connected. There is no synchronization between the isConnected()
 * and get() method calls, so your application should not rely on get()
 * returning non-null in case isConnected() returns true.
 * <p>
 * In other words, code like this:
 *
 * <pre>
 * if (clusterHolder.isConnected()) {
 * 	clusterHolder.get().getMetadata(); // Possible NPE in case setCluster(null) is called by other thread
 * }
 * </pre>
 * <p>
 * might still fail, in case your other threads decides to disconnect the
 * Cluster. Your application should be designed to prevent this behaviour (e.g.
 * by cancelling worker threads before shutting down the Cluster)
 */
public class ClusterHolder {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterHolder.class);
    private final AtomicReference<Cluster> myCluster;

    public ClusterHolder() {
        myCluster = new AtomicReference<>(); // New Atomic reference with null initial value of Cluster
    }

    public void setCluster(Cluster cluster) {
        LOG.debug("Set cluster in Cluster Holder: {}", cluster);

        Cluster previousValue = myCluster.getAndSet(cluster);
        if (previousValue != null) {
            LOG.debug("ClusterHolder closing old cluster: {}", previousValue);
            previousValue.close();
        }
    }

    /**
     * Get the  cluster value or null if no cluster has been connected.
     *
     * @return Cluster object from C*.
     */
    public Cluster getCluster() {
        return myCluster.get();
    }


    /**
     * If there is a connected C* cluster then create a session by connecting to it, otherwise return null.
     */
    public Session connect() {
        Cluster cluster = myCluster.get();
        if (cluster != null && !cluster.isClosed()) {
            return cluster.connect();
        }
        return null;
    }

    /**
     * Return whether or not the Cluster connection has been made.
     */
    public boolean isConnected() {
        return myCluster.get() != null;
    }

    /**
     * Return whether or not the Cluster is connected and not closed.
     */
    public boolean isAvailable() {
        Cluster cluster = myCluster.get();
        return cluster != null && !cluster.isClosed();
    }

}
