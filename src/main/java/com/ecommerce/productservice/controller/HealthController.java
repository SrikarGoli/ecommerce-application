package com.ecommerce.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/test")
    public String test(){
        return "Api is working";
    }
}
