package org.customizedquery.example.client;

import org.customizedquery.example.server.example.api.DemoUserApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Jover Zhang
 */
@EnableFeignClients(clients = DemoUserApi.class)
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}
