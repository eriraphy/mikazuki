package rph.mikazuki.grpc.demo;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import rph.mikazuki.grpc.TerminalGrpc;
import rph.mikazuki.grpc.TerminalProto;
import rph.mikazuki.grpc.model.GrpcClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

public class DemoClient extends GrpcClient {
    private TerminalGrpc.TerminalBlockingStub blockingStub;
    private TerminalGrpc.TerminalStub asyncStub;


    public DemoClient(String host, int port) {
        super(host, port);
        blockingStub = TerminalGrpc.newBlockingStub(channel);
        asyncStub = TerminalGrpc.newStub(channel);
    }

    public void basicValidation(String key, String content0, String content1) {
        TerminalProto.Request request = TerminalProto.Request.newBuilder()
                .setKey(key)
                .setContent0(content0)
                .setContent1(content1)
                .build();

        TerminalProto.Response response;
        try {
            response = blockingStub.basicValidation(request);
            if (response != null && !response.getKey().isEmpty()) {
                System.out.println("Response: " + response.getKey());
            } else {
                System.err.println("No information found");
            }
        } catch (StatusRuntimeException e) {
            System.err.printf("RPC failed: {%s}", e.getStatus());
        }

    }

    public void serverStreamingValidation(String key, String content0, String content1) {
        TerminalProto.Request request = TerminalProto.Request.newBuilder()
                .setKey(key)
                .setContent0(content0)
                .setContent1(content1)
                .build();

        Iterator<TerminalProto.Response> responses;
        try {
            responses = blockingStub.serverStreamingValidation(request);
            for (int i = 1; responses.hasNext(); i++) {
                TerminalProto.Response response = responses.next();
                System.out.println("Response" + i + ": " + response.getKey());
            }
        } catch (StatusRuntimeException e) {
            System.err.printf("RPC failed: {%s}", e.getStatus());
        }

    }

    public void clientStreamingValidation(ArrayList<String> keys, ArrayList<String> content0s, ArrayList<String> content1s) {
        final CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<TerminalProto.Response> responseObserver = new StreamObserver<TerminalProto.Response>() {
            @Override
            public void onNext(TerminalProto.Response response) {
                System.out.println("Response: " + response.getKey());
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.printf("RPC failed: {%s}", throwable.getCause());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };

        StreamObserver<TerminalProto.Request> requestObserver = asyncStub.clientStreamingValidation(responseObserver);
        try {
            for (int index = 0; index < keys.size(); index++) {
                requestObserver.onNext(TerminalProto.Request.newBuilder()
                        .setKey(keys.get(index))
                        .setContent0(content0s.get(index))
                        .setContent1(content1s.get(index))
                        .build());
            }
        } catch (Exception e) {
            requestObserver.onError(e);
            throw e;
        }
        requestObserver.onCompleted();
    }
}
