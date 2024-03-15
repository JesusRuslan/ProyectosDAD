package ejemplo.dad;

public class SensorTempHum {
	
	private Float temp;
	private Float hum;
	
	public SensorTempHum(Float temp, Float hum) {
		super();
		this.temp = temp;
		this.hum = hum;
	}
	public Float getTemp() {
		return temp;
	}
	public void setTemp(Float temp) {
		this.temp = temp;
	}
	public Float getHum() {
		return hum;
	}
	public void setHum(Float hum) {
		this.hum = hum;
	}
}
