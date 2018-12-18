package com.cassandradb.client.dbclient.client.configuraion;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cassandradb.client.client.AppConfig;
import com.cassandradb.client.client.configuraion.DatabaseConfig;
import com.cassandradb.client.client.configuraion.PartitionType;
import com.cassandradb.client.client.service.DBCluster;
import com.cassandradb.client.client.service.configuration.ConfigurationAdmin;
import com.cassandradb.client.client.service.exceptions.DatabaseAlreadyExistsException;
import com.cassandradb.client.client.service.exceptions.InvalidRequestException;
import com.cassandradb.client.client.service.exceptions.ProcessingException;

// @Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ AppConfig.class })
public class ConfigurationAdminImplTest
{
    @Autowired
    private DBCluster myDbCluster;

    @Test
    public void createKeyspace() throws DatabaseAlreadyExistsException,
        InvalidRequestException, ProcessingException
    {
	assertNotNull(myDbCluster);
	ConfigurationAdmin configurationAdmin = myDbCluster
	    .getConfigurationAdmin();
	assertNotNull(configurationAdmin);
	DatabaseConfig dbConfig = DatabaseConfig.newBuilder()
	    .setDatabaseName("testdb").setPartitionType(PartitionType.GLOBAL)
	    .setReplication(Collections.EMPTY_MAP).build();
	configurationAdmin.createDatabase(dbConfig);
    }

}
