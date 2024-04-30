package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Actuadores {

	private Integer valueId;
	private Integer placaId;
	private Integer groupId;
	private Integer actuadorId;
	private Double velocidad;
	private Boolean sentido;
	private Long tiempo;

	
	public Actuadores(Integer actuadorId, Integer placaId) {
		super();
		this.valueId = valueId;
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.tiempo = System.currentTimeMillis();;
	}
	
	public Actuadores(Integer actuadorId, Integer placaId,Integer groupId,Double velocidad, Boolean sentido) {
		super();
		this.valueId = valueId;
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.groupId=groupId;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.tiempo = System.currentTimeMillis();;
		
	}

	public Actuadores(Integer valueId,Integer placaId, Integer groupId, Integer actuadorId,Double velocidad, Boolean sentido,Long tiempo) {
		super();
		this.valueId = valueId;
		this.placaId = placaId;
		this.groupId=groupId;
		this.actuadorId = actuadorId;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.tiempo = System.currentTimeMillis();;
		
	}
	public Actuadores(Integer actuadorId, Integer groupId, Integer placaId,Double velocidad, Boolean sentido,Long tiempo) {
		super();
		this.placaId = placaId;
		this.groupId=groupId;
		this.actuadorId = actuadorId;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.tiempo = System.currentTimeMillis();;
		
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



	public Double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Double velocidad) {
		this.velocidad = velocidad;
	}

	public Boolean getSentido() {
		return sentido;
	}

	public void setSentido(Boolean sentido) {
		this.sentido = sentido;
	}

	public Long getTiempo() {
		return tiempo;
	}
	public Integer getGroupId() {
		return groupId;
	}

}
