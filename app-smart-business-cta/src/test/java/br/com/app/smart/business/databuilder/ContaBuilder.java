package br.com.app.smart.business.databuilder;

import br.com.app.smart.business.conta.dto.ContaDTO;
import br.com.app.smart.business.conta.dto.TipoContaDTO;
import br.com.app.smart.business.databuilder.RegistroAuditoriaBuilder.TipoRegistroAuditoriaBuilder;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;

public class ContaBuilder {

	public static ContaDTO getInstanceDTO(TipoContaBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarContaDTO();
		case CONTA_ASSINATURA:
			return criarContaAssinaturaDTO();
		default:
			break;
		}
		return criarContaDTO();
	}

	private static ContaDTO criarContaDTO() {

		RegistroAuditoriaDTO registroAuditoria = RegistroAuditoriaBuilder
				.getInstanceDTO(TipoRegistroAuditoriaBuilder.DEFAULT);

		ContaDTO dto = new ContaDTO();
		dto.setDescricao("descricao");
		dto.setTipoConta(TipoContaDTO.MASTER);
		dto.setRegistroAuditoria(registroAuditoria);

		return dto;

	}

	private static ContaDTO criarContaAssinaturaDTO() {

		RegistroAuditoriaDTO registroAuditoria = RegistroAuditoriaBuilder
				.getInstanceDTO(TipoRegistroAuditoriaBuilder.DEFAULT);

		ContaDTO dto = new ContaDTO();
		dto.setDescricao("descricao");
		dto.setTipoConta(TipoContaDTO.MASTER);
		dto.setRegistroAuditoria(registroAuditoria);

		return dto;

	}

	public static enum TipoContaBuilder {

		INSTANCIA, CONTA_ASSINATURA;
	}
}
