package com.github.atdi.news.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * Main class. This class is loaded by reflection
 * by spring-boot class loader.
 *
 * Created by aurelavramescu on 12/11/15.
 */
@EntityScan(basePackages = { "com.github.atdi.news.model" },
        basePackageClasses = { Bootstrap.class, Jsr310JpaConverters.class })
@SpringBootApplication
public class Bootstrap {

    /**
     * Main method.
     *
     * @param args input arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

}
