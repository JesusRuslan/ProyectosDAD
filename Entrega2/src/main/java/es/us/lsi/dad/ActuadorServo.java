package es.us.lsi.dad;

public class ActuadorServo {
	private int sensorId;
	private float velocidad;
	private long time=System.currentTimeMillis();
	private boolean sentido; //true sentido agujas
	private int placaid;
	



	public ActuadorServo(float velocidad, long time, boolean sentido, int placaid,int sensorId) {
		super();
		this.sensorId=sensorId;
		this.velocidad = velocidad;
		this.time = time;
		this.sentido = sentido;
		this.placaid = placaid;
	}



	public int getSensorId() {
		return sensorId;
	}



	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}



	public float getVelocidad() {
		return velocidad;
	}



	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}



	public long getTime() {
		return time;
	}



	public void setTime(long time) {
		this.time = time;
	}



	public boolean isSentido() {
		return sentido;
	}



	public void setSentido(boolean sentido) {
		this.sentido = sentido;
	}



	public int getPlacaid() {
		return placaid;
	}



	public void setPlacaid(int placaid) {
		this.placaid = placaid;
	}
	
	
	
	
}
