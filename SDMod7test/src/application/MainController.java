package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import java.util.Map.Entry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class MainController implements Initializable {

	Map<String, Integer> wordsFound = new HashMap<String, Integer>(); // Create hashMap
	ArrayList<Entry<String, Integer>> wordArray = new ArrayList<>(); // Create arrayList from hashMap
	boolean sortOrder = true;
	int showResults = 20;
	URL siteURL = null;

	@FXML
	public ToggleGroup sortGroup;
	public TextArea resultsList;
	public TextField numResults;
	public TextField urlField;
	public String countURL;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public URL getURL() {

		countURL = urlField.getText();

		try {
			siteURL = new URL(countURL);
		} catch (MalformedURLException e) {
		}
		return siteURL;
	}

	public int generateArray(URL siteURL) throws IOException {
		int uniqueWords = 0;
		wordsFound.clear();
		wordArray.clear();
		Scanner scnr;
		BufferedReader reader = new BufferedReader(new InputStreamReader(siteURL.openStream()));

		scnr = new Scanner(reader);

		while (scnr.hasNext()) {
			String word = scnr.next().toLowerCase();
			word = word.replaceAll("name=.*?\\>", "") // Strip unwanted characters and tags
					.replaceAll("\".*?\\>", "").replaceAll("\\<.*?\\>", "").replaceAll("=", "").replaceAll(";", "")
					.replaceAll("!", "").replaceAll(":", "").replaceAll(",", "").replaceAll("--", "").replace("[", "")
					.replace(".", "").replace("?", "").replace("<a", "");

			if (!wordsFound.containsKey(word)) {
				wordsFound.put(word, 1); // Add new word to list
				uniqueWords++;
			} else {
				wordsFound.put(word, wordsFound.get(word) + 1); // Increment existing word
			}

		}
		scnr.close();


	wordArray.addAll(wordsFound.entrySet());wordArray.remove(0); // Ignore replaced words

	return uniqueWords;

	}

	public void setOrderAsc(ActionEvent setOrderAsc) {
		sortOrder = false;
	}

	public void setOrderDesc(ActionEvent setOrderDesc) {
		sortOrder = true;
	}

	public void displayButtonClick(ActionEvent displayButtonClick) throws IOException {

		showResults = Integer.parseInt(numResults.getText());
		siteURL = getURL();
		generateArray(siteURL);

		if (sortOrder == false) {
			wordArray.sort(Comparator.comparing(Entry<String, Integer>::getValue));
		} else {
			wordArray.sort(Comparator.comparing(Entry<String, Integer>::getValue).reversed()); // Sort arrayList
		}
		wordArray.subList(showResults, wordArray.size()).clear();
		resultsList.clear();
		resultsList.appendText(wordArray.toString().replaceAll(",", "\n"));
	}

}
