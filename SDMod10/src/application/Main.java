package application;

import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Software Development I Assignment 9
 * 
 * @author Christopher Ehrhardt
 * @version 1.09
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("SDMod6.fxml"));
			primaryStage.setTitle("Word Counter +");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		getConnection();
		launch(args);
	}

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/word_occurrences";
			String username = "root";
			String password = "password";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public static void post(String word, int count) throws Exception {

		Connection con = getConnection();
		PreparedStatement posted = con.prepareStatement(
				"INSERT INTO word_occurrences.word (word, word_count) VALUES ('" + word + "', '" + count + "')");

		posted.executeUpdate();
	}

	public static ArrayList<String> get() throws Exception {
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT word, word_count FROM word_occurrences.word");

			ResultSet result = statement.executeQuery();

			ArrayList<String> array = new ArrayList<String>();
			while (result.next()) {
				String insert = result.getString("word_count") + " " + result.getString("word");
				array.add(insert);
			}
				return array;

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public static class Word extends Main implements Comparable<Word> {
		String word;
		int count;

		@Override
		public int hashCode() {
			return word.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return word.equals(((Word) obj).word);
		}

		@Override
		public int compareTo(Word b) {
			return b.count - count;
		}
	}
}
