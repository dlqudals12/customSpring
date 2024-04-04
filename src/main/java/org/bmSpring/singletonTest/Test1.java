package org.bmSpring.singletonTest;

import org.bmSpring.annotations.Bean;

@Bean
public class Test1 {

    private final Test2 test2;


    public Test1(Test2 test2) {
        this.test2 = test2;
    }

    public void doTest2() {
        System.out.println("Test2 method 들어옴");
        test2.test();
    }
}
