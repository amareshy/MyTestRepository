package com.cassandradb.client.client.configuraion;

import java.util.Collections;
import java.util.Map;

public class DbConfigInternal
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

    private DbConfigInternal(DbConfigInternalBuilder builder)
    {
	this.databaseName = builder.databaseName;
	this.replication = builder.replication;
	this.partitionType = builder.partitionType;
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

    public static DbConfigInternalBuilder newBuilder()
    {
	return new DbConfigInternalBuilder();
    }

    public static class DbConfigInternalBuilder
    {
	private String databaseName;
	private Map<String, String> replication;
	private PartitionType partitionType;

	private DbConfigInternalBuilder()
	{

	}

	public DbConfigInternalBuilder setDatabaseName(String databaseName)
	{
	    this.databaseName = databaseName;
	    return this;
	}

	public DbConfigInternalBuilder setReplication(
	    Map<String, String> replication)
	{
	    this.replication = replication;
	    return this;
	}

	public DbConfigInternalBuilder setPartitionType(
	    PartitionType partitionType)
	{
	    this.partitionType = partitionType;
	    return this;
	}

	public DbConfigInternal build()
	{
	    return new DbConfigInternal(this);
	}
    }
}
