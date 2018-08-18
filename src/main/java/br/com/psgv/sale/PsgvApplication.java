package br.com.psgv.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.psgv.sale.services.S3Service;

@SpringBootApplication
public class PsgvApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;

    public static void main(String[] args) {
        SpringApplication.run(PsgvApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    	s3Service.uploadFile("C:\\temp\\man.jpg");
    }
}
