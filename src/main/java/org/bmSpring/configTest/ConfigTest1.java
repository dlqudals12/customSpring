package org.bmSpring.configTest;

import org.bmSpring.annotations.bean.Bean;
import org.bmSpring.annotations.component.Configuration;

@Configuration
public class ConfigTest1 {

    @Bean
    public ConfigTestDto1 configTestDto1() {
        return new ConfigTestDto1();
    }

    @Bean
    public ConfigTestDto2 configTestDto2() {
        return new ConfigTestDto2();
    }

    @Bean
    public ConfigTestDto3 configTestDto3() {
        return new ConfigTestDto3();
    }
}
