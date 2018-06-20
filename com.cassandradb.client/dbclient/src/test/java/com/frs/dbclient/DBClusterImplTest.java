package com.frs.dbclient;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.frs.dbservice.DBCluster;

/**
 * Unit test for simple App.
 */
//@Ignore
public class DBClusterImplTest 
{
	@Before
	public void before() {
		AbstractApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
		DBCluster dbCluster = appContext.getBean("dbCluster",DBCluster.class);
		System.out.println(" "+ dbCluster.getConnection());
	}
	
	@Test
	public void clusterTest() {
		
	}
	
	
}
