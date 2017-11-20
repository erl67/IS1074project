package part1;
/**
 * Class AddUser
 * author : ERL67
 * created: 11/19/2017
 */
import java.util.Scanner;

public class AddUser {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        scanner.close();
        
        if (userName.isEmpty() || password.isEmpty()) {
        	System.out.println("\nInvalid input. Try Again.");
        } else {
            String pwdHash = JavaMD5Hash.md5(password);
            System.out.println("\nYour username: " + userName);
            System.out.println("\nYour password: " + password);
            System.out.println("\nPassword MD5: " + pwdHash);
        	FileManager.writeUser(userName, pwdHash);
        }
	}
}
