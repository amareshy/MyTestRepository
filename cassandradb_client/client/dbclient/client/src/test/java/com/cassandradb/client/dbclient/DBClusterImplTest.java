package com.cassandradb.client.dbclient;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cassandradb.client.dbclient.client.AppConfig;
import com.cassandradb.client.dbclient.client.datasource.ClusterConnection;
import com.cassandradb.client.dbclient.service.DBCluster;
import com.cassandradb.client.dbclient.service.exceptions.UnableToProcessException;
import com.cassandradb.client.dbclient.service.status.StatusAdmin;

/**
 * Unit test for DBCluster.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class DBClusterImplTest {
    @Autowired
    private DBCluster myDBCluster;
    @Autowired
    private ClusterConnection myClusterConn;

    @Test
    public void testStatusAdminForClusterConnection() throws UnableToProcessException {
    	assertNotNull(myDBCluster);
    	StatusAdmin statusAdmin = myDBCluster.getStatusAdmin();
    	assertNotNull(statusAdmin);
    	assertTrue(statusAdmin.isConnected());
    }

}
