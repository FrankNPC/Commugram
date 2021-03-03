package org.commugram.relay;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan("org.commugram.relay.websocket")
@ImportResource({"classpath*:spring-*.xml"})
public class CommugramWebsocketRelaySpringApplicationBoot {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CommugramWebsocketRelaySpringApplicationBoot.class, args);
    }
    
}
