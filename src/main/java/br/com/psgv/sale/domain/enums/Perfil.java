package br.com.psgv.sale.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"), // ROLE_ADMIN padrão do Spring
	CLIENTE(2, "ROLE_CLIENTE"); // ROLE_CLIENTE padrão do Spring

	private Integer codigo;
	private String descricao;

	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer codigo) {

		if (codigo == null) {
			return null;
		}

		for (Perfil perfil : Perfil.values()) {
			if (perfil.getCodigo().equals(codigo)) {
				return perfil;
			}
		}

		throw new IllegalArgumentException("Código inválido: " + codigo);
	}
}
