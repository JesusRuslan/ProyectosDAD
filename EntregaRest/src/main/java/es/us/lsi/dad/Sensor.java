package es.us.lsi.dad;

public class Sensor {

	private Integer idValue;
	private Integer idSensor;
	private Integer idPlaca;
	private Double valor1;
	private Double valor2;
	private Integer tipoSensor;
	private Long tiempo;
	private Integer idGroup;
	
	public Sensor(Integer idValue, Integer idSensor, Integer idPlaca, Double valor1, Double valor2, Integer tipoSensor,
			Long tiempo, Integer idGroup) {
		super();
		this.idValue = idValue;
		this.idSensor = idSensor;
		this.idPlaca = idPlaca;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.tipoSensor = tipoSensor;
		this.tiempo = tiempo;
		this.idGroup = idGroup;
	}

	public Integer getIdValue() {
		return idValue;
	}

	public Integer getIdSensor() {
		return idSensor;
	}

	public Integer getIdPlaca() {
		return idPlaca;
	}

	public Double getValor1() {
		return valor1;
	}

	public Double getValor2() {
		return valor2;
	}

	public Integer getTipoSensor() {
		return tipoSensor;
	}

	public Long getTiempo() {
		return tiempo;
	}

	public Integer getIdGroup() {
		return idGroup;
	}
	
}
