package rph.mikazuki.grpc.model;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public abstract class GrpcServer {
    private final int port;
    private final Server server;

    public GrpcServer(int port, BindableService service) {
        this.port = port;
        server = ServerBuilder.forPort(port).addService(service).build();
    }

    public void start() throws Exception {
        server.start();
        System.out.println(">>> Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(">>> Shutting down gRPC server");
            GrpcServer.this.stop();
            System.out.println(">>> Server shut down");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}
