package part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class AuthenticateUser {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        scanner.close();
        
    	HashMap<String, String> userMap = readUsers();
    	
    	if (userMap.containsKey(userName)) {
    		if (userMap.get(userName).equals(JavaMD5Hash.md5(password))) {
    			System.out.println("Welcome back " + userName);
    		} else {
    			System.out.println("Invalid Password");
    		}
    	} else {
    		System.out.println("Not a valid user");
    	}
	}
	
	public static HashMap<String, String> readUsers (){

		final String SPLIT = "@@";
		final String fileName = "users.txt";

		HashMap<String, String> userMap = new HashMap<String, String>();

		try {
			FileReader fr = new FileReader(fileName); 
			BufferedReader br = new BufferedReader(fr); 
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] lineParts = line.split(SPLIT);
				userMap.put(lineParts[0], lineParts[1]);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return userMap;
	}

}
