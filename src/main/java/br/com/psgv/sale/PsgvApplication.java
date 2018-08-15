package br.com.psgv.sale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PsgvApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PsgvApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
