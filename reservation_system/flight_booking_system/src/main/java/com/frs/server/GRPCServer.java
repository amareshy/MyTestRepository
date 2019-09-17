package com.frs.server;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.etcd.client.client.service.EntityData;
import com.etcd.client.client.service.EntityKey;
import com.etcd.client.client.service.EntityProperty;
import com.etcd.client.client.service.EntityValue;
import com.etcd.client.client.service.EtcdDataAccessorService;
import com.etcd.client.client.service.LeaseKeepAliveObserver;
import com.frs.services.UserManagentService;

import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;

@Component
public class GRPCServer
{
    /** Some logger. */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(GRPCServer.class);

    /**
     * GRPC Server to set up.
     */
    private Server server;

    /** Initiqlized once at startup use for ETCD. */
    private String applicationUID;

    @Inject
    private EtcdDataAccessorService etcdDataAccess;

    @Inject
    private UserManagentService myUserMgmtService;

    @PostConstruct
    public void start() throws Exception
    {
	int port = 50051;
	String applicationHost = "127.0.0.1";
	applicationUID = "1";
	LOGGER.info("Initializing Grpc Server...");

	final ServerServiceDefinition userManagementService = this.myUserMgmtService
	    .bindService();

	server = ServerBuilder.forPort(port).addService(userManagementService)
	    .build();

	Runtime.getRuntime().addShutdownHook(new Thread()
	{
	    public void run()
	    {
		LOGGER.info("Calling shutdown for GrpcServer");
		server.shutdown();
	    }
	});

	// Start Grpc listener
	server.start();
	LOGGER.info("Grpc Server started on port: '{}'", server.getPort());

	// Service are now Bound an started, declare in ETCD
	final String applicationAdress = String.format("%s:%d", applicationHost,
	    port);
	LOGGER.info("Registering services in ETCD with address {}",
	    applicationAdress);
	registerServicesToEtcd(applicationAdress, userManagementService);
	LOGGER.info("Services now registered in ETCD");
	server.awaitTermination();
    }

    @PreDestroy
    public void stop()
    {
	server.shutdown();
    }

    /**
     * Enter information into ETCD.
     *
     * @param serviceDefinitions services definitions from GRPC
     * @throws IOException exception when accessing ETCD
     */
    private void registerServicesToEtcd(String applicationAdress,
        ServerServiceDefinition... serviceDefinitions) throws IOException
    {
	// Note that we don't use a lambda to ease Exception propagation
	for (ServerServiceDefinition service : serviceDefinitions)
	{
	    final String shortName = shortenServiceName(
	        service.getServiceDescriptor().getName());
	    final String serviceKey = String.format(
	        "flightReservationSystem/services/%s/%s", shortName,
	        applicationUID);
	    LOGGER.info(" + [{}] : key={}", shortName, serviceKey);

	    EntityProperty property = EntityProperty.newBuilder().withTtl(5)
	        .withLeaseKeepAliveObserver(new LeaseKeepAliveObserver()
	        {

		    @Override
		    public void onNext(LeaseKeepAliveResponse value)
		    {
		        LOGGER.debug("onNext LeaseKeepAliveResponse :: id :"
		            + value.getID() + " TTL: " + value.getTTL());

		    }

		    @Override
		    public void onError(Throwable t)
		    {
		        LOGGER.error("onError LeaseKeepAliveResponse :: ", t);

		    }

		    @Override
		    public void onCompleted()
		    {
		        LOGGER.debug("onCompleted LeaseKeepAliveResponse ");
		    }
	        }).build();

	    EntityKey entityKey = EntityKey.newBuilder().withKeyName(serviceKey)
	        .build();
	    EntityValue entityValue = EntityValue.newBuilder()
	        .withValue(applicationAdress).build();

	    etcdDataAccess.register(EntityData.newBuilder()
	        .withEntityKey(entityKey).withEntityValue(entityValue)
	        .withEntityProperty(property).build());

	}
    }

    /**
     * Remove special characters.
     */
    private String shortenServiceName(String fullServiceName)
    {
	return fullServiceName.replaceAll(".*\\.([^.]+)", "$1");
    }
}
