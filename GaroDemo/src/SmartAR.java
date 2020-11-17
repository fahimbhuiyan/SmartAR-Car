import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Iterator;




public class SmartAR {
	 static int totalEntries =0;
	 static boolean swap = false;
	 static ArrayList<Car> arrList = new ArrayList<Car>(); 
	 static LinkedHashMap<String, Car> linkedHM = new LinkedHashMap<>();
	 static LinkedList<Car> duplicateList = new LinkedList<Car>();
	 static int dupCounter = 0;
	 static int keyLength = 6;
	 static boolean hashADT = false;
	 static boolean seqADT = false;
	 static long threshold = 0;
	
	public static void store(String path) throws FileNotFoundException {
		Car possibleDup = null;
		Scanner sc = null;
		File f1 = new File(path);
		sc = new Scanner(f1);
		
		LinkedHashMap<String, Car> temp = new LinkedHashMap<>(); // remove duplicates before counting 
	    while (sc.hasNextLine()) {
	    	String key = sc.nextLine().trim();
	    	possibleDup = temp.put(key,new Car(key,"no make",0));
	    	if(possibleDup != null) {							// if it returns a car, its a duplicate, store it
	    		duplicateList.add(possibleDup);			
	    	}
		} 
	    
	    totalEntries += temp.size();
	    swap();							  // verify if size is good for threshold defined
	    if(totalEntries >= threshold ) {
	    	linkedHM.putAll(temp);
	    	hashADT = true;
	    }
	    else {
	    	Iterator<Car> it = temp.values().iterator();
			Car c = null;
        	while (it.hasNext()) {
	       		c = it.next();
	       		arrList.add(c);
	       	}
	       	temp.clear();  		 // clear temp hashmap 								  
		}
	    
	    if(hashADT == true) 
	    	System.out.println("Stored in linkedHashMap");
	    else
	    	System.out.println("Stored in arrayList");
	    sc.close();
		
	}
	
	public static void swap() {
		if(hashADT == true && totalEntries < threshold) {
			Iterator<Car> it = linkedHM.values().iterator();
			Car c =null;
			while (it.hasNext()) {
				c = it.next();
				arrList.add(c);
			}
			
			linkedHM.clear();
			hashADT = false;
			System.out.println("Swapped enteries to arrayList");
			
		}
		else if (hashADT == false && totalEntries >= threshold) {
			Iterator<Car> it = arrList.iterator(); 		//iterate through arrList cars
			Car c =null;
			while(it.hasNext()) {
				c = it.next();
				linkedHM.put(c.getKey(),c);
			}
			arrList.clear();
			hashADT = true;
			System.out.println("Swapped entries to linkedHashMaps");
		}
	}
	
	public static void setThreshold(int T) {
		if(T < 0) {
			System.out.println("cannot be negative");
			threshold = 0;
		}
		else {
			threshold = T;
			System.out.println("threshold set to " + T);
		}
		swap();
	}
	
	public static void setKeyLength(int len) {
		if (len >= 6 && len <= 12) {
			keyLength = len;
			System.out.println("key length set to " + len);
		}
		else {
			System.out.println("Key length must be between 6 and 12 inclusively");
		}
	}
	
	public static String[] generate(int n) {
		String[] newKeys = new String[n];
		String alphaNumericStrings = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(n);
		for(int i = 0; i<n; i++) {
			sb.delete(0,  sb.length());
			for(int j = 0; j<keyLength; j++) {
				int index  = (int)(alphaNumericStrings.length()* Math.random());
	            sb.append(alphaNumericStrings.charAt(index));
			}
			newKeys[i] = sb.toString();
		}
		return newKeys;
	}
	
	public static void allKeys() {
		if(hashADT == true) {
			 ArrayList<String> v = new ArrayList<String>(linkedHM.keySet());   // put keyset in arraylist and sort
			 Collections.sort(v);
			    for (String str : v) {
			      System.out.println(str);
			    }
			    v.clear();
		}
		else {
			if(arrList.size() == 0) {
				System.out.println("Empty! nothing to sort");
			}
			else {
				ArrayList<Car> temp = new ArrayList<Car>(); 
				temp.addAll(arrList);
				temp.sort(Comparator.comparing(Car::getKey)); 	// use comparator to compare car attribute
				for (Car c: temp){
				     System.out.println(c.getKey());
				}
				temp.clear();
			}
		}
	}
	
	public static void add(String key, Car car) {
		boolean isDuplicate = false;
		int index = 0;
		Car duplicate = null;
		if(key.length() < 6 || key.length() > 12 ) {
			System.out.println("key Length must be between 6 & 12.");
		}
		
		else {
			if(hashADT == true) {
				duplicate = linkedHM.put(key, (new Car(key,car.getMake(),car.getYear())));
				if(duplicate != null)
					duplicateList.add(duplicate);
				else
					totalEntries++;
			}
			else {
				for(Car c : arrList){            
	                if(c.getKey().equals(key)) {
	                    index = ((arrList.indexOf(c)));
	                    duplicate = arrList.get(index);
	                    arrList.set(index,car);
	                    duplicateList.add(duplicate);
	                    isDuplicate = true;
	                }              
	            }
				if(isDuplicate == false)
				arrList.add(car);
			}
			System.out.println("New entry added");
			swap();
		}
	}
	
	public static void remove(String key) {
		Car deleteCar = null;
		boolean removed = false;
		if(hashADT == true) {
			deleteCar = linkedHM.remove(key); // returns value
			if(deleteCar != null) {
				removed = true;
				duplicateList.add(deleteCar);
			}
		}
		
		else {
			for (ListIterator<Car> it = arrList.listIterator();	//
				it.hasNext();) {	// list iterator or it throws exception, cannot remove/add looping arrlist
					Car value = it.next();
					if(value.getKey().equals(key)) {
						removed = true;
						duplicateList.add(value);
						it.remove();
					}
			}
		}
		
		if(removed == true) {
			totalEntries--;
			swap();
			System.out.println("Entry removed");
		}
		else {
			System.out.println("key doesn't exist");
		}
	}
	
	
	public static Car getValues(String key) {
		Car value = null;
		if (hashADT == true) {
			value = linkedHM.get(key);
		}
		else {
			for(Car c : arrList) {
				if (c.getKey().equals(key)) {
					value = c;
				}
			}
		}
		return value;
	}
	
	public static String nextKey(String key) {
		String nextKey = "";
		int nextIndex = 0;
		if(hashADT == true) {
			Iterator<String> it = linkedHM.keySet().iterator(); 	//Iterator for keysets
			while (it.hasNext()) {
				String temp = it.next();
				if(temp.equals(key))
					nextKey = it.next();
			}
		}
		
		else {
			for(Car c : arrList) {
				if(c.getKey().equals(key)) {
					nextIndex = ((arrList.indexOf(c)) + 1);
					c = arrList.get(nextIndex);
					nextKey = c.getKey();
				}
			}
			if(nextKey.equals(""))
				nextKey ="No such key";
		}
		return nextKey;
	}
	
	public static String prevKey(String key) {
		String prevKey = "";
		int prevIndex = 0;
		if(hashADT == true) {
			String prev = "";
			Iterator<String> it = linkedHM.keySet().iterator(); //Iterator for keysets
			while (it.hasNext()) {
				String temp = it.next();
				if(temp.equals(key))
					prevKey = prev;
					prev = temp;
			}
		}
		
		else {
			for(Car c : arrList) {
				if(c.getKey().contentEquals(key)) {
					prevIndex = ((arrList.indexOf(c)) - 1);
					c = arrList.get(prevIndex);
					prevKey = c.getKey();
				}
			}
			if(prevKey.equals(""))
				prevKey ="No such key";
		}
		return prevKey;
	}
	
	public static void previousCars(String key) {
		LinkedList<Car> prevCars = new LinkedList<Car>();
		for(Car c : duplicateList) {
			if(c.getKey().contentEquals(key)) {
				prevCars.add(c);
			}
		}
		Collections.reverse(prevCars);
		System.out.println(prevCars.toString());
	}
	
	public static void methods(){
		System.out.println("\nMethods: \n"
				+ "0: exit\n"
				+ "1: store from file\n"
				+ "2: Total entries\n"
				+ "3: setThreshold\n"
				+ "4: setKeyLength\n"
				+ "5: generate\n"
				+ "6: allKeys\n"
				+ "7: add\n"
				+ "8: remove\n"
				+ "9: getValues\n"
				+ "10:nextKey\n"
				+ "11:prevKey\n"
				+ "12:previousCars\n"
				+ "13:method list");
	}
	
}
