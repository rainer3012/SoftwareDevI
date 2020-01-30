// CEN-3024C
// Christopher Ehrhardt
// 1/29/2020

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class WordCounter {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner scnr = new Scanner(new File("full.html")); // Load input file

		Map<String, Integer> wordsFound = new HashMap<String, Integer>(); // Create hashMap

		while (scnr.hasNext()) {
			String word = scnr.next().toLowerCase();
			word = word.replaceAll("name=.*?\\>", ""); // Strip unwanted characters and tags
			word = word.replaceAll("\".*?\\>", "");
			word = word.replaceAll("\\<.*?\\>", "");
			word = word.replaceAll("=", "");
			word = word.replaceAll(";", "");
			word = word.replaceAll("!", "");
			word = word.replaceAll(":", "");
			word = word.replaceAll(",", "");
			word = word.replaceAll("--", "");
			word = word.replace(".", "");
			word = word.replace("?", "");
			word = word.replace("<a", "");

			if (!wordsFound.containsKey(word)) {
				wordsFound.put(word, 1); // Add new word to list
			} else {
				wordsFound.put(word, wordsFound.get(word) + 1); // Increment existing word
			}

		}
		scnr.close();

		ArrayList<Entry<String, Integer>> wordArray = new ArrayList<>(); // Create arrayList from hashMap
		wordArray.addAll(wordsFound.entrySet());
		wordArray.sort(Comparator.comparing(Entry<String, Integer>::getValue).reversed()); // Sort arrayList

		for (int i = 1; i < wordArray.size(); i++) {
			System.out.println(wordArray.get(i)); // Print top 20 entries
		}

	}
}
