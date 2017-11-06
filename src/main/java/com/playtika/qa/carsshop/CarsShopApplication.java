package com.playtika.qa.carsshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration;


@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        EndpointMBeanExportAutoConfiguration.class,
        FreeMarkerAutoConfiguration.class,
        H2ConsoleAutoConfiguration.class,
        JmxAutoConfiguration.class,
        MetricRepositoryAutoConfiguration.class,
        SpringApplicationAdminJmxAutoConfiguration.class,
        AuditAutoConfiguration.class,
        EndpointAutoConfiguration.class,
        MetricExportAutoConfiguration.class,
        PublicMetricsAutoConfiguration.class,
        WebClientAutoConfiguration.class,
        HealthIndicatorAutoConfiguration.class

})
public class CarsShopApplication {

    public static void main(String[] args) {

        SpringApplication.run(CarsShopApplication.class, args);
    }
}
