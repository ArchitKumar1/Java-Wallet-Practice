package practice.grpc;


import com.application.genproto.GreeterGrpc;
import com.application.genproto.HelloReply;
import com.application.genproto.HelloRequest;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class main {


    public static void main(String[] args) {

        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(getGRPCChannel());
        HelloReply helloReply = stub.sayHello(getRequest());
        System.out.println(helloReply.toString());

    }

    private static HelloRequest getRequest() {
        return HelloRequest.newBuilder()
                .setAge(24)
                .setName("123")
                .build();
    }


    public static ManagedChannel getGRPCChannel() {
        try {
            List<ClientInterceptor> interceptors = new ArrayList<>();

            String target = "localhost:9001";
            ManagedChannelBuilder builder = ManagedChannelBuilder
                    .forTarget(target)
                    .defaultLoadBalancingPolicy("pick_first")
                    .intercept(interceptors);
            ManagedChannel channel = builder.build();
            return channel;
        } catch (Exception e) {
            log.error("Error creating channel", e);
            throw e;
        }

    }
}
