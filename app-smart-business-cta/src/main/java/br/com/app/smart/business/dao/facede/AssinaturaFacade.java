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
import br.com.app.smart.business.model.Assinatura;

@Stateless
public class AssinaturaFacade extends AbstractFacade<Assinatura> {

	public AssinaturaFacade() {
		super(Assinatura.class);
	}

	public AssinaturaFacade(Class<Assinatura> entityClass) {
		super(entityClass);
	}

	@PersistenceContext(unitName = "persistencia-contexto-conta")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}
	
	public List<Assinatura> buscarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Assinatura> criteria = cb.createQuery(Assinatura.class);
		Root<Assinatura> parametro = criteria.from(Assinatura.class);
		CriteriaQuery<Assinatura> todos = criteria.select(parametro);
		TypedQuery<Assinatura> allQuery = em.createQuery(todos);
		
		List<Assinatura> resultado = allQuery.getResultList();
		
		System.out.println("Quantidade Assinatura: " + resultado.size());
		return resultado;
	}

}
