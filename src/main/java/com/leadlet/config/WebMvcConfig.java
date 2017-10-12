package com.leadlet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebMvcConfig extends WebMvcConfigurerAdapter {
    public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/**").setViewName("forward:/");
	}
}
