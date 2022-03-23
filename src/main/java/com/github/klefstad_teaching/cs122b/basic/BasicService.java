package com.github.klefstad_teaching.cs122b.basic;

import com.github.klefstad_teaching.cs122b.basic.config.BasicServiceConfig;
import com.github.klefstad_teaching.cs122b.core.spring.StackService;
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
