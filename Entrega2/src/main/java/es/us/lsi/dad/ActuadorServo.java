package es.us.lsi.dad;

public class ActuadorServo   {
	private int actuadorId;
	private float velocidad;
	private long time=System.currentTimeMillis();
	private boolean sentido; //true sentido agujas
	private int placaid;
	



	public ActuadorServo(float velocidad, long time, boolean sentido, int placaid,int actuadorId) {
		super();
		this.actuadorId=actuadorId;
		this.velocidad = velocidad;
		this.time = time;
		this.sentido = sentido;
		this.placaid = placaid;
	}



	


	public int getActuadorId() {
		return actuadorId;
	}






	public void setActuadorId(int actuadorId) {
		this.actuadorId = actuadorId;
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
