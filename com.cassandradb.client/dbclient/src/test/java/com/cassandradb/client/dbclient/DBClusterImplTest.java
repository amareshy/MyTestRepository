package com.cassandradb.client.dbclient;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.cassandradb.client.dbservice.DBCluster;

/**
 * Unit test for simple App.
 */
public class DBClusterImplTest 
{
	private AbstractApplicationContext appContext;
	
	@Before
	public void before() {
		appContext = new AnnotationConfigApplicationContext(AppConfig.class);
	}
	
	@Test
	public void clusterTest() {
		DBCluster dbCluster = appContext.getBean("dbCluster",DBCluster.class);
		assertNotNull(dbCluster);
	}
	
	
}
