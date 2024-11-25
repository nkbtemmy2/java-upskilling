package com.example.spring_basics;

import com.example.spring_basics.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Registers this class as a Spring-managed component
public class AppRunner implements CommandLineRunner {

    private final MessageService messageService;

    // Constructor-based dependency injection
    @Autowired
    public AppRunner(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(messageService.getMessage());
    }
}