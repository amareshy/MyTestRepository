package com.etcdserver.grpc;

import static java.lang.String.format;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etcdserver.dao.EtcdDao;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.examples.helloworld.GreeterGrpc.GreeterImplBase;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

/**
 * Startup a GRPC server on expected port and register all services.
 */
@Component
public class GrpcServer
{

    /** Some logger. */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(GrpcServer.class);

    /** Connectivity to ETCD Service discovery. */
    @Autowired
    private EtcdDao etcdDao;

    /**
     * Communication channel between service, for now GUAVA inmemory messaging.
     */
    // @Inject
    // private EventBus eventBus;

    /**
     * GRPC Server to set up.
     */
    private Server server;

    /** Initiqlized once at startup use for ETCD. */
    private String applicationUID;

    @PostConstruct
    public void start() throws Exception
    {
	int port = 50051;
	applicationUID = "localhost" + ":" + port;
	LOGGER.info("Initializing Grpc Server...");
	// Binding Services
	/*
	 * final ServerServiceDefinition commentService =
	 * this.commentService.bindService(); final ServerServiceDefinition
	 * ratingService = this.ratingService.bindService(); final
	 * ServerServiceDefinition statisticsService =
	 * this.statisticsService.bindService(); final ServerServiceDefinition
	 * suggestedVideoService = this.suggestedVideosService.bindService();
	 * final ServerServiceDefinition uploadsService =
	 * this.uploadsService.bindService(); final ServerServiceDefinition
	 * userManagementService = this.userManagementService.bindService();
	 * final ServerServiceDefinition videoCatalogService =
	 * this.videoCatalogService.bindService(); final ServerServiceDefinition
	 * searchService = this.searchService.bindService();
	 */

	// Initializing GRPC endpoint
	server = ServerBuilder.forPort(port).addService(new GreeterImpl())
	    .build();
	/*
	 * .addService(commentService).addService(ratingService)
	 * .addService(statisticsService).addService(suggestedVideoService)
	 * .addService(uploadsService).addService(userManagementService)
	 * .addService(videoCatalogService).addService(searchService).build();
	 */

	// Initialize Event bus
	/*
	 * eventBus.register(suggestedVideosService);
	 * eventBus.register(cassandraMutationErrorHandler);
	 */

	/**
	 * Declare a shutdown hook otherwise the JVM cannot be stop since the
	 * Grpc server is listening on a port forever
	 */
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
	final String applicationAdress = format("%s:%d", "reservation-system",
	    port);
	LOGGER.info("Registering services in ETCD with address {}",
	    applicationAdress);
	registerServicesToEtcd(applicationAdress);
	LOGGER.info("Services now registered in ETCD");
    }

    @PreDestroy
    public void stop()
    {
	// eventBus.unregister(suggestedVideosService);
	// eventBus.unregister(cassandraMutationErrorHandler);
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
	    final String serviceKey = format("/reservation/services/%s/%s",
	        shortName, applicationUID);
	    LOGGER.info(" + [{}] : key={}", shortName, serviceKey);
	    etcdDao.register(serviceKey, applicationAdress);
	}
    }

    /**
     * Remove special caracters.
     */
    private String shortenServiceName(String fullServiceName)
    {
	return fullServiceName.replaceAll(".*\\.([^.]+)", "$1");
    }

    static class GreeterImpl extends GreeterImplBase
    {
	@Override
	public void sayHello(HelloRequest request,
	    StreamObserver<HelloReply> responseObserver)
	{
	    super.sayHello(request, responseObserver);
	}
    }
}
