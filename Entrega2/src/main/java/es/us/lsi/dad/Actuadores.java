package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Actuadores {

	private Integer valueId;
	private Integer actuadoresId;
	private TipoActuador tipo;
	private Integer placaid;
	private List<Double> valor;
	private Long tiempo;

	private static int cont = 0;
	private static final Random PRNG = new Random();
	
	public Actuadores(Integer actuadoresId, Integer placaid) {
		super();
		this.actuadoresId = actuadoresId;
		TipoActuador[] tipos = TipoActuador.values();
		this.tipo = tipos[PRNG.nextInt(tipos.length)];
		this.placaid = placaid;
		List<Double> valores = new ArrayList<>();
		valores.add(0.); valores.add(0.);
		this.valor = valores;
		this.tiempo = System.currentTimeMillis()+cont; cont++;
	}
	
	public Actuadores(Integer actuadoresId, Integer placaId, List<Double> valor, Long tiempo) {
		super();
		this.actuadoresId = actuadoresId;
		this.placaid = placaId;
		this.valor = valor;
		this.tiempo = tiempo;
	}

	public Integer getActuadoresId() {
		return actuadoresId;
	}
	
	public TipoActuador getTipo() {
		return tipo;
	}

	public Integer getPlacaId() {
		return placaid;
	}

	public List<Double> getValor() {
		return new ArrayList<>(valor);
	}

	public Long getTiempo() {
		return tiempo;
	}
		public Actuadores(int actuadoresId) {
			super();
			this.actuadoresId = actuadoresId;
		}

}