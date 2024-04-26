package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Actuadores {

	private Integer valueId;
	private Integer actuadorId;
	private Integer placaId;
	private Double velocidad;
	private Boolean sentido;
	private Long tiempo;

	private static int cont = 0;
	
	public Actuadores(Integer actuadorId, Integer placaId) {
		super();
		this.valueId = cont;
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.tiempo = System.currentTimeMillis();
		cont++;
	}
	
	public Actuadores(Integer actuadorId, Integer placaId,Double velocidad, Boolean sentido) {
		super();
		this.valueId = cont;
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.tiempo = System.currentTimeMillis();
		cont++;
		
	}

	public Actuadores(Integer valueId,Integer actuadorId, Integer placaId, Double velocidad, Boolean sentido,Long tiempo) {
		super();
		this.valueId = cont;
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.tiempo = System.currentTimeMillis();
		cont++;
		
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

}
