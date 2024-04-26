package es.us.lsi.dad;

public class SensorFotoResistor {
	private Integer sensorId;
	private Integer placaId;
	private Float lecturaLuz;
	private Boolean luz;
	private Long tiempo;
	
	public SensorFotoResistor(Integer sensorId, Integer placaId, Float lecturaLuz) {
		super();
		this.sensorId = sensorId;
		this.placaId = placaId;
		this.lecturaLuz = lecturaLuz;
		this.luz = lecturaLuz>=2250.0?true:false;
		this.tiempo = System.currentTimeMillis();
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public Integer getPlacaId() {
		return placaId;
	}

	public Float getLecturaLuz() {
		return lecturaLuz;
	}

	public Boolean getLuz() {
		return luz;
	}

	public Long getTiempo() {
		return tiempo;
	}

	
	
	
	
}
