package com.frs.dbservice.configuration;

import java.util.List;
import java.util.Map;

import com.frs.dbservice.exceptions.ConfigNotFoundException;
import com.frs.dbservice.exceptions.DatabaseAlreadyExistsException;
import com.frs.dbservice.exceptions.InvalidRequestException;
import com.frs.dbservice.exceptions.ProcessingException;
import com.frs.dbservice.exceptions.TableAlreadyExistsException;

public interface ConfigurationAdmin
{
    /**
     * Creates a database based on the configuration defined in the {@link DatabaseConfig}.
     * <p>
     * In the {@link DatabaseConfig} the replication of data can be set. Replication of data has to be set to one of the following:
     *
     * <ul>
     * in the cluster. This replication type will append an identifier at the end of the database name.</li>
     * </ul>
     *
     * @param databaseConfig
     *            the database configuration
     * @throws InvalidRequestException
     *             thrown if invalid input was given
     * @throws DatabaseAlreadyExistsException
     *             when database already exists
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    void createDatabase(DatabaseConfig databaseConfig) throws InvalidRequestException, DatabaseAlreadyExistsException, ProcessingException;

    /**
     * Creates a database based on the configuration defined in the {@link DatabaseConfig}.
     * <p>
     * This method is essentially the same as createDatabase() with the difference that if the database already exists, no exception will be thrown.
     * No check is made to see if the existing database is equal to the one that was sent in as parameter.
     *
     * @param databaseConfig
     *            the database configuration
     * @return True if a database was created, false if it already existed.
     * @throws InvalidRequestException
     *             thrown if invalid input was given
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    boolean createDatabaseIfNotExist(DatabaseConfig databaseConfig) throws InvalidRequestException, ProcessingException;

    /**
     * Creates a table in a database based on the configuration defined in the {@link TableConfig}.
     * <p>
     * In order for a table to be created an existing database must be specified in the {@link TableConfig}. The {@link TableConfig} allows for all
     * Cassandra table options to be set, for instance:
     *
     * <ul>
     * <li>Compaction</li>
     * <li>Compression</li>
     * <li>Gc_grace_seconds</li>
     * </ul>
     *
     * For example compaction can be set as follows:
     *
     * <pre>
     * Map&lt;CharSequence, CharSequence&gt; tableOptions = new HashMap&lt;&gt;();
     * tableOptions.put(&quot;compaction&quot;, &quot;{'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy'}&quot;);
     * </pre>
     *
     * Correct syntax of the table options is required and these options have to be set in the {@link TableConfig}. Table options not specified will
     * be set to the default options chosen by CIL.
     *
     * @param tableConfig
     *            the table configuration
     * @throws InvalidRequestException
     *             thrown if invalid input was given
     * @throws TableAlreadyExistsException
     *             when the table already exists
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    void createTable(TableConfig tableConfig) throws InvalidRequestException, TableAlreadyExistsException, ProcessingException;

    /**
     * Creates a table in a database based on the configuration defined in the {@link TableConfig}.
     * <p>
     * This method is the same as createTable() with the difference that is does not raise an exception if the table already exist. No check is made
     * to see if the existing table is equal to the one that was sent in as parameter.
     *
     * @param tableConfig
     *            the table configuration
     * @return True if a table was created, false if one already existed..
     * @throws InvalidRequestException
     *             thrown if invalid input was given
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    boolean createTableIfNotExist(TableConfig tableConfig) throws InvalidRequestException, ProcessingException;

    /**
     * Updates a database based on the configuration defined in the {@link DatabaseConfig}.
     * <p>
     * The following options can be updated or overridden:
     *
     * <ul>
     * <li>{@link DatabaseConfig#replication}</li>
     * <li>{@link DatabaseConfig#partitionType}</li>
     * <li>{@link DatabaseConfig#profiles}</li>
     * </ul>
     *
     * CIL does not support to rename a database name once created. If a new database name is entered with the very same configuration, a
     * {@link ConfigNotFoundException} is thrown. Instead create a new database and migrate the data to the new database.
     *
     * @param databaseConfig
     *            the database configuration with updated options
     * @throws InvalidRequestException
     *             thrown if invalid input was given
     * @throws ConfigNotFoundException
     *             thrown if there is no existing configuration to update
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    void updateDatabase(DatabaseConfig databaseConfig) throws InvalidRequestException, ConfigNotFoundException, ProcessingException;

    /**
     * Updates a table in a database based on the configuration defined in the {@link TableConfig}.
     * <p>
     * The following options can be updated or overridden:
     *
     * <ul>
     * <li>{@link TableConfig#tableOptions}, e.g. compaction</li>
     * <li>{@link TableConfig#profiles}</li>
     * </ul>
     *
     * CIL does not support to rename a table or its referred database once created. If a new database or table name is entered with the very same
     * configuration, a {@link ConfigNotFoundException} is thrown. Instead create a new table and migrate the data to the new table.
     *
     * @param tableConfig
     *            the table configuration with updated options
     * @throws InvalidRequestException
     *             thrown if invalid input was given
     * @throws ConfigNotFoundException
     *             thrown if there is no existing configuration to update
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    void updateTable(TableConfig tableConfig) throws InvalidRequestException, ConfigNotFoundException, ProcessingException;

    /**
     * Gets a map containing database names as key and associated {@link DatabaseConfig} as value.
     *
     * @return a map of database names and associated configurations
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    Map<String, DatabaseConfig> getAllDatabaseConfigurations() throws ProcessingException;

    /**
     * Gets a map containing table names as key and associated {@link TableConfig} as value.
     *
     * @return a map of table names and associated configurations
     * @throws InvalidRequestException
     *             thrown if invalid input was given, for example the database does not exist
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    Map<String, TableConfig> getAllTableConfigurations(String databaseName) throws InvalidRequestException, ProcessingException;


    /**
     * Gets all partition IDs from a {@link DatabaseConfig}.
     *
     * Gets all the partition IDs from a specified {@link DatabaseConfig}. Will retrieve the partition IDs from a {@link DatabaseConfig},
     * both referring to a profile and having its partitions specified.
     *
     * @param databaseConfig
     *             the database configuration to retrieve the partitions from
     * @return a list containing the partition IDs
     * @throws InvalidRequestException
     *             thrown if invalid input was given
     * @throws ProcessingException
     *             when the cluster is unable to process the request
     */
    List<String> getAllPartitions(DatabaseConfig databaseConfig) throws InvalidRequestException, ProcessingException;
}
