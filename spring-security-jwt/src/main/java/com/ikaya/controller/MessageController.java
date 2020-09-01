package com.ikaya.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
    @GetMapping
    //@Secured({"ROLE_USER"})
    public ResponseEntity getMessage() {
        return ResponseEntity.ok("Merhaba JWT");
    }
}
