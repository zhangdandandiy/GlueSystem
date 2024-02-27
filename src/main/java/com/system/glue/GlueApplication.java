package com.system.glue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GlueApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlueApplication.class, args);
        System.out.println("O(∩_∩)O    启动成功    哈哈~    \n" +
                " .---     ---.         .---.             \n" +
                " |  |     |  |        /     \\           \n" +
                " |  |     |  |       /  /`\\  \\         \n" +
                " |  |_____|  |      /  /___\\  \\        \n" +
                " |  |_____|  |     /  /_____\\  \\       \n" +
                " |  |     |  |    /  /       \\  \\      \n" +
                " |  |     |  |   /  /         \\  \\     \n" +
                " |__|     |__|  /__/           \\__\\    \n");
    }

}
