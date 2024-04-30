package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sensores {

	private Integer valueId;
	private Integer placaId;
	private Integer groupId;
	private Integer sensorId;
	private Double valor1;
	private Double valor2;
	private Long tiempo;
	

	public Sensores(Integer valueId, Integer placaId,Integer groupId,Integer sensorId, Double valor1, Double valor2,Long tiempo) {
		super();
		this.valueId = valueId;
		this.placaId = placaId;
		this.groupId=groupId;
		this.sensorId = sensorId;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.tiempo = System.currentTimeMillis();
	}
	
	public Sensores(Integer valueId,Integer sensorId, Integer groupId,Integer placaId, Double valor1, Double valor2) {
		super();
		this.valueId = valueId;
		this.sensorId = sensorId;
		this.groupId=groupId;
		this.placaId = placaId;

		this.valor1 = valor1;
		this.valor2 = valor2;
		this.tiempo = System.currentTimeMillis();;
	}

	public Integer getValueId() {
		return valueId;
	}
	
	public Integer getSensorId() {
		return sensorId;
	}
	
	public Integer getPlacaId() {
		return placaId;
	}

	public Double getValor1() {
		return valor1;
	}
	public Double getValor2() {
		return valor2;
	}

	public Long getTiempo() {
		return tiempo;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	


}