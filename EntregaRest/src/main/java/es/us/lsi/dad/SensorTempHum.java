package es.us.lsi.dad;

public class SensorTempHum {
	
	private Integer sensorId;
	private Integer placaId;
	private Float temperatura;
	private Float humedad;
	private Long tiempo;
	
	public SensorTempHum(Integer sensorId, Integer placaId, Float temperatura, Float humedad) {
		super();
		this.sensorId = sensorId;
		this.placaId = placaId;
		this.temperatura = temperatura;
		this.humedad = humedad;
		this.tiempo = System.currentTimeMillis();
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public Integer getPlacaId() {
		return placaId;
	}

	public Float getTemperatura() {
		return temperatura;
	}

	public Float getHumedad() {
		return humedad;
	}

	public Long getTiempo() {
		return tiempo;
	}

}
