package com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller created to see the properties inside config-server being dynamically updated.
 * In this case, I only wanted to see the property authuser.refreshscope.name
 * Every time I change some property on config-server, I push it to Github
 * and make a call at one of the Spring Acuator's endpoints, in this case /actuator/refresh.
 * Remember that very complex properties like: database/rabbit/... connection the actuator can't update so easily
 */
@RestController
@RefreshScope
public class RefreshScopeController {

    @Value("${authuser.refreshscope.name}")
    private String name;

    @RequestMapping("/refreshscope")
    public String refreshscope() {
        return this.name;
    }

}
