package com.cassandradb.client.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cassandradb.client.client.datasource.ClusterConnection;
import com.cassandradb.client.client.datasource.ClusterHolder;
import com.cassandradb.client.client.persistence.requesthandler.DatabaseIfHandler;

@Configuration
@ComponentScan("com.cassandradb.client.client")
public class AppConfig
{
    @Bean(name = "clusterConnection")
    public ClusterConnection getClusterConnection()
    {
	return new ClusterConnection();
    }

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
