package es.us.lsi.dad;

public class Placa {

	private Integer valueId;
	private Integer placaId;
	private Integer groupId;
	
	public Placa(Integer valueId, Integer placaId, Integer groupId) {
		super();
		this.valueId = valueId;
		this.placaId = placaId;
		this.groupId = groupId;
	}
	
	public Integer getValueId() {
		return valueId;
	}
	
	public Integer getPlacaId() {
		return placaId;
	}
	
	public Integer getGroupId() {
		return groupId;
	}
	
}
