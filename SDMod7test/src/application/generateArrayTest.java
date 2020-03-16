package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

class generateArrayTest {

	@Test
	void test() throws MalformedURLException{
		MainController test = new MainController();
		int output;
		URL testURL = new URL("http://shakespeare.mit.edu/macbeth/full.html");
		try {
			output = test.generateArray(testURL);
			assertEquals(3480, output);
		} catch (IOException e) {
		}

	}

}
