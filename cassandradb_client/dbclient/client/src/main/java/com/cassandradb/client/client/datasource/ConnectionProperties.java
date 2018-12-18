package com.cassandradb.client.client.datasource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:datasource.properties")
public class ConnectionProperties
{
    private static final Logger LOG = LoggerFactory
        .getLogger(ConnectionProperties.class);
    private Set<String> myContactPointAddresses = new HashSet<>();
    private int myContactPointPort;
    private String myServerUsername;
    private String myServerPassword;

    @Autowired
    Environment env;

    /**
     * Default value for Cassandra port(i.e. value of native_transport_port in
     * yaml file).
     */
    public static final int DEFAULT_SERVER_PORT = 9042;

    /**
     * Default value for Cassandra hostname.
     * <p>
     * This default value is typically only usable in a test environment.
     */
    public static final String DEFAULT_SERVER_HOST = "localhost";
    /**
     * Default value for Cassandra client username
     */
    public static final String DEFAULT_SERVER_USERNAME = "cassandra";
    /**
     * Default value for Cassandra client password
     */
    public static final String DEFAULT_SERVER_PASSWORD = "cassandra";

    public ConnectionProperties()
    {
    }

    public void apply()
    {
	if (env == null)
	{
	    // Set all default
	    myContactPointAddresses.add(DEFAULT_SERVER_HOST);
	    myContactPointPort = DEFAULT_SERVER_PORT;
	    myServerUsername = DEFAULT_SERVER_USERNAME;
	    myServerPassword = DEFAULT_SERVER_PASSWORD;
	}

	myContactPointAddresses = getHosts();
	myContactPointPort = getPort();
	myServerUsername = getUserName();
	myServerPassword = getPassword();
    }

    private String getPassword()
    {
	return env.getProperty("cassandra.server.password");
    }

    private String getUserName()
    {
	return env.getProperty("cassandra.server.username");
    }

    private Set<String> getHosts()
    {
	Set<String> hosts = Collections.synchronizedSet(new HashSet<>());
	@SuppressWarnings("unchecked")
	Set<String> cassandraServerHosts = env
	    .getProperty("cassandra.server.hosts", HashSet.class);

	// Convert hostnames to IP addresses
	for (String host : cassandraServerHosts)
	{

	    try
	    {
		InetAddress address = InetAddress.getByName(host);
		hosts.add(address.getHostAddress());
	    } catch (UnknownHostException e)
	    {
		LOG.error("Invalid host entered: " + host);
	    }
	}
	return Collections.unmodifiableSet(hosts);
    }

    private int getPort()
    {
	return env.getProperty("cassandra.server.port", Integer.class);
    }

    public Set<String> getMyContactPointAddresses()
    {
	return myContactPointAddresses;
    }

    public int getMyContactPointPort()
    {
	return myContactPointPort;
    }

    public String getMyServerUsername()
    {
	return myServerUsername;
    }

    public String getMyServerPassword()
    {
	return myServerPassword;
    }
}
