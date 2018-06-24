package com.cassandradb.client.dbclient.persistence.cql;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cassandradb.client.dbclient.AppConfig;
import com.cassandradb.client.dbservice.exceptions.UnableToProcessException;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Session.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AppConfig.class})
public class ClusterConnectionTest {

	@Autowired
	private ClusterConnection myClusterConnection;
//	private CassandraShutDownHook shutdownHook;

	@Before
	public void init() {
		  /*shutdownHook = new CassandraShutDownHook();
		  CassandraEmbeddedServerBuilder.builder().withShutdownHook(shutdownHook).buildServer();*/
//		CassandraDaemon.main(new String[]{});
		
	}

	@Test
	public void testCreateClusterConnectionForConnectingCluster() throws UnableToProcessException {
		assertNotNull(myClusterConnection);
		Session session = myClusterConnection.getSession();
		assertNotNull(session);
		session.init();
		State state = session.getState();
		assertNotNull(state);
		System.out.println("This session is currently connected to the hosts : " + state.getConnectedHosts());
		Set<Host> hosts = session.getCluster().getMetadata().getAllHosts();
		assertFalse(hosts.isEmpty());
		System.out.println("All Known hosts in the cluster : " + hosts);
	}
	
	@After
	public void shutDown() {
		/*try {
			shutdownHook.shutDownNow();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}

}
