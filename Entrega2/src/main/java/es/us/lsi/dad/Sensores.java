package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sensores {
	private Integer valueId;
	private Integer sensorId;
	private Integer placaId;
	private List<Double> valor;
	private Long tiempo;
	
	private static int cont = 0;

	public Sensores(Integer sensorId, Integer placaId) {
		super();
		this.valueId = cont;
		this.sensorId = sensorId;
		this.placaId = placaId;
		List<Double> valores = new ArrayList<>();
		valores.add(0.); valores.add(0.);
		this.valor = valores;
		this.tiempo = System.currentTimeMillis();
		cont++;
	}
	
	public Sensores(Integer sensorId, Integer placaId, List<Double> valor) {
		super();
		this.valueId = cont;
		this.sensorId = sensorId;
		this.placaId = placaId;
		this.valor = valor;
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

	public List<Double> getValor() {
		return new ArrayList<>(valor);
	}

	public Long getTiempo() {
		return tiempo;
	}
	


}