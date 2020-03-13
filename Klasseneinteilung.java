import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Klasseneinteilung implements Runnable {

	final static String FILE = "/dg13/home/marcseid/devel/eclipse-workspace/HelloWorld/src/misc/wishes2.txt";

	private int[] zahlen;
	private int aus;
	private int anzZahlen;

	public Klasseneinteilung(int aus, int anzZahlen) {
		this.aus = aus;
		this.anzZahlen = anzZahlen;
		this.zahlen = new int[anzZahlen + 2];
	}

	private void print() {
		System.out.println();
		for (int zahl : zahlen) {
			System.out.print(zahl + " ");
		}
	}

	private void doit(int fehlen, int start) {
		if (fehlen > 0) {
			for (int i = start; i <= aus; i++) {
				zahlen[anzZahlen - fehlen] = i;
				doit(fehlen - 1, i + 1);
			}
		} else {
			score();
			//print();
		}
	}

	private void score() {
//		System.out.println();
//		for (int zahl : zahlen) {
//			System.out.print(zahl + " ");
//		}
	}

	public void run() {
		doit(anzZahlen, 1);
	}

	public static String[][] loadFromFile(String filename) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			List<String[]> lines = new ArrayList<String[]>();
			String thisLine = br.readLine();
			while ((thisLine = br.readLine()) != null) {
			     lines.add(thisLine.split(","));
			     thisLine = br.readLine();
			}
			String[][] dataFromFile = new String[lines.size()][0];
			lines.toArray(dataFromFile);
			return dataFromFile;
		}
	}
	
	public static int[] nameToNumber(String[][] input) {
		int[] numberArray = new int[input.length];
		for (int i = 0; i < numberArray.length; i++) {
			numberArray[i] = i;
		}
		return numberArray;
	}
	
	public static String[][] namesToNumbers(String[][] input) {
		String[][] workCopy = input;
		// int[][] twoDNumberArray = new int[input.length][input[0].length];
		for (int i = 0; i < workCopy.length; i++) {
			String toTest = input[i][0];
			int j = 0;
			for (String[] s: workCopy) {
				int k = 0;
				for (String testString: s) {
			        if (testString.equals(toTest)) {
			        	workCopy[j][k] = Integer.toString(j); 
			        }
			        k++;
			    }
			    j++;
			}
		}
		return workCopy;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String[][] wishes = loadFromFile(FILE);
		System.out.println(Arrays.deepToString(wishes).replace("], ", "]\n"));
		System.out.println(Arrays.toString(nameToNumber(wishes)));
		System.out.println(Arrays.deepToString(namesToNumbers(wishes)).replace("], ", "]\n"));
		
		

		int alleSchueler = 20;
		int schuelerProKlasse;
		if (alleSchueler % 2 == 0) {
			schuelerProKlasse = alleSchueler / 2;
		} else {
			schuelerProKlasse = (alleSchueler + 1) / 2;
		}

		Thread thread = new Thread(new Klasseneinteilung(alleSchueler, schuelerProKlasse));
		thread.start();
	}
}
