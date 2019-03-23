package Replacer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <h1>Replacer.RegexReplacer</h1>
 * The Replacer.RegexReplacer implements simple methods for replacing
 * substrings matching the regex patterns in given string or file
 * with other substrings.
 *
 * @author  Konrad Kurzydym
 * @version 1.0
 * @since   2019-03-23
 */
public class RegexReplacer {

    /** Provide regex patterns and replacement strings in arrays - each regex corresponds
     * to the replacement string of the same index.
     * The replacements happen one after another. The method works line by line.
     * @param fileIn Read-only file to which replacements will happen.
     * @param fileOut File to which replacements will be saved.
     * @param regex Array with regex patterns
     * @param replacement Array with strings which will replace matches
     * @return void
     * @exception DifferentLengthException When there's different number of regexes and replacements
     * @exception IOException On input error.
     * @see IOException*/
    public static void replaceInFile(File fileIn, File fileOut, String[] regex, String[] replacement) throws IOException, DifferentLengthException {
        if(regex.length != replacement.length) {
            throw new DifferentLengthException("Different length of regex and replacement!");
        }
        BufferedReader br = new BufferedReader(new FileReader(fileIn));
        new FileWriter(fileOut, false).close(); //clear file
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut, true));
        String line;
        //noinspection LoopStatementThatDoesntLoop
        long lines = Files.lines(Paths.get(fileIn.getPath())).count();
        for(long i = 0; i < lines; i++) {
            line = br.readLine();
            for(int j = 0; j < regex.length; j++) {
                line = line.replaceAll(regex[j], replacement[j]);
            }
            writer.append(line);
            if(i < lines - 1) {
                writer.append(System.getProperty("line.separator"));
            }
        }
        br.close();
        writer.close();
    }

    /** Provide regex patterns and replacement strings in arrays - each regex corresponds
     * to the replacement string of the same index.
     * The replacements happen one after another.
     * @param textToReplace Text in which replacements will happen.
     * @param regex Array with regex patterns
     * @param replacement Array with strings which will replace matches
     * @return String after replacements
     * @exception DifferentLengthException When there's different number of regexes and replacements */
    public static String replace(String textToReplace, String[] regex, String[] replacement) throws DifferentLengthException {
        if(regex.length != replacement.length) {
            throw new DifferentLengthException("Different length of regex and replacement!");
        }
        for(int i = 0; i < regex.length; i++) {
            textToReplace = textToReplace.replaceAll(regex[i], replacement[i]);
        }
        return textToReplace;
    }

    public static class DifferentLengthException extends Exception{
        DifferentLengthException(String s){
            super(s);
        }
    }
}

