package is1074.password;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

	public static HashMap<String, String> readUsers() {
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

	public static ArrayList<String> getDictionary() {
		final String fileName = "dictionary.txt";
		ArrayList<String> wordList = new ArrayList<String>();

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty())
					wordList.add(line);
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return wordList;
	}

}
