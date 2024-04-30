package es.us.lsi.dad;

public class Sensor {

	private Integer valueId;
	private Integer sensorId;
	private Integer placaId;
	private Double valor1;
	private Double valor2;
	private Integer tipoSensor;
	private Long tiempo;
	private Integer groupId;
	
	public Sensor(Integer valueId, Integer sensorId, Integer placaId, Double valor1, Double valor2, Integer tipoSensor,
			Long tiempo, Integer groupId) {
		super();
		this.valueId = valueId;
		this.sensorId = sensorId;
		this.placaId = placaId;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.tipoSensor = tipoSensor;
		this.tiempo = tiempo;
		this.groupId = groupId;
	}

	public Integer getValueId() {
		return valueId;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public Integer getPlacaId() {
		return placaId;
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

	public Integer getGroupId() {
		return groupId;
	}
	
}
