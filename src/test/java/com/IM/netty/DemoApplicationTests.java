package com.IM.netty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        String strDate2 = dtf2.format(localDateTime);
        System.out.println("template:"+strDate2);
        Stream.of(strDate2).forEach(System.out::println);

    }

}
