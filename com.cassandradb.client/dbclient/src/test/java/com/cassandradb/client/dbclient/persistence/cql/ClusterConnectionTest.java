package com.cassandradb.client.dbclient.persistence.cql;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cassandradb.client.dbclient.persistence.cql.ClusterConnection;
import com.cassandradb.client.dbservice.exceptions.UnableToProcessException;
import com.datastax.driver.core.Host;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {ClusterConnectionTestConfig.class})
public class ClusterConnectionTest {

	@Autowired
	private ClusterConnection myClusterConnection;


	@Test
	public void test() throws UnableToProcessException {
		assertNotNull(myClusterConnection);
		Set<Host> hosts = myClusterConnection.getSession().getCluster().getMetadata().getAllHosts();
		System.out.println("Test started.........." + hosts.toString());
	}

}
