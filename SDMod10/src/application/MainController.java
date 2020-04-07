package application;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * Main Controller Class
 * 
 * @author Christopher Ehrhardt
 * @version 1.09
 */
public class MainController extends Main implements Initializable {

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

	/**
	 * generateArray Method
	 * 
	 * @author Christopher Ehrhardt
	 * @param siteURL
	 * @return uniqueWords A count of total uniqueWords for JUnit testing
	 * @throws IOException
	 */
	public int generateArray(URL siteURL) throws IOException {
		int uniqueWords = 0;

		Map<String, Word> countMap = new HashMap<String, Word>();

		Document doc = Jsoup.connect("" + siteURL + "").get();

		String text = doc.body().text();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] words = line.split("[^A-ZÃ…Ã„Ã–a-zÃ¥Ã¤Ã¶]+");
			for (String word : words) {
				if ("".equals(word)) {
					continue;
				}

				Word wordObj = countMap.get(word.toLowerCase());
				if (wordObj == null) {
					wordObj = new Word();
					wordObj.word = word;
					wordObj.count = 0;
					countMap.put(word, wordObj);
				}

				wordObj.count++;
			}
		}

		reader.close();

		SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());

		for (Word word : sortedWords) {
			try {
				post(word.word, word.count);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return uniqueWords;

	}

	public void setOrderAsc(ActionEvent setOrderAsc) {
		sortOrder = false;
	}

	public void setOrderDesc(ActionEvent setOrderDesc) {
		sortOrder = true;
	}

	/**
	 * displayButtonClick Method
	 * 
	 * @author Christopher Ehrhardt
	 * @param displayButtonClick
	 * @throws Exception
	 */
	public void displayButtonClick(ActionEvent displayButtonClick) throws Exception {

		showResults = Integer.parseInt(numResults.getText());
		siteURL = getURL();
		generateArray(siteURL);

		ArrayList<String> wordResults = get();

		if (sortOrder == false) {
			Collections.reverse(wordResults);
		}
		wordResults.subList(showResults, wordResults.size()).clear();

		resultsList.clear();
		resultsList.appendText(wordResults.toString().replaceAll(",", "\n"));
	}

}
