package br.com.app.smart.business.dao.facede;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.appsmartbusiness.persistencia.dao.facede.AbstractFacade;
import br.com.app.smart.business.model.Contrato;

@Stateless
public class ContratoFacade extends AbstractFacade<Contrato> {

	
	public ContratoFacade() {
		super(Contrato.class);
	}
	
	public ContratoFacade(Class<Contrato> entityClass) {
		super(entityClass);
	}

	@PersistenceContext(unitName = "persistencia-contexto-conta")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}
	
	public List<Contrato> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Contrato> criteria = cb.createQuery(Contrato.class);
		Root<Contrato> parametro = criteria.from(Contrato.class);
		CriteriaQuery<Contrato> todos = criteria.select(parametro);
		TypedQuery<Contrato> allQuery = em.createQuery(todos);
		
		List<Contrato> resultado = allQuery.getResultList();
		
		System.out.println("Quantidade Contratatos: " + resultado.size());
		return resultado;
	}

}
