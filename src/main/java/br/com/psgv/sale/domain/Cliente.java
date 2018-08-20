package br.com.psgv.sale.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.psgv.sale.domain.enums.Perfil;
import br.com.psgv.sale.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
    
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	private String cpfOuCnpj;
	
	private Integer tipo;
	
	@JsonIgnore
	private String senha;
	
	//Sempre observar relacionamento um para muitos, se é necessário barrar ou nao quando deletar a entidade principal 
	//(neste caso sempre que deletar a entidade Cliente, será deletado também a entidade de Endereço)
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) //qualquer operação que for relacionada a cliente será deletada juntamente, neste caso Endereco será deletado automaticamente caso cliente for deletado
	private List<Endereco> enderecos = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>();
	
	//garantir que assim que gerar o banco de dados da tabela Cliente automaticamente sejam carregados os perfis também
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIL")
	private Set<Integer> perfis = new HashSet<>();
	
	//Sempre observar relacionamento um para muitos, se é necessário barrar ou nao quando deletar a entidade principal 
	//(neste caso sempre que deletar a entidade Cliente, será barrado a entidade de Pedido para que dê erro caso for deletado um Cliente com Pedido)
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	//sempre que instanciar um cliente o mesmo terá como perfil CLIENTE
	public Cliente() {
		addPerfil(Perfil.CLIENTE);
	}

	//sempre que instanciar um cliente o mesmo terá como perfil CLIENTE
	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo == null ? null : tipo.getCodigo();
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	//Transforma todos os perfis encontrados em um set de inteiros
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCodigo());
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
