package com.facemind.app.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/health")
    public String healthCheck(){
        return "HEALTHY!";
    }

    @GetMapping("/checkAPI")
    public String checkAPI(){
        return "당신은 무중단 CI/CD에 성공했습니다. 미쳤다리";
    }


}
