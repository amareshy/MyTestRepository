package com.cassandradb.client.client.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cassandradb.client.client.datasource.ClusterConnection;
import com.cassandradb.client.client.service.status.StatusAdmin;

@Repository("statusAdmin")
public class StatusAdminImpl implements StatusAdmin {
	@Autowired
	private ClusterConnection myClusterConn;

	@Override
	public boolean isConnected() {
		return myClusterConn.isConnected();
	}

}
