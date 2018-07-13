package rph.mikazuki.demo.grpcclient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryManager;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;

@EnableEurekaClient
@Component
public class DemoGrpcClient {

    public void basicValidation(String key) {

        final InstanceInfo instanceInfo = DiscoveryManager.getInstance()
                .getDiscoveryClient()
                .getNextServerFromEureka("grpc-service", false);
        final ManagedChannel channel= ManagedChannelBuilder
                .forAddress(instanceInfo.getIPAddr(),instanceInfo.getPort())
                .usePlaintext()
                .build();
        final TerminalGrpc.TerminalBlockingStub stub = TerminalGrpc.newBlockingStub(channel);

        TerminalProto.Request request = TerminalProto.Request.newBuilder()
                .setKey(key)
                .setContent0("cc0")
                .setContent1("cc1")
                .build();
        TerminalProto.Response response;

        response = stub.basicValidation(request);
        System.out.println("Response: " + response.getKey());
    }


}
