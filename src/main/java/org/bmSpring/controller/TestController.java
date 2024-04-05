package org.bmSpring.controller;

import org.bmSpring.annotations.component.Controller;
import org.bmSpring.annotations.mapping.DeleteMapping;
import org.bmSpring.annotations.mapping.GetMapping;
import org.bmSpring.annotations.mapping.PatchMapping;
import org.bmSpring.annotations.mapping.RequestMapping;

@Controller
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
