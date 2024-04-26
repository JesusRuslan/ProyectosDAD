package es.us.lsi.dad;

public class ActuadorServo {
	private Integer actuadorId;
	private Integer placaId;
	private Double velocidad;
	private Boolean sentido; // True = sentido horario
	private Long tiempo;
	
	public ActuadorServo(Integer actuadorId, Integer placaId, Double velocidad, Boolean sentido) {
		super();
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.tiempo = System.currentTimeMillis();
	}

	public Integer getActuadorId() {
		return actuadorId;
	}

	public Integer getPlacaId() {
		return placaId;
	}

	public Double getVelocidad() {
		return velocidad;
	}

	public Boolean getSentido() {
		return sentido;
	}

	public Long getTiempo() {
		return tiempo;
	}
	 
}
