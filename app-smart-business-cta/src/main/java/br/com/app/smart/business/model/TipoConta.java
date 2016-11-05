package br.com.app.smart.business.model;

import java.io.Serializable;

public enum TipoConta implements Serializable {

	MASTER(1);

	private Integer value;

	private TipoConta(Integer valor) {

		this.value = valor;
	}

	public Integer getValue() {
		return value;
	}

}
