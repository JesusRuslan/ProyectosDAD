package es.us.lsi.dad;

public class SensorFotoResistor {
	private int sensorId;
	private float valorLuz;
	private long time=System.currentTimeMillis();
	private Boolean dia;
	private int placaid;
	
	
	public float getValorLuz() {
		return valorLuz;
	}
	public void setValorLuz(float valorLuz) {
		this.valorLuz = valorLuz;
		this.dia=valorLuz>2250.0?true:false;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Boolean getDia() {
		return dia;
	}

	public SensorFotoResistor(float valorLuz, long time,int placaid,int sensorId) {
		super();
		this.sensorId=sensorId;
		this.placaid=placaid;
		this.valorLuz = valorLuz;
		this.time = time;
		this.dia=valorLuz>2250.0?true:false; //revisar 
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

	public void setDia(Boolean dia) {
		this.dia = dia;
	}
	
	
	
	
	
	
}
