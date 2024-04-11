package org.bmSpring.controller;

import org.bmSpring.annotations.component.Controller;
import org.bmSpring.annotations.mapping.*;
import org.bmSpring.servlet.enums.HttpType;

@Controller
@RequestMapping("/maps")
public class TestController {

    @GetMapping("/")
    public void test1() {

    }

    @PostMapping("/1")
    public void test2() {

    }

    @PatchMapping("/")
    public void test3() {

    }

    @DeleteMapping("/")
    public void test4() {

    }

    @RequestMapping(value = "/requet", type = HttpType.PATCH)
    public void test43() {

    }
}
