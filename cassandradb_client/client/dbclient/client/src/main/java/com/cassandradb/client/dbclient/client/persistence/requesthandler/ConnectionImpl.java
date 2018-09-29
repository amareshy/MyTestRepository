package com.cassandradb.client.dbclient.client.persistence.requesthandler;

import org.springframework.stereotype.Repository;

import com.cassandradb.client.dbservice.service.Connection;

@Repository("myConnection")
public class ConnectionImpl implements Connection {
    public ConnectionImpl() {

    }
}
