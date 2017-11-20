package part1;
/**
 * Class AddUser
 * author : ERL67
 * created: 11/19/2017
 */
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import part1.JavaMD5Hash;

public class AddUser {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String pwdHash = JavaMD5Hash.md5(password);

        System.out.println("\nYour username: " + userName);
        System.out.println("\nYour password: " + password);
        System.out.println("\nPassword MD5: " + pwdHash);
        
        scanner.close();
        
        if (userName.isEmpty() || password.isEmpty()) {
        	System.out.println("\nInvalid input. Try Again.");
        } else {
        	writeUser(userName, pwdHash);
        }
	}
	
	public static void writeUser (String userName, String pwdHash) {
		
		final String SPLIT = "@@";
		final String fileName = "users.txt";
		
		try {
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);      
				bw.write(userName + SPLIT + pwdHash);
				bw.newLine();
			bw.close();
			fw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
