package Test;

import Replacer.RegexReplacer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

class RegexReplacerTest {

    @org.junit.jupiter.api.Test
    void replaceInFile() throws IOException {
        File fileIn = new File("src/Test/test.txt");
        File fileOut = new File("src/Test/testOut.txt");
        File fileResults = new File("src/Test/testResults.txt");
        String[] regex =  {"[0-9]{3}", "O"};
        String[] replacement = {"cat", "ring"};
        try {
            RegexReplacer.replaceInFile(fileIn, fileOut, regex, replacement);
        } catch (IOException | RegexReplacer.DifferentLengthException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileOut));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert br != null;
        //noinspection MismatchedQueryAndUpdateOfStringBuilder
        StringBuilder outString = new StringBuilder();
        String line;
        long lines = Files.lines(Paths.get(fileOut.getPath())).count();
        for(long i = 0; i < lines; i++) {
            line = br.readLine();
            outString.append(line).append(i < lines-1 ? '\n' : "");
        }
        br.close();

        try {
            br = new BufferedReader(new FileReader(fileResults));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //noinspection MismatchedQueryAndUpdateOfStringBuilder
        StringBuilder resultsString = new StringBuilder();
        lines = Files.lines(Paths.get(fileResults.getPath())).count();
        for(long i = 0; i < lines; i++) {
            line = br.readLine();
            resultsString.append(line).append(i < lines-1 ? '\n' : "");
        }
        br.close();

        fileOut.delete();

        assert outString.toString().equals(resultsString.toString());
    }

    @org.junit.jupiter.api.Test
    void replace() {
        String catering = "436eO";
        String[] regex =  {"[0-9]{3}", "O"};
        String[] replacement = {"cat", "ring"};
        try {
            catering = RegexReplacer.replace(catering, regex, replacement);
        } catch (RegexReplacer.DifferentLengthException e) {
            e.printStackTrace();
        }
        assert catering.equals("catering");
    }
}