package application;

import java.io.File;
import java.io.FileNotFoundException;
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
//import javafx.scene.control.Button;
//import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class MainController implements Initializable {
	
Map<String, Integer> wordsFound = new HashMap<String, Integer>(); // Create hashMap
ArrayList<Entry<String, Integer>> wordArray = new ArrayList<>(); // Create arrayList from hashMap
boolean sortOrder = true;
int showResults = 20;
	
	@FXML
	public ToggleGroup sortGroup;	
	public TextArea resultsList;
	public TextField numResults;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
	
	}
	
	public void generateArray() {
		wordsFound.clear();
		wordArray.clear();
		Scanner scnr;
		try {
			scnr = new Scanner(new File("full.html"));

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
				word = word.replace("[", "");
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		wordArray.addAll(wordsFound.entrySet());
		wordArray.remove(0); // Ignore replaced words
	}
	
	public void setOrderAsc(ActionEvent setOrderAsc) {
		sortOrder = false;
	}
	
	public void setOrderDesc(ActionEvent setOrderDesc) {
		sortOrder = true;
	}
	

	public void displayButtonClick(ActionEvent displayButtonClick) {
		
		showResults = Integer.parseInt(numResults.getText());
		generateArray();
		
		if (sortOrder == false) {
			wordArray.sort(Comparator.comparing(Entry<String, Integer>::getValue));
			wordArray.subList(showResults, wordArray.size()).clear();
			resultsList.clear();
			resultsList.appendText(wordArray.toString().replaceAll(",", "\n"));
		} else {
			wordArray.sort(Comparator.comparing(Entry<String, Integer>::getValue).reversed()); // Sort arrayList
			wordArray.subList(showResults, wordArray.size()).clear();
			resultsList.clear();
			resultsList.appendText(wordArray.toString().replaceAll(",", "\n"));
		}
		
	}
	
}
