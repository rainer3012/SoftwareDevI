import java.math.BigDecimal;
import java.util.Random;

public class Application {
	static double sumSingle = 0;
	static double sumMulti = 0;
	static double singleTime = 0;
	static double multiTime = 0;
	static int intArray[] = new int[200000000];
	
	public static double compareSums(double sumSingle, double SumDouble) {
		double sumDiff = 0;
		sumDiff = sumSingle - sumMulti;
		return sumDiff;
	}
	
	public static void main(String[] args) {

		System.out.println("Generating Integer Array, please wait...");

		for (int i = 0; i < intArray.length; i++) {
			Random randomInt = new Random();
			intArray[i] = randomInt.nextInt(10) + 1;
		}

		System.out.println("Array Generated, now calculating sum in single thread...");

		double timeStartSingle = System.nanoTime();
		for (int i = 0; i < intArray.length; i++) {
			int value = intArray[i];
			sumSingle += value;
		}
		
		singleTime = System.nanoTime() - timeStartSingle;

		System.out.println("\nSum of array equals " + BigDecimal.valueOf(sumSingle).toPlainString() 
				+ "\nSingle thread processing time: " + BigDecimal.valueOf(singleTime).toPlainString() + " ns");

		System.out.println("\nNow calculating sum in multiple threads...");



		Multithread thread1 = new Multithread(0, intArray);
		Multithread thread2 = new Multithread(20000000, intArray);
		Multithread thread3 = new Multithread(40000000, intArray);
		Multithread thread4 = new Multithread(60000000, intArray);
		Multithread thread5 = new Multithread(80000000, intArray);
		Multithread thread6 = new Multithread(100000000, intArray);
		Multithread thread7 = new Multithread(120000000, intArray);
		Multithread thread8 = new Multithread(140000000, intArray);
		Multithread thread9 = new Multithread(160000000, intArray);
		Multithread thread10 = new Multithread(180000000, intArray);

		double timeStartMulti = System.nanoTime();	
		thread1.run();
		thread2.run();
		thread3.run();
		thread4.run();
		thread5.run();
		thread6.run();
		thread7.run();
		thread8.run();
		thread9.run();
		thread10.run();
		
		sumMulti = thread1.getSum() +
				   thread2.getSum() +
				   thread3.getSum() +
				   thread4.getSum() +
				   thread5.getSum() +
				   thread6.getSum() +
				   thread7.getSum() +
				   thread8.getSum() +
				   thread9.getSum() +
				   thread10.getSum();
		
		multiTime = System.nanoTime() - timeStartMulti;
		System.out.println("\nSum of array equals " + BigDecimal.valueOf(sumMulti).toPlainString() 
				+ "\nMultiple thread processing time: " + BigDecimal.valueOf(multiTime).toPlainString() +" ns");

				   
		
	}
}
