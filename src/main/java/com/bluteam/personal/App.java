package com.bluteam.personal;

import com.bluteam.personal.apigateway.Gateway;
import com.bluteam.personal.apigateway.Service;
import com.bluteam.personal.cache.CacheEntry;
import com.bluteam.personal.cache.CacheManager;
import com.bluteam.personal.cache.Student;
import com.bluteam.personal.loadbalance.LoadBalancer;
import com.bluteam.personal.ratelimit.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        LoadBalancer loadBalancer = new LoadBalancer();
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
       /* System.out.println("=====API Gateway Started===");
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
*/
/*        System.out.println("=====Rate Limit Started===");
        RateLimiter rateLimiter = new RateLimiter(5, 15);
        String[] users = {"user01", "user02", "user03", "user04"};

        ExecutorService executor = Executors.newFixedThreadPool(users.length);

        for (int i = 0; i < 100; i++) {
            final int requestNumber = i;
            executor.submit(() -> {
                String user = users[requestNumber % 4]; // user1, user2, user3, user4
                if (rateLimiter.isAllowed(user)) {
                    System.out.println("Request " + requestNumber + " from " + user + " - ALLOWED");
                } else {
                    System.out.println("Request " + requestNumber + " from " + user + " - BLOCKED");
                }
            });
            Thread.sleep(300);
        }
        executor.shutdown();*/


        System.out.println("=====Cache Aside Strategy Started===");
        Student[] students = {new Student(String.valueOf(1), "student01")
            , new Student(String.valueOf(2), "student02")
            , new Student(String.valueOf(3), "student03")
            , new Student(String.valueOf(4), "student04")
            , new Student(String.valueOf(5), "student05")
            , new Student(String.valueOf(6), "student06")
            , new Student(String.valueOf(7), "student07")
            , new Student(String.valueOf(8), "student08")};

        CacheManager cacheManager = new CacheManager();
        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {
            for (int i = 0; i < 100; i++) {
                final int requestNumber = i;
                executorService.submit(() -> {
                    var student = students[requestNumber % 8];
                    var cachedStudent = cacheManager.get(student.getId());
                    if (cachedStudent == null) {
                        cacheManager.put(student.getId(), student);
                        System.out.println("Student " + student.getName() + " fetch by database");
                    } else {
                        if (!cacheManager.isExpired(cachedStudent)) {
                            System.out.println("Cached Student " + cachedStudent.getValue() + " fetch by caching system");
                        } else {
                            System.out.println("Student " + student.getName() + " fetch by database");
                            cacheManager.remove(student.getId());
                            cacheManager.put(student.getId(), student);

                        }
                    }
                });
                Thread.sleep(300);
            }
        }
    }
}
