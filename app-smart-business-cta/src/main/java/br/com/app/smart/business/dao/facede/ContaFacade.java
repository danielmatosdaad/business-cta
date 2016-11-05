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
import br.com.app.smart.business.model.Conta;

@Stateless
public class ContaFacade extends AbstractFacade<Conta> {

	public ContaFacade() {
		super(Conta.class);
	}

	public ContaFacade(Class<Conta> entityClass) {
		super(entityClass);
	}
	

	@PersistenceContext(unitName = "persistencia-contexto-conta")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}


	public List<Conta> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Conta> criteria = cb.createQuery(Conta.class);
		Root<Conta> parametro = criteria.from(Conta.class);
		CriteriaQuery<Conta> todos = criteria.select(parametro);
		TypedQuery<Conta> allQuery = em.createQuery(todos);
		
		List<Conta> resultado = allQuery.getResultList();
		
		System.out.println("Quantidade todos? " + resultado.size());
		return resultado;
	}
	
	
}
