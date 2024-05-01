package es.us.lsi.dad;

public class Actuador {

	private Integer idValue;
	private Integer idActuador;
	private Integer idPlaca;
	private Double valor;
	private Integer tipoActuador;
	private Long tiempo;
	private Integer idGroup;
	
	public Actuador(Integer idValue, Integer idActuador, Integer idPlaca, Double valor, Integer tipoActuador,
			Long tiempo, Integer idGroup) {
		super();
		this.idValue = idValue;
		this.idActuador = idActuador;
		this.idPlaca = idPlaca;
		this.valor = valor;
		this.tipoActuador = tipoActuador;
		this.tiempo = tiempo;
		this.idGroup = idGroup;
	}

	public Integer getIdValue() {
		return idValue;
	}

	public Integer getIdActuador() {
		return idActuador;
	}

	public Integer getIdPlaca() {
		return idPlaca;
	}

	public Double getValor() {
		return valor;
	}

	public Integer getTipoActuador() {
		return tipoActuador;
	}

	public Long getTiempo() {
		return tiempo;
	}

	public Integer getIdGroup() {
		return idGroup;
	}
	
}
