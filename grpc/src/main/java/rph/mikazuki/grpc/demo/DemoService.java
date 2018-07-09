package rph.mikazuki.grpc.demo;

import io.grpc.stub.StreamObserver;
import rph.mikazuki.grpc.TerminalGrpc;
import rph.mikazuki.grpc.TerminalProto;

import java.util.concurrent.TimeUnit;

public class DemoService extends TerminalGrpc.TerminalImplBase {

    @Override
    public void basicValidation(TerminalProto.Request request, StreamObserver<TerminalProto.Response> responseObserver) {
        responseObserver.onNext(this.validateGeneral(request));
        responseObserver.onCompleted();
    }

    @Override
    public void serverStreamingValidation(TerminalProto.Request request, StreamObserver<TerminalProto.Response> responseObserver) {
        responseObserver.onNext(this.validateGeneral(request));
        responseObserver.onNext(this.validateAdv(request));
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<TerminalProto.Request> clientStreamingValidation(final StreamObserver<TerminalProto.Response> responseObserver) {
        return new StreamObserver<TerminalProto.Request>() {
            int requestCount;
            final long startTime = System.nanoTime();
            String res = "";

            @Override
            public void onNext(TerminalProto.Request request) {
                requestCount++;
                res += " || " + request.getKey() + " | " + request.getContent0() + " | " + request.getContent1();
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("clientStreamingValidation cancelled");
            }

            @Override
            public void onCompleted() {
                long seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                String appendix = " || elapsed time: " + String.valueOf(seconds);
                appendix += " || total counts " + String.valueOf(requestCount);
                responseObserver.onNext(TerminalProto.Response.newBuilder().setKey(res + appendix).build());
            }
        };
    }

    private TerminalProto.Response validateGeneral(TerminalProto.Request request) {
        String res = request.getKey() + " | " + request.getContent0() + " | " + request.getContent1();
        return TerminalProto.Response.newBuilder().setKey(res).build();
    }

    private TerminalProto.Response validateAdv(TerminalProto.Request request) {
        String res = request.getKey() + " || " + request.getContent0() + " || " + request.getContent1();
        return TerminalProto.Response.newBuilder().setKey(res).build();
    }
}
