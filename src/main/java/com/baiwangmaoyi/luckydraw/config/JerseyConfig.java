package com.baiwangmaoyi.luckydraw.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.baiwangmaoyi.luckydraw.rest.NY2017Resource;

@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(NY2017Resource.class);
    }

}
