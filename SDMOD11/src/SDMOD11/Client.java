package SDMOD11;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {
	// IO streams
	DataOutputStream outServer = null;
	DataInputStream inServer = null;

	@Override
	public void start(Stage clientStage) {
		BorderPane paneForTextField = new BorderPane();
		paneForTextField.setPadding(new Insets(5, 10, 5, 10));
		paneForTextField.setLeft(new Label("Enter number to verify prime: "));

		TextField textField = new TextField();
		textField.setAlignment(Pos.BOTTOM_RIGHT);
		paneForTextField.setCenter(textField);

		BorderPane mainPane = new BorderPane();
		TextArea textArea = new TextArea();
		mainPane.setCenter(new ScrollPane(textArea));
		mainPane.setTop(paneForTextField);

		Scene scene = new Scene(mainPane, 450, 200);
		clientStage.setTitle("Software Development Module 11 Client");
		clientStage.setScene(scene);
		clientStage.show();

		try {
			// Socket Connection
			Socket socket = new Socket("localhost", 8000);

			// input stream
			inServer = new DataInputStream(socket.getInputStream());

			// output stream
			outServer = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			textArea.appendText(e.toString() + '\n');
		}

		textField.setOnAction(e -> {
			try {
				int prime = Integer.parseInt(textField.getText().trim());

				outServer.writeInt(prime);
				outServer.flush();

				boolean boolPrime = inServer.readBoolean();

				textArea.appendText("Prime number to verify is " + prime + "\n");

				if (boolPrime == true) {
					textArea.appendText(prime + " is a prime number!\n");
				} else {
					textArea.appendText(prime + " is not a prime number.\n");
				}

			} catch (IOException ex) {
				System.err.println(ex);
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}
