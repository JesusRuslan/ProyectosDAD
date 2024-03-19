package es.us.lsi.dad;

public class SensorTYM {
	private int sensorId;
	private Float Temperatura;
	private Float Humedad;
	private long time= System.currentTimeMillis();
	private int placaid;
	
	private static int cont=0;
	
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
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public SensorTYM(Float temperatura, Float humedad, long time,int placaid) {
		super();
		this.sensorId= cont;
		cont++;
		this.placaid= placaid;
		Temperatura = temperatura;
		Humedad = humedad;
		this.time = time;
	}
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
	public int getPlacaid() {
		return placaid;
	}
	public void setPlacaid(int placaid) {
		this.placaid = placaid;
	}
	

	
}
