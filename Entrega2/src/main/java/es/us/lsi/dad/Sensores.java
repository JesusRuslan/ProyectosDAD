package es.us.lsi.dad;

public class Sensores {
	
	private int placaid;
	private int sensorId;
	private float valor;
	private long tiempo;
	
	private static int cont=0;

	public Sensores(int sensorId) {
		super();
		this.sensorId=sensorId;
	}
	
	public Sensores(int placaid, int sensorId, float valor, long tiempo) {
		super();
		this.placaid = placaid;
		this.sensorId = cont;
		cont++;
		this.valor = valor;
		this.tiempo = tiempo;
	}

	public int getPlacaid() {
		return placaid;
	}

	public void setPlacaid(int placaid) {
		this.placaid = placaid;
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
	


}