package rph.mikazuki.grpc.demo;

public class DemoServerApp {

    public static void main(String[] args) throws Exception {
        DemoServer terminalServer = new DemoServer(8735,new DemoService());
        terminalServer.start();
        terminalServer.blockUntilShutdown();
    }
}
