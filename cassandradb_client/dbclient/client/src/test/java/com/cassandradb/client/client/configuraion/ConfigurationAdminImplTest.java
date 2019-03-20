package com.cassandradb.client.client.configuraion;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cassandradb.client.client.AppConfig;
import com.cassandradb.client.client.service.DBCluster;
import com.cassandradb.client.client.service.configuration.ConfigurationAdmin;
import com.cassandradb.client.client.service.configuration.DatabaseConfig;
import com.cassandradb.client.client.service.exceptions.DatabaseAlreadyExistsException;
import com.cassandradb.client.client.service.exceptions.DbServiceException;
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
        InvalidRequestException, ProcessingException, DbServiceException
    {
	assertNotNull(myDbCluster);
	ConfigurationAdmin configurationAdmin = myDbCluster
	    .getConfigurationAdmin();
	assertNotNull(configurationAdmin);
	Map<String, String> replication = new HashMap<>();
	replication.put("india_dc", "1");
	replication.put("uk_dc", "2");
	DatabaseConfig dbConfig = DatabaseConfig.newBuilder()
	    .setDatabaseName("testdb2").setPartitionType(PartitionType.GLOBAL)
	    .setReplication(replication).build();
	configurationAdmin.createDatabase(dbConfig);

    }

    @Test
    public void createKeyspaceIfNotExists()
        throws DatabaseAlreadyExistsException, InvalidRequestException,
        ProcessingException, DbServiceException
    {
	assertNotNull(myDbCluster);
	ConfigurationAdmin configurationAdmin = myDbCluster
	    .getConfigurationAdmin();
	assertNotNull(configurationAdmin);
	Map<String, String> replication = new HashMap<>();
	replication.put("india_dc", "1");
	replication.put("uk_dc", "2");
	DatabaseConfig dbConfig = DatabaseConfig.newBuilder()
	    .setDatabaseName("testdb2")
	    .setPartitionType(PartitionType.PARTITIONED)
	    .setReplication(replication).build();
	configurationAdmin.createDatabaseIfNotExist(dbConfig);
    }
}
