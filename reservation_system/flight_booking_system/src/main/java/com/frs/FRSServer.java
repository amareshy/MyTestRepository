package com.frs;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages =
{ "com.cassandradb.client", "com.frs", "com.etcd.client.client" })
@EnableAutoConfiguration
public class FRSServer
{
    public static void main(String[] args)
    {
	SpringApplication app = new SpringApplication(FRSServer.class);
	app.setBannerMode(Banner.Mode.OFF);
	app.run(args);
    }

}
