package org.bmSpring.controller;

import org.bmSpring.annotations.component.Controller;
import org.bmSpring.annotations.mapping.*;
import org.bmSpring.annotations.parameter.RequestParam;
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

    @RequestMapping(value = "/request", type = HttpType.GET)
    public TestDto test43(@RequestParam String name) throws InterruptedException {
        System.out.printf("INVOKE : -> %s \n", name);
        Thread.sleep(10000);
        return new TestDto(name);
    }
}
