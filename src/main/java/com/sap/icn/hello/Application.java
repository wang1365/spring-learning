package com.sap.icn.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by I321761 on 2017/5/22.
 */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final String URL = "http://gturnquist-quoters.cfapps.io/api/random";
    private static String PROXY_HOST = "proxy.pal.sap.corp";
    private static int PROXY_PORT = 8080;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    SimpleClientHttpRequestFactory httpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT)));
        return factory;
    }

    @Bean
    RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            Quote quote = restTemplate.getForObject(URL, Quote.class);
            logger.info(quote.toString());
        };
    }

    @Override
    public void run(String... args) throws Exception {
        // Consuming REST API
        Quote quote = restTemplate.getForObject(URL, Quote.class);
        logger.info(quote.toString());

        // Jdbc
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers (id serial, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> names = Arrays.asList("Xiaochuan Wang", "Shun Xu", "Yun Ma", "Huateng Ma")
                .stream()
                .map(v -> v.split(" "))
                
                .collect(Collectors.toList());

        names.forEach(name -> logger.info(String.format("Insert name: %s %s", name[0], name[1])));

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) values (?, ?)", names);

        logger.info("Query records from db");
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers",
                (rs, rowNum) -> new Customer(rs.getInt(1), rs.getNString(2), rs.getNString(3))
        ).forEach(customer -> logger.info(customer.toString()));
    }
}
