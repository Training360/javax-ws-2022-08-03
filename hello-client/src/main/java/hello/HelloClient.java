package hello;

import com.learnwebservices.services.hello.HelloEndpointService;
import com.learnwebservices.services.hello.HelloRequest;

public class HelloClient {

    public static void main(String[] args) {
        var service = new HelloEndpointService();
        var port = service.getHelloEndpointPort();

        var request = new HelloRequest();
        request.setName("Training");

        var response = port.sayHello(request);

        System.out.println(response.getMessage());
    }
}
