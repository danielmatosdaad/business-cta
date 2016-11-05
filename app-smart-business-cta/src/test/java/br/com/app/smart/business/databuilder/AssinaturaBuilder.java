package br.com.app.smart.business.databuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.app.smart.business.conta.dto.AssinaturaDTO;
import br.com.app.smart.business.conta.dto.ContaDTO;
import br.com.app.smart.business.conta.dto.ContratoDTO;
import br.com.app.smart.business.databuilder.ContaBuilder.TipoContaBuilder;
import br.com.app.smart.business.databuilder.ContratoBuilder.TipoContratoBuilder;
import br.com.app.smart.business.databuilder.RegistroAuditoriaBuilder.TipoRegistroAuditoriaBuilder;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;

public class AssinaturaBuilder {

	public static AssinaturaDTO getInstanceDTO(TipoAssinaturaBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarAssinaturaDTO();
		case ASSINATURA_CONTRATO_CONTA:

			return criarAssinaturaContratoContaDTO();
		default:
			break;
		}
		return criarAssinaturaDTO();
	}

	private static AssinaturaDTO criarAssinaturaDTO() {

		RegistroAuditoriaDTO registroAuditoria = RegistroAuditoriaBuilder
				.getInstanceDTO(TipoRegistroAuditoriaBuilder.DEFAULT);

		AssinaturaDTO dto = new AssinaturaDTO();
		dto.setRegistroAuditoria(registroAuditoria);

		return dto;

	}

	private static AssinaturaDTO criarAssinaturaContratoContaDTO() {

		RegistroAuditoriaDTO registroAuditoria = RegistroAuditoriaBuilder
				.getInstanceDTO(TipoRegistroAuditoriaBuilder.DEFAULT);

		List<ContratoDTO> contratos = new ArrayList<ContratoDTO>();
		ContratoDTO contratoDTO = ContratoBuilder.getInstanceDTO(TipoContratoBuilder.INSTANCIA);
		contratos.add(contratoDTO);

		ContaDTO contaDTO = ContaBuilder.getInstanceDTO(TipoContaBuilder.INSTANCIA);

		List<ContaDTO> contas = new ArrayList<ContaDTO>();
		contas.add(contaDTO);

		AssinaturaDTO dto = new AssinaturaDTO();
		dto.setRegistroAuditoria(registroAuditoria);
		dto.setContratos(contratos);
		dto.setContas(contas);
		
		return dto;

	}

	public static enum TipoAssinaturaBuilder {

		INSTANCIA, ASSINATURA_CONTRATO_CONTA;
	}
}
