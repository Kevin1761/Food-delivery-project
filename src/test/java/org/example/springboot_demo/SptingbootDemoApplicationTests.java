package org.example.springboot_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootTest
class SpringbootDemoApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Scanner scanner;

    @Test
    void test_print() {
        println pr =(println)applicationContext.getBean(println.class);
        System.out.println(pr);
        pr.print();
    }
    @Test
    void test_scanner(){
        Object target = applicationContext.getBean("scanner");
        System.out.println(target);


    }

}
