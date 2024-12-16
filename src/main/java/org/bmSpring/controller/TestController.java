package org.bmSpring.controller;

import org.bmSpring.annotations.component.Controller;
import org.bmSpring.annotations.mapping.*;
import org.bmSpring.annotations.parameter.RequestParam;
import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.model.HttpServletRequest;
import org.bmSpring.servlet.model.HttpServletResponse;

@Controller
@RequestMapping("/maps")
public class TestController {

    @GetMapping("/")
    public String test1() {
        return "SUCCESS";
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
    public TestDto test43(@RequestParam String name, HttpServletRequest request, @RequestParam(required = false) String path, HttpServletResponse response) throws InterruptedException {
        System.out.println("REQUEST: " + request.getHost());
        System.out.println("RESPONSE: " + response.getContentType());
        System.out.println("PATH: " + path);
        System.out.println("NAME: " + name);
        System.out.printf("INVOKE : -> %s %s \n", name, path);
        return new TestDto(name);
    }
}
