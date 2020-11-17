
public class Car {
	private int year;
	private String make;
	private String key;
	
	Car(String key,String make, int year) {
		year = year;
		make = make;
		key = key;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
