package rph.mikazuki.grpc.demo;

import io.grpc.BindableService;
import rph.mikazuki.grpc.model.GrpcServer;

public class DemoServer extends GrpcServer {

    public DemoServer(int port, BindableService service) {
        super(port,service);
    }

}
