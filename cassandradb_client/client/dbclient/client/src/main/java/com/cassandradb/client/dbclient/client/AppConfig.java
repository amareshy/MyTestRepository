package com.cassandradb.client.dbclient.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cassandradb.client.dbclient.client.datasource.ClusterHolder;
import com.cassandradb.client.dbclient.client.persistence.requesthandler.DatabaseIfHandler;

@Configuration
@ComponentScan("com.cassandradb.client.dbclient")
public class AppConfig
{
    @Bean(name = "myCluster")
    public ClusterHolder getCluster()
    {
	return new ClusterHolder();
    }

    @Bean(name = "myDatabaseIfHandler")
    public DatabaseIfHandler getDatabaseIfHandler()
    {
	return new DatabaseIfHandler();
    }
}
