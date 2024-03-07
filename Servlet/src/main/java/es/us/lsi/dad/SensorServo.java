package es.us.lsi.dad;

public class SensorServo {
	private Float Velocidad;
	private Boolean Sentido; //True normal, False inverso
	
	
	
	
	
	public SensorServo(Float velocidad, Boolean sentido) {
		super();
		Velocidad = velocidad;
		Sentido = sentido;
	}
	public Float getVelocidad() {
		return Velocidad;
	}
	public void setVelocidad(Float velocidad) {
		Velocidad = velocidad;
	}
	public Boolean getSentido() {
		return Sentido;
	}
	public void setSentido(Boolean sentido) {
		Sentido = sentido;
	}
	 
}
