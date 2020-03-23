
public class Multithread extends Application implements Runnable{

	private int arrayStartPoint;
	private double sum = 0;
	int intArray[];
	
	public Multithread() {
		}
	

	public Multithread(int arrayStartPoint, int[] intArray) {
		super();
		this.arrayStartPoint = arrayStartPoint;
		this.sum = 0;
		this.intArray = intArray;
	}


	public void run() {
		for (int i = arrayStartPoint; i < (arrayStartPoint + 20000000); i++) {
			int value = intArray[i];
			sum += value;
		}
	}


	public double getSum() {
		return sum;
	}


}
