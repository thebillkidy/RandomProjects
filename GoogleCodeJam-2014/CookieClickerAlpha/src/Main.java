import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		// Configure
		String inputFile = "./S1.in";
		String outputFile = "./S1.out";
		
		// Open reader and writer
		Scanner scanner = new Scanner(new File(inputFile));
		PrintWriter pw = new PrintWriter(new File(outputFile));
		
		// Get number of tests
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		
		// Solve the tests
		for (int i = 0; i < numberOfTests; i++) {
			pw.println(_solve(scanner, i + 1, numberOfTests));
		}
		
		// Close the reader and the writer
		scanner.close();
		pw.flush();
	}

	private static String _solve(Scanner scanner, int testNumber, int numberOfTests) {
		// Read the input
		double farmPrice = scanner.nextDouble();
		double extraCookiesPerSecond = scanner.nextDouble();
		double resultAmountOfCookies = scanner.nextDouble();
		scanner.nextLine();
		
		// Set initial params
		int farmCount = 0;
		double baseCookiesPerSecond = 2.0;
		
		// calculate first result
		double foundResult = _calculateTimeToReachResult(baseCookiesPerSecond, extraCookiesPerSecond, farmCount, farmPrice, resultAmountOfCookies);
		farmCount++;
		double foundResult2 = _calculateTimeToReachResult(baseCookiesPerSecond, extraCookiesPerSecond, farmCount, farmPrice, resultAmountOfCookies);
		
		// While we find smaller values
		while (foundResult2 < foundResult) {
			farmCount++;
			foundResult = foundResult2;
			foundResult2 = _calculateTimeToReachResult(baseCookiesPerSecond, extraCookiesPerSecond, farmCount, farmPrice, resultAmountOfCookies);
		}
		
		// Determine the result
		String calculatedResult = _calculateResult(foundResult, testNumber);
		System.out.println(calculatedResult);
		return calculatedResult;
	}
	
	private static double _calculateTimeToReachResult(double cookiesPerSecBase, double cookiesPerSecExtra, int farmCount, double farmPrice, double result) {
		double farmsTimeNeeded = _calculateTotalSecondsForAllFarms(cookiesPerSecBase, cookiesPerSecExtra, farmCount, farmPrice, result);
		double timeNeededForResult = _calculateSecondsNeededForFarm(result, farmCount, cookiesPerSecBase, cookiesPerSecExtra) + farmsTimeNeeded;
		return timeNeededForResult;
	}
	
	private static double _calculateTotalSecondsForAllFarms(double cookiesPerSecBase, double cookiesPerSecExtra, int farmCount, double farmPrice, double result) {
		double timeNeeded = 0.0;
		
		for (int i = 0; i < farmCount; i++) {
			timeNeeded += _calculateSecondsNeededForFarm(farmPrice, i, cookiesPerSecBase, cookiesPerSecExtra);
			//cookieCount = 0; // Reset cookieCount after buying a base
		}
		
		return timeNeeded;
	}

	private static double _calculateSecondsNeededForFarm(double farmPrice, int farmCount, double cookiesPerSecBase, double cookiesPerSecExtra) {
		return farmPrice / (cookiesPerSecBase + (farmCount * cookiesPerSecExtra));
	}
	
	private static String _calculateResult(double result, int testNumber) {
		return String.format("Case #%d: %.7f", testNumber, result);
	}
}