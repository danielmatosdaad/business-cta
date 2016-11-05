package br.com.app.smart.business.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.app.smart.business.dao.interfaces.Entidade;

@Entity
@XmlRootElement
@Table(name = "conta")
public class Conta implements Entidade, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoConta tipoConta;

	@ManyToMany
	private List<Assinatura> assinaturas;

	private RegistroAuditoria registroAuditoria;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {

		this.id = id;

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public List<Assinatura> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(List<Assinatura> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public RegistroAuditoria getRegistroAuditoria() {
		return registroAuditoria;
	}

	public void setRegistroAuditoria(RegistroAuditoria registroAuditoria) {
		this.registroAuditoria = registroAuditoria;
	}

}
