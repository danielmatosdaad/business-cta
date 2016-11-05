package br.com.app.smart.business.databuilder;

import br.com.app.smart.business.conta.dto.ContratoDTO;
import br.com.app.smart.business.databuilder.RegistroAuditoriaBuilder.TipoRegistroAuditoriaBuilder;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;

public class ContratoBuilder {

	public static ContratoDTO getInstanceDTO(TipoContratoBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarContratoDTO();
		case CONTRATO_ASSINATURA:
		default:
			break;
		}
		return criarContratoDTO();
	}

	private static ContratoDTO criarContratoDTO() {
		
		RegistroAuditoriaDTO registroAuditoria = RegistroAuditoriaBuilder.getInstanceDTO(TipoRegistroAuditoriaBuilder.DEFAULT);
		
		ContratoDTO dto = new ContratoDTO();
		dto.setClausula("clausula1,clauula2");
		dto.setRegistroAuditoria(registroAuditoria);

		return dto;

	}

	public static enum TipoContratoBuilder {

		INSTANCIA, CONTRATO_ASSINATURA;
	}
}
