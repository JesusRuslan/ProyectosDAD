package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Actuador {

	private Integer valueId;
	private Integer actuadorId;
	private TipoActuador tipo;
	private Integer placaId;
	private List<Double> valor;
	private Long tiempo;

	private static int cont = 0;
	private static final Random PRNG = new Random();
	
	public Actuador(Integer actuadorId, Integer placaId) {
		super();
		this.actuadorId = actuadorId;
		TipoActuador[] tipos = TipoActuador.values();
		this.tipo = tipos[PRNG.nextInt(tipos.length)];
		this.placaId = placaId;
		List<Double> valores = new ArrayList<>();
		valores.add(0.); valores.add(0.);
		this.valor = valores;
		this.tiempo = System.currentTimeMillis()+cont; cont++;
	}
	
	public Actuador(Integer actuadorId, Integer placaId, List<Double> valor, Long tiempo) {
		super();
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.valor = valor;
		this.tiempo = tiempo;
	}

	public Integer getActuadorId() {
		return actuadorId;
	}
	
	public TipoActuador getTipo() {
		return tipo;
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
