package com.etcdserver;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class ETCDServer
{
    public static void main(String[] args)
    {
	SpringApplication app = new SpringApplication(ETCDServer.class);
	app.setBannerMode(Banner.Mode.OFF);
	app.run(args);
    }
}
