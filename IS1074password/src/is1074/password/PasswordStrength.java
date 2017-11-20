package is1074.password;

import java.util.ArrayList;
import java.util.Scanner;

public class PasswordStrength {

	public static void main(String[] args) {

		ArrayList<String> wordList = FileManager.getDictionary(0);
		char[] chars = { '!', '@', '#', '$', '%', '^', '&', '*', '(', '0', '{', '}', '-' };
		char[] nums = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		boolean strong = true;
		String testPassword;
		Scanner scanner = new Scanner(System.in);

		do {
			System.out.print("Enter password to test strength: ");
			testPassword = scanner.nextLine();
			if (testPassword.length() < 6)
				System.out.println("Too short. Must be at least 7 letters\n");
		} while (testPassword.length() < 6);
		scanner.close();

		int strengthScore = (testPassword.length() > 7) ? 5 : 0;

		if (wordList.contains(testPassword)) {
			System.out.println("This password is in the dictionary. Weak.");
			strong = false;
		} else {
			for (String word : wordList) {
				if (testPassword.contains(word)) {
					System.out.println("This password contains a dictionary word. Moderate.");
					strong = false;
					break;
				}
			}

			for (char special : chars)
				if (testPassword.indexOf(special) != -1)
					strengthScore += 2;
			for (char special : nums)
				if (testPassword.indexOf(special) != -1)
					strengthScore++;

			if (strong == true)
				System.out.println("Your password is strong.");
		}
		System.out.println("The overall strength of your password is: " + strengthScore);
	}

}
