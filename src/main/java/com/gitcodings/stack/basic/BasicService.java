package com.gitcodings.stack.basic;

import com.gitcodings.stack.basic.config.BasicServiceConfig;
import com.gitcodings.stack.core.spring.StackService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@StackService
@EnableConfigurationProperties({
    BasicServiceConfig.class
})
public class BasicService
{
    public static void main(String[] args)
    {
        SpringApplication.run(BasicService.class, args);
    }
}
