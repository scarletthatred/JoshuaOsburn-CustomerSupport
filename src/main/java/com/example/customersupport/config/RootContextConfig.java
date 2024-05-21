package com.example.customersupport.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
@Configuration
@ComponentScan(basePackages = "com.example.customersupport.site",
                excludeFilters = @ComponentScan.Filter(Controller.class))
public class RootContextConfig {
}
