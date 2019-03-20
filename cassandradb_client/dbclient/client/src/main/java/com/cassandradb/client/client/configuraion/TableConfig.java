package com.cassandradb.client.client.configuraion;

import java.util.Collections;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class TableConfig
{
    /* Defines the name of the database (mandatory) */
    private final String databaseName;
    /* Defines the name of the table to be created (mandatory) */
    private final String tableName;
    /*
     * Defines options for the table to create, e.g., compaction strategy
     * (non-mandatory)
     */
    private final Map<String, String> tableOptions;
    /* Defines the type of data Table can store. */
    private final TableType tableType;
    /*
     * Defines the option for whether or not the mapping table should be created
     */
    public final boolean createMapping;

    private TableConfig(TableConfigBuilder builder)
    {
	if (StringUtils.isEmpty(builder.databaseName))
	{
	    throw new IllegalArgumentException(
	        "databaseName must be set and shouldn't be null or empty");
	}
	if (StringUtils.isEmpty(builder.tableName))
	{
	    throw new IllegalArgumentException(
	        "tableName must be set and shouldn't be null or empty");
	}
	if (CollectionUtils.isEmpty(builder.tableOptions))
	{
	    throw new IllegalArgumentException(
	        "tableOptions must be set and shouldn't be null or empty");
	}
	if (builder.tableType == null
	    || !TableType.class.isAssignableFrom(builder.tableType.getClass()))
	{
	    throw new IllegalArgumentException(
	        "tableType must be set and should be a valid value");
	}

	this.databaseName = builder.databaseName;
	this.tableName = builder.tableName;
	this.tableOptions = builder.tableOptions;
	this.tableType = builder.tableType;
	this.createMapping = builder.createMapping;
    }

    public String getDatabaseName()
    {
	return databaseName;
    }

    public String getTableName()
    {
	return tableName;
    }

    public Map<String, String> getTableOptions()
    {
	return Collections.unmodifiableMap(tableOptions);
    }

    public TableType getTableType()
    {
	return tableType;
    }

    public boolean isCreateMapping()
    {
	return createMapping;
    }

    public static TableConfigBuilder newBuilder()
    {
	return new TableConfigBuilder();
    }

    static class TableConfigBuilder
    {
	private String databaseName;
	private String tableName;
	private Map<String, String> tableOptions;
	private TableType tableType;
	public boolean createMapping;

	private TableConfigBuilder()
	{
	}

	public void setDatabaseName(String databaseName)
	{
	    this.databaseName = databaseName;
	}

	public void setTableName(String tableName)
	{
	    this.tableName = tableName;
	}

	public void setTableOptions(Map<String, String> tableOptions)
	{
	    this.tableOptions = tableOptions;
	}

	public void setTableType(TableType tableType)
	{
	    this.tableType = tableType;
	}

	public void setCreateMapping(boolean createMapping)
	{
	    this.createMapping = createMapping;
	}

	public TableConfig build()
	{
	    return new TableConfig(this);
	}
    }
}
