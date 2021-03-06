package is1074.password;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.common.base.Stopwatch; //using guava timer

public class PasswordCracker {

	public static void main(String[] args) {

		HashMap<String, String> userMap = FileManager.readUsers(true);
		ArrayList<String> wordList = FileManager.getDictionary(2); // Optimized to only use words of minimum length, based on password rules

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

		// crack type1 words first
		for (String plaintext : wordList) {
			if (FileManager.md5(plaintext).equals(testPassword)) {
				System.out.println("Password match: " + plaintext);
				cracked = true;
			}
		}

		if (!cracked) {
			System.out.println("No match with dictionary words, trying special characters");

			ArrayList<String> charsList = new ArrayList<String>();
			ArrayList<String> charCombos = new ArrayList<String>();

				int charset = 10;									// added a switch to allow for different charsets and compare runtimes
	
		        switch (charset) {
		            case 0:  charsList.add("0)");					//177240 variants per word, total variants for x specialchars = (x^2+x+1)^(x^2+x+1)-1
		            case 9:  charsList.add("9(");					//117648 words
		            case 8:  charsList.add("8*");
		            case 7:  charsList.add("7&");
		            case 6:  charsList.add("6^");
		            case 5:  charsList.add("5%");					//12320 words, me:chair1! = 2.634min
		            case 4:  charsList.add("4$");					//chair1! = 1.02m, chair1=1.037m
		            case 3:  charsList.add("3#");					//chair1! = 21.54s
		            case 2:  charsList.add("2@");					//440 words, chair1! = 5.852s
		            case 1:  charsList.add("1!"); break;			//48 words, chair1! = 1.094s
		            case 10: charsList.add("12344567890"); break; 	//17688 words, chair1 = 3.237m
		            case 11: charsList.add("1"); break; 			//2 combos c=x^2+x, 8 words w=(c+1)^(c+1)-1
		            case 20: charsList.add("0123@#$%&!"); break;	//me:chair1! = 2.555min
		            default: charsList.add("0123456789@#$%&");		//240 combos, 58080 words, chair1 = 14.34m
		        }
	        
			char[] chars = charsList.stream().collect(Collectors.joining()).toCharArray(); // convert string list to char array for iterating

			for (char special0 : chars) {
				charCombos.add(String.valueOf(special0));
				for (char special1 : chars) {
					charCombos.add(String.valueOf(special0) + String.valueOf(special1));
				}
			}

			for (String plaintext : wordList) {
				System.out.println(plaintext);
				ArrayList<String> type2words = new ArrayList<String>();

				for (String outer : charCombos) { // create all type 2 passwords for a given word.
					type2words.add(plaintext + outer);
					type2words.add(outer + plaintext);
					for (String inner : charCombos) {
						type2words.add(outer + plaintext + inner);
					}
				}

				for (String type2plaintext : type2words) { // test hash of new type2 words against password hash
					if (FileManager.md5(type2plaintext).equals(testPassword)) {
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