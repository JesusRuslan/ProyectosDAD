package es.us.lsi.dad;

public class Actuadores {

		private int placaid;
		private int actuadoresId;
		private float valor;
		private long tiempo;
		
		public int getPlacaid() {
			return placaid;
		}
		public void setPlacaid(int placaid) {
			this.placaid = placaid;
		}
		public int getActuadoresId() {
			return actuadoresId;
		}
		public void setActuadoresId(int actuadoresId) {
			this.actuadoresId = actuadoresId;
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
		public Actuadores(int placaid, int actuadoresId, float valor, long tiempo) {
			super();
			this.placaid = placaid;
			this.actuadoresId = actuadoresId;
			this.valor = valor;
			this.tiempo = tiempo;
		}
		public Actuadores(int actuadoresId) {
			super();
			this.actuadoresId = actuadoresId;
		}

}