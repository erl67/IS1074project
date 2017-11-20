package is1074.password;

import java.util.HashMap;
/**
 * Class AddUser
 * author : ERL67
 * created: 11/19/2017
 */
import java.util.Scanner;

public class AddUser {

	public static void main(String[] args) {

		HashMap<String, String> userMap = FileManager.readUsers();
		Scanner scanner = new Scanner(System.in);
		String userName = null;
		String password;

		do {
			System.out.print("Enter username: ");
			userName = scanner.nextLine();
			userName = (userMap.get(userName) == null) ? userName : null;
			if (userName==null) System.out.println("Username already taken. Try again.\n");
		} while (userName == null);

		System.out.print("Enter password: ");
		password = scanner.nextLine();
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
