package org.commugram.relay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring-*.xml"})
public class CommugramRelayServiceSpringApplicationBoot {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CommugramRelayServiceSpringApplicationBoot.class, args);
    }

}
