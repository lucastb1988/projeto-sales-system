package br.com.psgv.sale.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	//método para separar e retornar uma lista de (Integer) por , passando uma String (parametro na requisição)
	public static List<Integer> decodeIntList(String s) {
		/*List<Integer> list = new ArrayList<>();
		
		String[] vet = s.split(",");
		
		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		
		return list;*/
		
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	//método para decodificar um parametro passado na requisição (se o usuário der espaço por exemplo no parametro será decodificado da forma informada)
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
