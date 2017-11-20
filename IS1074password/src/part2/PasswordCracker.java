package part2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import part1.FileManager;
import part1.JavaMD5Hash;

import com.google.common.base.Stopwatch;


public class PasswordCracker {

	public static void main(String[] args) {
		
		Stopwatch timer = Stopwatch.createUnstarted();

		HashMap<String, String> userMap = FileManager.readUsers();
		ArrayList<String> wordList = FileManager.getDictionary();

		System.out.print("Enter username of password to crack: ");
		Scanner scanner = new Scanner(System.in);
		String userName = scanner.nextLine();
		scanner.close();

		String testPassword = userMap.get(userName);
		if (testPassword == null) {
			System.out.println("Not a valid user");
			return;
		}
		
		timer.start();
		
		if (crackPassword(testPassword, wordList)) {
			System.out.println("\nElapsed time: " + timer.toString());
		} else {
			System.out.println("Unable to crack the password after " + timer.toString());
		}
	}
	
	public static boolean crackPassword (String testPassword, ArrayList<String> wordList) {
		
		boolean cracked = false;
		
		// crack type1 plain words first
		for (String plaintext : wordList) {
			if (JavaMD5Hash.md5(plaintext).equals(testPassword)) {
				System.out.println("Password match: " + plaintext);
				//timer.stop();
				cracked = true;
			}
		}

		if (!cracked) {

			// build array of possible special combinations, could use
			// string.tochararray() but faster to do indvidually
			char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '!', '@', '#', '$', '%', '^', '&', '*', '(', '0', '{', '}', '-' };
			ArrayList<String> charCombos = new ArrayList<String>();

			for (char special0 : chars) {
				charCombos.add(String.valueOf(special0));
				for (char special1 : chars) {
					charCombos.add(String.valueOf(special0) + String.valueOf(special1));
				}
			}

			for (String plaintext : wordList) {
				
				ArrayList<String> type2words = new ArrayList<String>();

				for (String outer : charCombos) {		//create all type 2 passwords for a given word.
					type2words.add(plaintext + outer);
					type2words.add(outer + plaintext);
					System.out.println(plaintext + outer + "\t" + outer + plaintext);
					for (String inner : charCombos) {
						type2words.add(outer + plaintext + inner);
					}
				}
				
				for (String type2plaintext : type2words) {		//test hash of new type2 words against password hash
					System.out.println(type2plaintext);
					if (JavaMD5Hash.md5(type2plaintext).equals(testPassword)) {
						System.out.println("Password match: " + type2plaintext);
						cracked = true;
						return cracked;
					}
				}

			}
		}
		return cracked;
	}

}


//3 length special chars, takes forever to crack
//for (int i = 0; i < chars.length; i++) {
//	charCombos.add(String.valueOf(chars[i]));
//	for (int j = 0; j < chars.length; j++) {
//		charCombos.add(String.valueOf(chars[i]) + String.valueOf(chars[j]));
//		for (int k = 0; k < chars.length; k++) {
//			charCombos.add(String.valueOf(chars[i]) + String.valueOf(chars[j]) + String.valueOf(chars[k]));
//		}
//	}
//}

//was working on longer lengths of special characters, until realizing it would take months to crack more than a few
//for (char special0: chars) {
//	String combo = String.valueOf(special0);
//	charCombos.add(combo);
//	int x = 0;
//	while (combo.length() < 5) {
//		for (int i = 1; i < x; i++) {
//			combo+= String.valueOf(chars[i]);
//			charCombos.add(combo);
//		}
//	}
//}
