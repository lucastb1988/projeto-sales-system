package br.com.psgv.sale.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.com.psgv.sale.exceptions.FileException;

@Service
public class S3Service {
	
	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName;

	//método responsável para fazer um upload no AWS S3
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename(); //método extrai o nome do arquivo enviado
			InputStream is = multipartFile.getInputStream(); //InputStream: objeto básico de leitura do io, encapsula o objeto de leitura a partir de uma origem (multipartFile.getOriginalFilename())
			String contentType = multipartFile.getContentType(); //tipo do arquivo enviado

			return uploadFile(is, fileName, contentType);

		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {

		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);

			LOG.info("Iniciando upload");
			s3client.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload finalizado");

			return s3client.getUrl(bucketName, fileName).toURI();
			
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL em URI");
		}
	}
}
