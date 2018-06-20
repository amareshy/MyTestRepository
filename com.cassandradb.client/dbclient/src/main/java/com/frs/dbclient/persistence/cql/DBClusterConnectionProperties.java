package com.frs.dbclient.persistence.cql;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frs.dbclient.DBClusterImpl;

public class DBClusterConnectionProperties {
	private static final Logger LOG = LoggerFactory.getLogger(DBClusterConnectionProperties.class);
	private Set<String> myContactPointAddresses = new HashSet<>();
	private int myContactPointPort = -1;
	private String myServerUsername;
	private String myServerPassword;

	public DBClusterConnectionProperties() {

	}

	public void apply() {
		myContactPointAddresses = getHosts();
		myContactPointPort = getPort();
		myServerUsername = getUserName();
		myServerPassword = getPassword();
	}

	private String getPassword() {
		return DBClusterImpl.DEFAULT_SERVER_USERNAME;
	}

	private String getUserName() {
		return DBClusterImpl.DEFAULT_SERVER_PASSWORD;
	}

	private Set<String> getHosts() {
		Set<String> tempHosts = Collections.synchronizedSet(new HashSet<>());
		Set<String> hosts = Collections.synchronizedSet(new HashSet<>());

		tempHosts.add(DBClusterImpl.DEFAULT_SERVER_HOST);

		// Convert hostnames to IP addresses
		for (String host : tempHosts) {

			try {
				InetAddress address = InetAddress.getByName(host);
				hosts.add(address.getHostAddress());
			} catch (UnknownHostException e) {
				LOG.error("Invalid host entered: " + host);
			}
		}
		return Collections.unmodifiableSet(hosts);
	}

	private int getPort() {
		return DBClusterImpl.DEFAULT_SERVER_PORT;
	}

	public Set<String> getMyContactPointAddresses() {
		return myContactPointAddresses;
	}
	
	public int getMyContactPointPort() {
		return myContactPointPort;
	}
	
	public String getMyServerUsername() {
		return myServerUsername;
	}
	public String getMyServerPassword() {
		return myServerPassword;
	}
}
