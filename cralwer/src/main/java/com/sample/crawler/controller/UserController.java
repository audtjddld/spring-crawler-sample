package com.sample.crawler.controller;

import com.sample.crawler.controller.model.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class UserController {

    @PostMapping(path = "/user")
    public ResponseEntity<Void> post(@RequestBody @Valid UserRequest request) {
        log.info(">>>>>>>>>>>> {}", request);
        return ResponseEntity.ok().build();
    }
}
