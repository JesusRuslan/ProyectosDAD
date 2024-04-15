package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;

public class Actuador {

	private Integer valueId;
	private Integer actuadorId;
	private Integer placaId;
	private List<Double> valor;
	private Long tiempo;

	private static int cont = 0;
	
	public Actuador(Integer actuadorId, Integer placaId) {
		super();
		this.valueId = cont;
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		List<Double> valores = new ArrayList<>();
		valores.add(0.); valores.add(0.);
		this.valor = valores;
		this.tiempo = System.currentTimeMillis();
		cont++;
	}
	
	public Actuador(Integer actuadorId, Integer placaId, List<Double> valor) {
		super();
		this.valueId = cont;
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.valor = valor;
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

	public List<Double> getValor() {
		return new ArrayList<>(valor);
	}

	public Long getTiempo() {
		return tiempo;
	}
	
}
