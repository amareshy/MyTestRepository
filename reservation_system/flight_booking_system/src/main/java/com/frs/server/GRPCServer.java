package com.frs.server;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.examples.helloworld.GreeterGrpc.GreeterImplBase;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

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

    @PostConstruct
    public void start() throws Exception
    {
	int port = 50051;
	applicationUID = "localhost" + ":" + port;
	LOGGER.info("Initializing Grpc Server...");

	server = ServerBuilder.forPort(port).addService(new GreeterImpl())
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
	final String applicationAdress = String.format("%s:%d",
	    "reservation-system", port);
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
	    final String serviceKey = String.format(
	        "/reservation/services/%s/%s", shortName, applicationUID);
	    LOGGER.info(" + [{}] : key={}", shortName, serviceKey);
	    // etcdDao.register(serviceKey, applicationAdress);
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
