package com.kimyoong.practice.controller;

import com.kimyoong.practice.common.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
public class TestController {


    @GetMapping("/test")
    public ResponseEntity<RestResponse> getTest(){
            return ResponseEntity.ok(RestResponse.ok());
    }
}
