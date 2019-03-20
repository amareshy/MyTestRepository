package com.cassandradb.client.client.service.configuration;

import java.util.Collections;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.cassandradb.client.client.configuraion.PartitionType;

public class DatabaseConfig
{
    /* Defines the name of the database or keyspace(mandatory) */
    private final String databaseName;
    /* Replication factor of the keyspace for each DC. */
    private final Map<String, String> replication;
    /*
     * Defines whether the replication is of type partitioned or global
     * (mandatory)
     */
    private final PartitionType partitionType;

    private final String partition;

    private DatabaseConfig(DatabaseConfigBuilder builder)
    {
	if (StringUtils.isEmpty(builder.databaseName))
	{
	    throw new IllegalArgumentException(
	        "databaseName shouldn't be null or empty");
	}

	if (builder.partitionType == null || !PartitionType.class
	    .isAssignableFrom(builder.partitionType.getClass()))
	{
	    throw new IllegalArgumentException(
	        "partitionType must not be null or empty and it should be a valid value{ PARTITIONED or GLOBAL}");
	}

	this.databaseName = builder.databaseName;
	this.replication = builder.replication;
	this.partitionType = builder.partitionType;
	this.partition = builder.partition;
    }

    public String getDatabaseName()
    {
	return databaseName;
    }

    public Map<String, String> getReplication()
    {
	return Collections.unmodifiableMap(replication);
    }

    public PartitionType getPartitionType()
    {
	return partitionType;
    }

    public String getPartition()
    {
	return partition;
    }

    public static DatabaseConfigBuilder newBuilder()
    {
	return new DatabaseConfigBuilder();
    }

    public static class DatabaseConfigBuilder
    {
	private String databaseName;
	private Map<String, String> replication;
	private PartitionType partitionType;
	private String partition;

	private DatabaseConfigBuilder()
	{

	}

	public DatabaseConfigBuilder setDatabaseName(String databaseName)
	{
	    this.databaseName = databaseName;
	    return this;
	}

	public DatabaseConfigBuilder setReplication(
	    Map<String, String> replication)
	{
	    this.replication = replication;
	    return this;
	}

	public DatabaseConfigBuilder setPartitionType(
	    PartitionType partitionType)
	{
	    this.partitionType = partitionType;
	    return this;
	}

	public DatabaseConfigBuilder setPartition(String partition)
	{
	    this.partition = partition;
	    return this;
	}

	public DatabaseConfig build()
	{
	    return new DatabaseConfig(this);
	}
    }
}
