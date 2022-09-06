package com.messages.mngr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") // NOSONAR
@RequestMapping("health")
public class HealthController {

    @GetMapping()
    public ResponseEntity<String> checkApp() {
        return ResponseEntity.ok("Ok");
    }
}
