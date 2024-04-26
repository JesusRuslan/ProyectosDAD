package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sensores {
	private Integer valueId;
	private Integer sensorId;
	private Integer placaId;
	private Double valor1;
	private Double valor2;
	private Long tiempo;
	
	private static int cont = 0;

	public Sensores(Integer sensorId, Integer placaId,Double valor1, Double valor2,Long tiempo) {
		super();
		this.valueId = cont;
		this.sensorId = sensorId;
		this.placaId = placaId;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.tiempo = System.currentTimeMillis();
		cont++;
	}
	
	public Sensores(Integer valueId,Integer sensorId, Integer placaId, Double valor1, Double valor2) {
		super();
		this.valueId = cont;
		this.sensorId = sensorId;
		this.placaId = placaId;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.tiempo = System.currentTimeMillis();
		cont++;
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
	


}