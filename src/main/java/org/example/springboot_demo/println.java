package org.example.springboot_demo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class println {
    public void print(){
        System.out.println("this");
    }
    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }
}
