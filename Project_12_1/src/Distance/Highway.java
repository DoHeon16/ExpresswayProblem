package Distance;

import java.util.ArrayList;

public class Highway {

	String name;//고속도로 이름
	ArrayList<String> road=new ArrayList<String>();
	
	Highway(String highname, String name){
		this.name = name;
		this.road.add(name);
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getRoad() {
		return road;
	}
	
	public void setRoad(String road) {
		this.road.add(road);
	}
	
	public Highway(String name) {
		super();
		this.name = name;
	}	
	

	
	public Highway() {
		
	}
	
}
