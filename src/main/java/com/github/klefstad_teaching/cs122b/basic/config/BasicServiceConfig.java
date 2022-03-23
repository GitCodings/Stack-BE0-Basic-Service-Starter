package com.github.klefstad_teaching.cs122b.basic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "basic")
public class BasicServiceConfig
{
    private final String greetingMessage;

    public BasicServiceConfig(String greetingMessage)
    {
        this.greetingMessage = greetingMessage;
    }

    public String greetingMessage()
    {
        return greetingMessage;
    }
}
