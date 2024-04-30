package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;

public class Actuador {

	private Integer valueId;
	private Integer actuadorId;
	private Integer placaId;
	private Double valor;
	private Integer tipoActuador;
	private Long tiempo;
	private Integer groupId;
	
	public Actuador(Integer valueId, Integer actuadorId, Integer placaId, Double valor, Integer tipoActuador,
			Long tiempo, Integer groupId) {
		super();
		this.valueId = valueId;
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.valor = valor;
		this.tipoActuador = tipoActuador;
		this.tiempo = tiempo;
		this.groupId = groupId;
	}

	public Integer getValueId() {
		return valueId;
	}

	public Integer getActuadorId() {
		return actuadorId;
	}

	public Integer getPlacaId() {
		return placaId;
	}

	public Double getValor() {
		return valor;
	}

	public Integer getTipoActuador() {
		return tipoActuador;
	}

	public Long getTiempo() {
		return tiempo;
	}

	public Integer getGroupId() {
		return groupId;
	}
	
}
