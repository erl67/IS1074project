package is1074.password;

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

		HashMap<String, String> userMap = FileManager.readUsers();

		if (userMap.containsKey(userName)) {
			if (userMap.get(userName).equals(JavaMD5Hash.md5(password))) {
				System.out.println("Welcome back " + userName);
			} else {
				System.out.println("No");
			}
		} else {
			System.out.println("Not a valid user");
		}
	}

}
