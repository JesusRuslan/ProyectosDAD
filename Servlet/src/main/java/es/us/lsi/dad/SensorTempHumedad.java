package es.us.lsi.dad;

public class SensorTempHumedad {
	private Float Temperatura;
	private Float Humedad;
	
	public Float getTemperatura() {
		return Temperatura;
	}
	public void setTemperatura(Float temperatura) {
		Temperatura = temperatura;
	}
	public Float getHumedad() {
		return Humedad;
	}
	public void setHumedad(Float humedad) {
		Humedad = humedad;
	}
	public SensorTempHumedad(Float temperatura, Float humedad) {
		super();
		Temperatura = temperatura;
		Humedad = humedad;
	}
	
	
	
	
	
	
	
}
