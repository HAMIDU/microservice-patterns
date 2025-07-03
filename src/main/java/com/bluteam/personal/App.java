package com.bluteam.personal;

import com.bluteam.personal.apigateway.Gateway;
import com.bluteam.personal.apigateway.Service;
import com.bluteam.personal.loadbalancer.LoadBalance;
import com.bluteam.personal.loadbalancer.Server;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        LoadBalance loadBalance = new LoadBalance();
        /*System.out.println("=====Load Balancer Started===");

        Server serverA = new Server(1, "A", "127.0.0.1", 90);
        Server serverB = new Server(1, "B", "127.0.0.2", 90);
        Server serverC = new Server(1, "C", "127.0.0.3", 90);
        loadBalance.addServer(serverA);
        loadBalance.addServer(serverB);
        loadBalance.addServer(serverC);


        for (int i = 0; i < 10; i++) {
            Server server = loadBalance.chooseServer();
            System.out.println("Request Number " + i + " took the server " + server);
        }
*/
        System.out.println("=====API Gateway Started===");
        Gateway gateway = new Gateway();

        Service userService = new Service("UserService", "/users/*");
        Service orderService = new Service("OrderService", "/orders/*");
        Service productService = new Service("ProductService", "/products/*");

        gateway.registerService(userService);
        gateway.registerService(orderService);
        gateway.registerService(productService);

        String[] path = {"/users/123",
            "/orders/456",
            "/products/789",
            "/users/login"};

        for (int i = 0; i < path.length; i++) {
            Service service = gateway.routeRequest(path[i]);
            if (service != null) {
                System.out.println("Input request " + path[i] + " routed into service " + service.getName());
            } else {
                System.out.println("Input request " + path[i] + " not found");
            }
        }
    }
}
