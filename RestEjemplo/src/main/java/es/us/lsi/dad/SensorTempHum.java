package es.us.lsi.dad;

public class SensorTempHum {
	
	private int sensorId;
	private int placaId;
	private Float temperatura;
	private Float humedad;
	private Long tiempo;
	
	public SensorTempHum(int sensorId, int placaId, Float temperatura, Float humedad) {
		super();
		this.sensorId = sensorId;
		this.placaId = placaId;
		this.temperatura = temperatura;
		this.humedad = humedad;
		this.tiempo = System.currentTimeMillis();
	}

	public int getSensorId() {
		return sensorId;
	}

	public int getPlacaId() {
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
