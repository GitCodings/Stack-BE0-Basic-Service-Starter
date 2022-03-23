package com.github.klefstad_teaching.cs122b.basic.rest;

import com.github.klefstad_teaching.cs122b.basic.config.BasicServiceConfig;
import com.github.klefstad_teaching.cs122b.basic.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController
{
    private final BasicServiceConfig config;
    private final Validate validate;

    @Autowired
    public BasicController(BasicServiceConfig config,
                           Validate validate)
    {
        this.config = config;
        this.validate = validate;
    }
}
