package br.com.app.smart.business.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.app.smart.business.dao.interfaces.Entidade;

@Entity
@XmlRootElement
@Table(name = "contrato")
public class Contrato implements Entidade, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String clausula;

	@ManyToMany(mappedBy = "contratos")
	private List<Assinatura> assinaturas;

	private RegistroAuditoria registroAuditoria;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public List<Assinatura> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(List<Assinatura> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public String getClausula() {
		return clausula;
	}

	public void setClausula(String clausula) {
		this.clausula = clausula;
	}

	public RegistroAuditoria getRegistroAuditoria() {
		return registroAuditoria;
	}

	public void setRegistroAuditoria(RegistroAuditoria registroAuditoria) {
		this.registroAuditoria = registroAuditoria;
	}

}
