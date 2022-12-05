package org.Helpers;

public class StringHelper {
    /**
     * Performs soft substring. If length of line is less than substring needs,
     * method will return it till the end of line without any exceptions.
     * @param line Line to substring
     * @param startIndex Index to start from
     * @param length Amount of characters to take
     * @return Substring of line with defined params
     **/
    public static String substring(String line, int startIndex, int length) {
        if(startIndex > line.length()) {
            return "";
        }
        if(startIndex < 0) {
            startIndex = 0;
        }
        return line.substring(startIndex, Math.min(line.length() - startIndex, length));
    }
}
