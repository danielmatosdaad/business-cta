package br.com.app.smart.business.databuilder;

import br.com.app.smart.business.databuilder.FuncionalidadeBuilder.TipoFuncionalidadeBuilder;
import br.com.app.smart.business.databuilder.MetaDadoBuilder.TipoMetaDadoBuilder;
import br.com.app.smart.business.funcionalidade.dto.TelaDTO;

public class TelaBuilder {

	public static TelaDTO getInstanceDTO(TipoTelaBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarTelaDTO();

		default:
			break;
		}
		return criarTelaDTO();
	}

	private static TelaDTO criarTelaDTO() {

		TelaDTO dto = new TelaDTO();
		dto.setDescricaoTela("descricaoTela Tela");
		dto.setNomeTela("NomeTela");
		dto.setNumeroTela(1);
		dto.setTituloTela("Titulo tela");
		dto.setUrlTela("urlTela");
		dto.setMetadado(MetaDadoBuilder.getInstanceDTO(TipoMetaDadoBuilder.INSTANCIA));
		dto.setFuncionalidades(FuncionalidadeBuilder.getInstanceDTO(TipoFuncionalidadeBuilder.INSTANCIA));
		return dto;

	}

	public static enum TipoTelaBuilder {

		INSTANCIA;
	}
}
