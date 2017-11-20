package is1074.password;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.common.base.Stopwatch; //using Guava timer

public class PasswordCracker {

	public static void main(String[] args) {

		HashMap<String, String> userMap = FileManager.readUsers();
		ArrayList<String> wordList = FileManager.getDictionary(3);	//Optimized to only use words of minimum length, based on password rules

		System.out.print("Enter username of password to crack: ");
		Scanner scanner = new Scanner(System.in);
		String userName = scanner.nextLine();
		scanner.close();

		String testPassword = userMap.get(userName);
		if (testPassword == null) {
			System.out.println("Not a valid user");
		} else {
			Stopwatch timer = Stopwatch.createStarted();
			if (crackPassword(testPassword, wordList)) {
				System.out.println("\nElapsed time: " + timer.toString());
			} else {
				System.out.println("Unable to crack the password after " + timer.toString());
			}
		}
	}

	public static boolean crackPassword(String testPassword, ArrayList<String> wordList) {

		boolean cracked = false;

		// crack type1 plain words first
		for (String plaintext : wordList) {
			if (JavaMD5Hash.md5(plaintext).equals(testPassword)) {
				System.out.println("Password match: " + plaintext);
				cracked = true;
			}
		}

		if (!cracked) {
			System.out.println("No match with dictionary words, trying special characters");

			// build array of possible special combinations, could use
			// string.tochararray() but faster to use stream (about 5x)
			// char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '!', '@', '#', '$', '%', '^', '&', '*', '(', '0', '{', '}', '-' };

			ArrayList<String> charsList = new ArrayList<String>();
			ArrayList<String> charCombos = new ArrayList<String>();
			
			int charset = 99;
			
	        switch (charset) {
	            case 0:  charsList.add("0)");
	            case 9:  charsList.add("9(");
	            case 8:  charsList.add("8*");
	            case 7:  charsList.add("7&");
	            case 6:  charsList.add("6^");
	            case 5:  charsList.add("5%");
	            case 4:  charsList.add("4$");					//chair1! = 1.02m, chair1=1.037m
	            case 3:  charsList.add("3#");					//chair1! = 21.54s
	            case 2:  charsList.add("2@");					//chair1! = 5.852s
	            case 1:  charsList.add("1!"); break;			//chair1! = 1.094s
	            case 10:  charsList.add("12344567890"); break; 	//chair1 = 3.237m
	            default:  charsList.add("0123456789@#$%&");
	        }
	        
	        char[] chars = charsList.stream().collect(Collectors.joining()).toCharArray();	//convert string list to char array
 			
			for (char special0 : chars) {
				charCombos.add(String.valueOf(special0));
				for (char special1 : chars) {
					charCombos.add(String.valueOf(special0) + String.valueOf(special1));
				}
			}

			for (String plaintext : wordList) {
				System.out.println(plaintext);
				ArrayList<String> type2words = new ArrayList<String>();

				for (String outer : charCombos) {		//create all type 2 passwords for a given word.
					type2words.add(plaintext + outer);
					type2words.add(outer + plaintext);
					//System.out.println(plaintext + outer + "\t" + outer + plaintext);
					for (String inner : charCombos) {
						type2words.add(outer + plaintext + inner);
					}
				}
				
				for (String type2plaintext : type2words) {		//test hash of new type2 words against password hash
					//System.out.println(type2plaintext);
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