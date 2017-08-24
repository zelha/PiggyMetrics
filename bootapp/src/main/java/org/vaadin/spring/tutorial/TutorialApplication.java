package org.vaadin.spring.tutorial;

import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSConfigurationException;
import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.util.Properties;

@SpringBootApplication
@EnableResourceServer  //oauth2
@EnableDiscoveryClient //eureka
@Configuration
public class TutorialApplication  extends SpringBootServletInitializer {


    @Autowired
    private ResourceServerProperties sso;


    public static void main(String[] args) {
        SpringApplication.run(TutorialApplication.class, args);
    }




    @Bean
    public CORSFilter corsFilter() {
        CORSFilter filter ;
        Properties defaultCorsConfiguration = new Properties();
        defaultCorsConfiguration.setProperty("cors.allowOrigin","*");
        defaultCorsConfiguration.setProperty("cors.supportedMethods", "GET, POST, HEAD, OPTIONS");
        //NOTE : it is possible to load it from an XML or else.
        try {
           filter =  new CORSFilter(new CORSConfiguration(defaultCorsConfiguration));
        } catch (CORSConfigurationException e) {
            e.printStackTrace();
            filter = new CORSFilter();
        }
        return filter;
    }

    @Bean
    public ServletContextInitializer sessionCookieConfigListener() {
        return context -> {
            context.getSessionCookieConfig().setHttpOnly(false);
            context.getSessionCookieConfig().setName("bootapp-session-id3");

        };
    }


}
