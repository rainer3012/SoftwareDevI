package SDMOD11;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {
	@Override
	public void start(Stage primaryStage) {
		TextArea textArea = new TextArea();

		Scene scene = new Scene(new ScrollPane(textArea), 450, 200);
		primaryStage.setTitle("Software Development Module 11 Server");
		primaryStage.setScene(scene);
		primaryStage.show();

		new Thread(() -> {
			try {
				// Create a server socket
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> textArea.appendText("Server is running.\n"));

				// Listen for a connection
				Socket socket = serverSocket.accept();

				// Input and output streams
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

				while (true) {
					boolean isPrime = true;
					int primeCandidate = inputFromClient.readInt();
					

					if (primeCandidate % 2 == 0) {
						isPrime = false;
					} else {
						for (int i = 3; i * i <= primeCandidate; i += 2) {
							if (primeCandidate % i == 0) {
								isPrime = false;
							} else {
								isPrime = true;
							}
						}
					}

					outputToClient.writeBoolean(isPrime);

					Platform.runLater(() -> {
						textArea.appendText("Prime candidate received from client: " + primeCandidate + '\n');

					});
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}