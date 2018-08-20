package br.com.psgv.sale.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
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
	//necessita criar este método para passar para AWS S3
	public InputStream getInputStream(BufferedImage img, String extensao) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extensao, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (Exception e) {
			throw new FileException("Erro ao ler arquivo.");
		}
	}
	
	//recortar imagem de forma que ela fique quadrada
	public BufferedImage cropSquare(BufferedImage sourceImg) {
		//descobrir se é a largura ou altura a parte menor da imagem
		int minimo = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight(): sourceImg.getWidth();
		
		return Scalr.crop(
				sourceImg, 
				(sourceImg.getWidth()/2) - (minimo/2), //metade da largura menos a metade do minimo
				(sourceImg.getHeight()/2) - (minimo/2), //metade da altura menos a metade do minimo 
				minimo, //quanto quer recortar para largura (será o minimo)
				minimo); //quanto quer recortar para altura (será o minimo)
	}
	
	//função para redimensionar uma imagem
	//irá ajustar o recorte para ficarem iguais largura e altura (ex: 200 por 200)
	public BufferedImage resize(BufferedImage sourceImg, int size) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}
}
