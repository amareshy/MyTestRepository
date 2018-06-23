package com.cassandradb.client.dbclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cassandradb.client.dbclient.persistence.cql.DBClusterConnectionProperties;

@Configuration
@ComponentScan("com.cassandradb.client.dbclient")
public class AppConfig {

	@Bean
	public DBClusterConnectionProperties getDBClusterConnectionProperties() {
		return new DBClusterConnectionProperties();
	}
}
