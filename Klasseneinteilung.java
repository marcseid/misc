import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Klasseneinteilung implements Runnable {
    final static String FILE = "src/wishes.txt";

    static String[][] wishes;
    static int[] schuelerInt;
    static int[][] wuensche;
    static List<int[]> moeglichkeiten = new ArrayList<int[]>();
    private int[] zahlen;
    private int aus;
    private int anzZahlen;

    public Klasseneinteilung(int aus, int anzZahlen) {
        this.aus = aus;
        this.anzZahlen = anzZahlen;
        this.zahlen = new int[anzZahlen + 2];
    }

    private void print1() {
        System.out.println();
        for (int zahl : zahlen) {
            System.out.print(zahl + " ");
        }
    }


    private void doit(int fehlen, int start) {
        zahlen[5] = 0;
        zahlen[6] = 0;
        if (fehlen > 0) {
            for (int i = start; i <= aus; i++) {
                zahlen[anzZahlen - fehlen] = i;
                doit(fehlen - 1, i + 1);
            }
        } else {
            score();
            moeglichkeiten.add(zahlen);
            print1();
        }
    }

    private void score() {
        for (int i = 0; i < zahlen.length; i++) {
            for (int j = 0; j < wuensche.length; j++) {
                if (zahlen[i] == wuensche[j][0]) {
                    berechneScore(zahlen[i], zahlen, wuensche[j]);
                }
            }
        }
    }
    public void berechneScore(int uebereinstimmung, int[] testArray, int[] wunschArray) {
        for (int i = 0; i < (testArray.length - 2); i++) {
            int s = 3;
            for (int j = 1; j < wunschArray.length; j++) {
                    if (testArray[i] == wunschArray[j]) {
                    testArray[5] = testArray[5] + 1;
                    testArray[6] = testArray[6] + s;
                }
                s--;
            }
        }
    }

    public void run() {
        doit(anzZahlen, 1);
    }

    public static String[][] loadFromFile(String filename) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String[]> lines = new ArrayList<String[]>();
            String thisLine;
            while ((thisLine = br.readLine()) != null) {
                lines.add(thisLine.split(","));
            }
            String[][] dataFromFile = new String[lines.size()][0];
            lines.toArray(dataFromFile);
            return dataFromFile;
        }
    }

    public static int[] nameToNumber(String[][] input) {
        int[] numberArray = new int[input.length];
        for (int i = 0; i < numberArray.length; i++) {
            numberArray[i] = i + 1;
        }
        return numberArray;
    }

    public static int[][] namesToNumbers(String[][] input) {
        String[][] workCopy = input;
        int[][] twoDNumberArray = new int[input.length][input[0].length];
        for (int i = 0; i < workCopy.length; i++) {
            String toTest = input[i][0];
            int j = 0;
            for (String[] s : workCopy) {
                int k = 0;
                for (String testString : s) {
                    if (testString.equals(toTest)) {
                        workCopy[j][k] = Integer.toString(i + 1);
                    }
                    k++;
                }
                j++;
            }
        }
        for (int a = 0; a < workCopy.length; a++) {
            for (int b = 0; b < workCopy[0].length; b++) {
                twoDNumberArray[a][b] = Integer.parseInt(workCopy[a][b]);
            }
        }
        return twoDNumberArray;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        wishes = loadFromFile(FILE);
        System.out.println(Arrays.deepToString(wishes).replace("], ", "]\n"));
        schuelerInt = nameToNumber(wishes);
        wuensche = namesToNumbers(wishes);
        System.out.println(Arrays.toString(schuelerInt));
        System.out.println(Arrays.deepToString(wuensche).replace("], ", "]\n"));


        int alleSchueler = schuelerInt.length;
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
