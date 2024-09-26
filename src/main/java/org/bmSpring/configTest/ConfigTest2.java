package org.bmSpring.configTest;

import org.bmSpring.annotations.bean.Bean;
import org.bmSpring.annotations.component.Configuration;

@Configuration
public class ConfigTest2 {

    @Bean
    public ConfigTestDto4 configTestDto4() {
        return new ConfigTestDto4();
    }

    @Bean
    public ConfigTestDto5 configTestDto5() {
        return new ConfigTestDto5();
    }

    @Bean(name = "config123")
    public ConfigTestDto3 configTestDto3() {
        return new ConfigTestDto3();
    }
}