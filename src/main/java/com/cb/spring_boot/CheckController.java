package com.cb.spring_boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckController {

    @Autowired
    CheckService checkService;

    @GetMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }

    @PostMapping(value="/check")
    public ResponseEntity<Object> check(@RequestBody Check check) {
        Check checked = checkService.isValid(check);
        return new ResponseEntity<>(checked, checked.getValid()?HttpStatus.OK:HttpStatus.UNAUTHORIZED);
    }


}
