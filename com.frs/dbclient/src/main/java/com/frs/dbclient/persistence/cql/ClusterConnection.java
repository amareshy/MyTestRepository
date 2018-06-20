package com.frs.dbclient.persistence.cql;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Host.StateListener;
import com.datastax.driver.core.PerHostPercentileTracker;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.ServerSideTimestampGenerator;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.exceptions.AuthenticationException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.RetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.frs.dbservice.exceptions.UnableToProcessException;

/**
 * Class to create and modify databases and tables in C*.
 */
public class ClusterConnection implements StateListener, AutoCloseable {

	private final Logger LOG = LoggerFactory.getLogger(ClusterConnection.class);

	private final ClusterHolder myCluster = new ClusterHolder();
	private final Set<Host> myUpHosts;
	private final DBClusterConnectionProperties myDBClusterConnectionProperties = new DBClusterConnectionProperties();
	private String myLocalDataCenter;
	private Session mySession = null;

	private Exception myClusterConnectionException = null;

	public ClusterConnection() {
		myUpHosts = Collections.synchronizedSet(new HashSet<Host>());
	}

	/**
	 * Method to build the cluster with the configured set of initial contact
	 * points. Reconnects with new properties if configuration has been changed or
	 * just connects if no connection already exists.
	 *
	 * @param properties
	 *            The properties contains the contact points to the database
	 *            cluster.
	 */
	public void connectCluster() {

		// Check whether Cluster is available
		if (!myCluster.isAvailable()) {
			createClusterConnection();
		}
	}

	/**
	 * Method to build the cluster with the configured set of initial contact points
	 * and policies.
	 *
	 * @param properties
	 *            The properties contains the contact points to the database
	 *            cluster.
	 */
	private void createClusterConnection() {
		/* Get our settings from the properties passed in */
		myDBClusterConnectionProperties.apply();

		LoadBalancingPolicy loadBalancingPolicy = setLoadBalancingPolicy();

		RetryPolicy retryPolicy = DefaultRetryPolicy.INSTANCE;

		QueryOptions options = new QueryOptions();
		options.setRefreshSchemaIntervalMillis(0);
		options.setRefreshNodeIntervalMillis(500);
		options.setRefreshNodeListIntervalMillis(500);
		options.setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM);
		options.setSerialConsistencyLevel(ConsistencyLevel.LOCAL_SERIAL);

		Builder clusterBuilder = getBasicClusterBuilder();

		PerHostPercentileTracker percentileTracker = PerHostPercentileTracker
				.builder(SocketOptions.DEFAULT_READ_TIMEOUT_MILLIS).build();

		if (clusterBuilder.getContactPoints().isEmpty()) {
			throw new IllegalArgumentException("Need at least one host to connect to");
		}

		int port = myDBClusterConnectionProperties.getMyContactPointPort();
		LOG.info("Connecting - host: {}, port: {}", myDBClusterConnectionProperties.getMyContactPointAddresses(), port);
		clusterBuilder.withPort(port).withQueryOptions(options).withLoadBalancingPolicy(loadBalancingPolicy)
				.withReconnectionPolicy(new ExponentialReconnectionPolicy(1000, 20000)).withRetryPolicy(retryPolicy)
				.withTimestampGenerator(ServerSideTimestampGenerator.INSTANCE).withProtocolVersion(ProtocolVersion.V3)
				.withCredentials(myDBClusterConnectionProperties.getMyServerUsername(),
						myDBClusterConnectionProperties.getMyServerPassword());

		// Will close down previous cluster object and store the new one
		myCluster.setCluster(clusterBuilder.build());
		myCluster.getCluster().register(percentileTracker);
		myCluster.getCluster().register(this);
		myCluster.getCluster().register(QueryLogger.builder().withConstantThreshold(50).build());
		mySession = myCluster.getCluster().newSession();
		LOG.info("Cluster connection created with protocol version {}", ProtocolVersion.V3);

	}

	private LoadBalancingPolicy setLoadBalancingPolicy() {

		return new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().build(), false);
	}

	private Builder getBasicClusterBuilder() {
		Set<String> addresses = myDBClusterConnectionProperties.getMyContactPointAddresses();
		Builder builder = Cluster.builder();

		addContactPointsTo(builder, addresses);
		return builder;
	}

	private void addContactPointsTo(Builder builder, Set<String> addresses) {
		LOG.debug("Number of hosts in contact point list: {}", addresses.size());
		for (String host : addresses) {
			builder.addContactPoint(host);
			LOG.debug("Added host {} to contact points", host);
		}
	}

	@Override
	public void close() throws Exception {

	}

	@Override
	public void onAdd(Host host) {

	}

	@Override
	public void onDown(Host host) {

	}

	@Override
	public void onRemove(Host host) {

	}

	@Override
	public void onUp(Host host) {

	}

	@Override
	public void onRegister(Cluster cluster) {

	}

	@Override
	public void onUnregister(Cluster cluster) {

	}

	/**
	 * Method to check if the cluster has been initialized.
	 * 
	 * @return true if any of the initial contact points has been contacted
	 *         otherwise false.
	 */
	public boolean isInitialized() {
		boolean answer = false;
		if (myCluster.isAvailable()) {
			try {
				myCluster.getCluster().init();
				answer = true;
			} catch (NoHostAvailableException | AuthenticationException | IllegalStateException e) {
				LOG.warn("Cluster is not initialized!", e);
				myClusterConnectionException = e;
			}
		}
		return answer;
	}

	/**
	 * Method to check if the cluster has a connected host that is up.
	 * 
	 * @return true if any host is up otherwise false.
	 */
	public boolean isConnected() {
		boolean answer = false;
		if (isInitialized() && !myUpHosts.isEmpty()) {
			answer = true;
		}
		LOG.debug("isConnected : {}", answer);
		return answer;
	}

	public Session getSession() throws UnableToProcessException {
		if (!myCluster.isAvailable()) {
			throw new UnableToProcessException("Driver is not initialized!", myClusterConnectionException);
		}

		return mySession;
	}

}
