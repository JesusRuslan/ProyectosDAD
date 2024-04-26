package es.us.lsi.dad;

public class ActuadorRGB  {
	private Integer actuadorId;
	private Integer placaId;
	private Boolean subida;
	private Boolean bajada;
	private Long tiempo;
	
	public ActuadorRGB(Integer actuadorId, Integer placaId, Boolean subida, Boolean bajada) {
		super();
		this.actuadorId = actuadorId;
		this.placaId = placaId;
		this.subida = subida;
		this.bajada = bajada;
		this.tiempo = System.currentTimeMillis();
	}

	public Integer getActuadorId() {
		return actuadorId;
	}

	public Integer getPlacaId() {
		return placaId;
	}

	public Boolean getSubida() {
		return subida;
	}

	public Boolean getBajada() {
		return bajada;
	}

	public Long getTiempo() {
		return tiempo;
	}
	
	
	
}
