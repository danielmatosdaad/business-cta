package br.com.app.smart.business.service;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.appsmartbusiness.persistencia.service.ServiceDAO;
import br.com.app.smart.business.conta.dto.AssinaturaDTO;
import br.com.app.smart.business.dao.facede.AssinaturaFacade;
import br.com.app.smart.business.dao.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.model.Assinatura;

@Stateless
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class AssinaturaServiceImp implements IServicoRemoteDAO<AssinaturaDTO>, IServicoLocalDAO<AssinaturaDTO> {

	@Inject
	private Logger log;

	@EJB
	private AssinaturaFacade assinaturaFacade;

	@Override
	public AssinaturaDTO adiconar(AssinaturaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			return ServiceDAO.adiconar(this.assinaturaFacade, Assinatura.class, dto);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<AssinaturaDTO> adiconar(List<AssinaturaDTO> listaDto) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.adiconar(this.assinaturaFacade, Assinatura.class, listaDto);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public AssinaturaDTO alterar(AssinaturaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.alterar(this.assinaturaFacade, Assinatura.class, dto);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public void remover(AssinaturaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			
			ServiceDAO.remover(this.assinaturaFacade, Assinatura.class, dto);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}

	}

	@Override
	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
	

	}

	@Override
	public List<AssinaturaDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.bustarTodos(this.assinaturaFacade, AssinaturaDTO.class);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public AssinaturaDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.bustarPorID(this.assinaturaFacade, AssinaturaDTO.class,id);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<AssinaturaDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			
			return ServiceDAO.bustarPorIntervaloID(this.assinaturaFacade, AssinaturaDTO.class,range);

		} catch (Exception e) {
			
			throw new InfraEstruturaException(e);

		}
	}

}
