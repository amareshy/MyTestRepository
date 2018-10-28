package com.cassandradb.client.dbclient.client.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cassandradb.client.dbclient.client.datasource.ClusterConnection;
import com.cassandradb.client.dbclient.service.status.StatusAdmin;

@Repository("statusAdmin")
public class StatusAdminImpl implements StatusAdmin {
	@Autowired
	private ClusterConnection myClusterConn;

	@Override
	public boolean isConnected() {
		return myClusterConn.isConnected();
	}

}
