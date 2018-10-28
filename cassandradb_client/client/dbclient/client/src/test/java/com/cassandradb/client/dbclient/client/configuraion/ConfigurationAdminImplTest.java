package com.cassandradb.client.dbclient.client.configuraion;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cassandradb.client.dbclient.client.AppConfig;


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ConfigurationAdminImplTest {

	@Test
	public void test() {
	}

}
