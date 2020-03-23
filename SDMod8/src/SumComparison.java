import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SumComparison extends Application {

	@Test
	void testSumEquality() {
		double sumDiff;
		Application.main(null);
		
		sumDiff = Application.compareSums(sumSingle, sumMulti);
	
		assertEquals(0, sumDiff);
	}

	@Test
	void testArraySize() {
		int arraySize = Application.intArray.length;
		
		assertEquals(200000000, arraySize);
	}
}
