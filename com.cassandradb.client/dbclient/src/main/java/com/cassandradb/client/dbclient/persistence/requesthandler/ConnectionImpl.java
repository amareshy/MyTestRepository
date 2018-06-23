package com.cassandradb.client.dbclient.persistence.requesthandler;

import org.springframework.stereotype.Repository;

import com.cassandradb.client.dbservice.Connection;

@Repository("myConnection")
public class ConnectionImpl implements Connection {
	public ConnectionImpl() {

	}
}
