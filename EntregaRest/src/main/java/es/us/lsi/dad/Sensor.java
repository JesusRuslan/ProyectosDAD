package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sensor {

	private Integer valueId;
	private Integer sensorId;
	private TipoSensor tipo;
	private Integer placaId;
	private List<Double> valor;
	private Long tiempo;
	
	private static int cont = 0;
	private static final Random PRNG = new Random();

	public Sensor(Integer sensorId, Integer placaId) {
		super();
		this.sensorId = sensorId;
		TipoSensor[] tipos = TipoSensor.values();
		this.tipo = tipos[PRNG.nextInt(tipos.length)];
		this.placaId = placaId;
		List<Double> valores = new ArrayList<>();
		valores.add(0.); valores.add(0.);
		this.valor = valores;
		this.tiempo = System.currentTimeMillis()+cont; cont++;
	}
	
	public Sensor(Integer sensorId, TipoSensor tipo, Integer placaId, List<Double> valor, Long tiempo) {
		super();
		this.sensorId = sensorId;
		this.tipo = tipo;
		this.placaId = placaId;
		this.valor = valor;
		this.tiempo = tiempo;
	}

	public Integer getSensorId() {
		return sensorId;
	}
	
	public TipoSensor getTipo() {
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
