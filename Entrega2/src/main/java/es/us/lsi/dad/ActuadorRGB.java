package es.us.lsi.dad;

public class ActuadorRGB  {
	private boolean subida;
	private boolean bajada;
	private int placaid;
	private int actuadorId;
	
	public boolean isSubida() {
		return subida;
	}
	public void setSubida(boolean subida) {
		this.subida = subida;
	}
	public boolean isBajada() {
		return bajada;
	}
	public void setBajada(boolean bajada) {
		this.bajada = bajada;
	}
	public int getPlacaid() {
		return placaid;
	}
	public void setPlacaid(int placaid) {
		this.placaid = placaid;
	}
	public ActuadorRGB(boolean subida, boolean bajada, int placaid,int actuadorId) {
		super();
		this.actuadorId=actuadorId;
		this.subida = subida;
		this.bajada = bajada;
		this.placaid = placaid;
	}
	public int getActuadorId() {
		return actuadorId;
	}
	public void setActuadorId(int actuadorId) {
		this.actuadorId = actuadorId;
	}
	
	
	
}
