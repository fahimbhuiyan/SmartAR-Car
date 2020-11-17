/*
 * LinkHashMap can be used to maintain insertion order which keys inserted
 * into Map or can be used to maintain order on which keys are accessed.
 * The space and time complexity of arrayLists is O(n), you are added it to the arrayList.
 * Linked hash maps takes more memory. Each entry has references to next and previous entries. 
 * In addition, the complexity takes O(n). Furthermore, the program runs in O(n).
 */
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SmartAR_Main extends SmartAR{

	public static void main(String[] args) {
			System.out.println("***SmartAR***");
			
			methods();
			
			int method = 0;
			boolean isRunning = true;
			Scanner user = new Scanner(System.in);
			do {
				System.out.print("\nEnter value: ");
				if(user.hasNextInt()) {
					method = user.nextInt();
					switch(method) {
					case 0:
						System.out.println("Closing smartAr");
						isRunning = false;
						break;
					case 1:
						System.out.print("Enter file path: ");
						String temp = user.next();
						try {	
							store(temp); 
						    System.out.println("Total entries: " + totalEntries);
						}
						catch (FileNotFoundException e) {
							System.out.println("File not found");
						}
						break;
					case 2:
						System.out.println("Total entries: " + totalEntries);
						System.out.println("Arraylist size: " + arrList.size());
						System.out.println("LinkedHashmap size:" + linkedHM.size());
						break;
					case 3:
						System.out.print("Set threshold: ");
						try {
							int temp1 = user.nextInt();
							setThreshold(temp1);	
						}
						catch(InputMismatchException e) {
							System.out.println("Not a number");
						}
						break;
					case 4:
						try {
							System.out.print("Key length: ");
							int temp2 = user.nextInt();
							setKeyLength(temp2);
						}
						catch(InputMismatchException e) {
							System.out.println("Not a number");
						}
						break;
					case 5:
						try {
							System.out.print("Number of keys to generate: ");
							int temp3 = user.nextInt();
							System.out.println(Arrays.toString(generate(temp3)));
							//generate(temp3);
							}
						catch(InputMismatchException e) {
							System.out.println("Not a number");
						}
						break;
					case 6:
						try {
							System.out.println("all keys as a sorted sequence (lexicographic order)");   // it says return sequence
							allKeys();
						}
						catch (InputMismatchException e) {
							System.out.println("not sorted");
							
						}
						break;
					case 7:
						System.out.print("Enter Key: ");
						String tempK= user.next();
						System.out.print("Enter make: ");
						String tempM = user.next();
						System.out.print("Enter year: ");
						if(user.hasNextInt()) {
							int tempY = user.nextInt();
							Car c = new Car(tempK,tempM,tempY);
							add(tempK,c);
						}
						else {
							user.nextLine();
							System.out.println("Year cannot be a string");
						}
						break;
					case 8:
						System.out.print("Remove Key: ");
						String temp4 = user.next(); 
						remove(temp4);
						break;
					case 9:
						System.out.print("Key for values: ");
						String temp5 = user.next();
						Car v =	getValues(temp5);
						System.out.print(v);
						break;
					case 10:
						System.out.print("Get next key: ");
						String temp6 = user.next();
						System.out.println(nextKey(temp6));
						break;
					case 11:
						System.out.print("Get previous key: ");
						String temp7 = user.next();
						System.out.println(prevKey(temp7));
						break;
					case 12: 
						System.out.print("Previouscars: ");
						String temp8 = user.next();
						previousCars(temp8);						
						break;
					case 13:
						methods();
						break;
				
					}			
				}
					user.nextLine();		
			}
			while(isRunning);
			user.close();
			
		
	}

}
