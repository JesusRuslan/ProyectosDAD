package es.us.lsi.dad;

public class Placa {

	private Integer idValue;
	private Integer idPlaca;
	private Integer idGroup;
	
	public Placa(Integer idValue, Integer idPlaca, Integer idGroup) {
		super();
		this.idValue = idValue;
		this.idPlaca = idPlaca;
		this.idGroup = idGroup;
	}
	
	public Integer getIdValue() {
		return idValue;
	}
	
	public Integer getIdPlaca() {
		return idPlaca;
	}
	
	public Integer getIdGroup() {
		return idGroup;
	}
	
}
