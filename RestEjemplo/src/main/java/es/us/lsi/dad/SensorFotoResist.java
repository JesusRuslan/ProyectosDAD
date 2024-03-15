package es.us.lsi.dad;

public class SensorFotoResist {
	
	private int sensorId;
	private int placaId;
	private Float lecturaLuz;
	private Boolean luz;
	private Long tiempo;
	
	public SensorFotoResist(int sensorId, int placaId, Float lecturaLuz) {
		super();
		this.sensorId = sensorId;
		this.placaId = placaId;
		this.lecturaLuz = lecturaLuz;
		this.luz = lecturaLuz>=2250.0?true:false;
		this.tiempo = System.currentTimeMillis();
	}

	public int getSensorId() {
		return sensorId;
	}

	public int getPlacaId() {
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
