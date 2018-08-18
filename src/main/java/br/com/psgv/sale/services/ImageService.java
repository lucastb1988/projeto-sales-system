package br.com.psgv.sale.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.psgv.sale.exceptions.FileException;

@Service
public class ImageService {
	
	//BufferedImage = jpg
	//recuperar imagem jpg do arquivo
	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String extensao = FilenameUtils.getExtension(uploadedFile.getOriginalFilename()); //pegar extensão do arquivo a ser enviado
		if (!"png".equals(extensao) && !"jpg".equals(extensao)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas.");
		}
		
		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream()); //tentar obter um BufferedImage a partir do arquivo a ser enviado
			if ("png".equals(extensao)) { //se for imagem png converte em jpg
				img = pngToJpg(img);
			}
			
			return img;
			
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo.");
		}
	}

	//método para converter imagem PNG em JPG
	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		
		return jpgImage;
	}

	//InputStream = objeto que encapsula leitura
	//recuperar inputStream passando img e extensao
	public InputStream getInputStream(BufferedImage img, String extensao) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extensao, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (Exception e) {
			throw new FileException("Erro ao ler arquivo.");
		}
	}
}
