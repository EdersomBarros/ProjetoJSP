package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	
	private boolean useradmin;
	
	private String perfil;
	private String sexo;
	private String fotouser;
	private String extensaofotouser;
	
	
	
	
	
	public String getFotouser() {
		return fotouser;
	}
	public void setFotouser(String fotouser) {
		this.fotouser = fotouser;
	}
	public String getExtensaofotouser() {
		return extensaofotouser;
	}
	public void setExtensaofotouser(String extensaofotouser) {
		this.extensaofotouser = extensaofotouser;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getSexo() {
		return sexo;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getPerfil() {
		return perfil;
	}
	
	

	public boolean getUseradmin() {
		return useradmin;
	}

	public void setUseradmin(boolean useradmin) {
		this.useradmin = useradmin;
	}

	public boolean isNovo() {

		if (this.id == null) {
			return true;/*inserir novo*/
		} else if (this.id != null && this.id > 0) {
			return false;/*atualizar*/
		}
		return id == null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
