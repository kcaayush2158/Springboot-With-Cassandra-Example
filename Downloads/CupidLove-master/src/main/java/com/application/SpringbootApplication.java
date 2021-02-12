package com.application;

import com.pusher.rest.Pusher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collections;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootApplication.class, args);

        Pusher pusher = new Pusher("942339", "fe6ae4d627b4ab84c8d9", "24772a863476b96756cc");
        pusher.setCluster("ap2");
        pusher.setEncrypted(true);

        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "hello jsss"));
    }

}
