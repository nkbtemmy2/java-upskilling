package com.example.spring_basics.services;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    /**
     * This function return string
     * @return
     */
    public String getMessage() {
        return "Hello from the MessageService!";
    }
}
