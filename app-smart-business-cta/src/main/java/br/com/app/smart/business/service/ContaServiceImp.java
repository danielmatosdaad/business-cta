package br.com.app.smart.business.service;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.appsmartbusiness.persistencia.service.ServiceDAO;
import br.com.app.smart.business.conta.dto.ContaDTO;
import br.com.app.smart.business.dao.facede.ContaFacade;
import br.com.app.smart.business.dao.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.model.Conta;

@Stateless
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class ContaServiceImp implements IServicoRemoteDAO<ContaDTO>, IServicoLocalDAO<ContaDTO> {

	@Inject
	private Logger log;

	@EJB
	ContaFacade contaFacade;

	@Override
	public ContaDTO adiconar(ContaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.adiconar(this.contaFacade, Conta.class, dto);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<ContaDTO> adiconar(List<ContaDTO> listaDto) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.adiconar(this.contaFacade, Conta.class, listaDto);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public ContaDTO alterar(ContaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.alterar(this.contaFacade, Conta.class, dto);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public void remover(ContaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			ServiceDAO.remover(this.contaFacade, Conta.class, dto);

		} catch (Exception e) {
			throw new InfraEstruturaException(e);

		}

	}

	@Override
	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
	

	}

	@Override
	public List<ContaDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.bustarTodos(this.contaFacade, ContaDTO.class);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public ContaDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.bustarPorID(this.contaFacade, ContaDTO.class,id);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<ContaDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.bustarPorIntervaloID(this.contaFacade, ContaDTO.class,range);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

}
