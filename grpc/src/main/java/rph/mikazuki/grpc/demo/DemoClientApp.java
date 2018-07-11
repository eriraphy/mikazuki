package rph.mikazuki.grpc.demo;

import java.util.ArrayList;

public class DemoClientApp {

    public static void main(String[] args) throws Exception {
        DemoClient client = new DemoClient("localhost", 8735);

        try {
            client.basicValidation("test", "basic", "testString");
            client.serverStreamingValidation("test", "server streaming", "testString");
            ArrayList<String> keys = new ArrayList<String>() {{
                add("test0");
                add("test1");
            }};
            ArrayList<String> content0 = new ArrayList<String>() {{
                add("client streaming");
                add("client streaming");
            }};
            ArrayList<String> content1 = new ArrayList<String>() {{
                add("testString0");
                add("testString1");
            }};
            client.clientStreamingValidation(keys, content0, content1);
        } finally {
            client.shutdown();
        }

    }
}
