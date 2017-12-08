package is1074.password;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileManager {

	public static void writeUser(String userName, String pwdHash) {
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

	/**
	 * @param getPwdHash boolean to determine if add md5 value to hashmap
	 * @return hashmap key of username, value of md5 for pwd or null
	 */
	public static HashMap<String, String> readUsers(boolean getPwdHash) {
		final String SPLIT = "@@";
		final String fileName = "users.txt";

		HashMap<String, String> userMap = new HashMap<String, String>();

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] lineParts = line.split(SPLIT);	//prevent exposing hash when not necessary
				if (getPwdHash == true) {
					userMap.put(lineParts[0], lineParts[1]);
				} else {
					userMap.put(lineParts[0], null);
				}
				
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return userMap;
	}

	/**
	 * @param minLength minimum length of word to check
	 * @return arraylist of dictionary words
	 */
	public static ArrayList<String> getDictionary(int minLength) {
		final String fileName = "dictionary.txt";
		ArrayList<String> wordList = new ArrayList<String>();

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty())
					if (line.length() > minLength) wordList.add(line);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return wordList;
	}
	
	public static String md5(String input) {
		String md5 = null;
		if (null == input)
			return null;
		try {
			// Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());
			// Converts message digest value in base 16 (hex)
			md5 = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}

}
