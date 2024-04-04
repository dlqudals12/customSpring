package org.bmSpring.controller;

import org.bmSpring.annotations.*;

@Bean
@RequestMapping("/maps")
public class TestController {

    @GetMapping("/")
    public void test1() {

    }

    @GetMapping("/1")
    public void test2() {

    }

    @PatchMapping("/")
    public void test3() {

    }

    @DeleteMapping("/")
    public void test4() {

    }
}
