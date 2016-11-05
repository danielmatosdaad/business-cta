package br.com.app.smart.business.databuilder;

import java.util.Date;

import br.com.app.smart.business.dto.RegistroAuditoriaDTO;
import br.com.app.smart.business.parametro.dto.ParametroDTO;

public class ParametroBuilder {

	public static ParametroDTO getInstanceDTO(TipoParametroBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarParametroDTO();

		default:
			break;
		}
		return criarParametroDTO();
	}

	private static ParametroDTO criarParametroDTO() {
		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
		r.setDataCadastro(new Date());

		ParametroDTO dto = new ParametroDTO();

		return dto;

	}

	public static enum TipoParametroBuilder {

		INSTANCIA;
	}
}
