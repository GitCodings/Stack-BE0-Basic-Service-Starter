package com.gitcodings.stack.basic.rest;

import com.gitcodings.stack.basic.config.BasicServiceConfig;
import com.gitcodings.stack.basic.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController
{
    private final BasicServiceConfig config;
    private final Validate           validate;

    @Autowired
    public BasicController(BasicServiceConfig config,
                           Validate validate)
    {
        this.config = config;
        this.validate = validate;
    }
}
