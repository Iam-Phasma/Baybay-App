package com.example.baybay;

import java.util.HashMap;
import java.util.Map;

public class Z_BaybayinCanvasConverter {
    private static final Map<String, String> baybayinMap = new HashMap<>();
    static {
        baybayinMap.put("NGA", "\u004E");
        baybayinMap.put("NGE", "\u004E"+"\u0065");
        baybayinMap.put("NGI", "\u004E"+"\u0069");
        baybayinMap.put("NGO", "\u004E"+"\u006F");
        baybayinMap.put("NGU", "\u004E"+"\u0075");

        baybayinMap.put("BA", "\u0042");
        baybayinMap.put("BE", "\u0042"+"\u0065");
        baybayinMap.put("BI", "\u0042"+"\u0069");
        baybayinMap.put("BO", "\u0042"+"\u006F");
        baybayinMap.put("BU", "\u0042"+"\u0075");

        baybayinMap.put("KA", "\u004B");
        baybayinMap.put("KE", "\u004B"+"\u0065");
        baybayinMap.put("KI", "\u004B"+"\u0069");
        baybayinMap.put("KO", "\u004B"+"\u006F");
        baybayinMap.put("KU", "\u004B"+"\u0075");

        baybayinMap.put("DA", "\u0044");
        baybayinMap.put("DE", "\u0044"+"\u0065");
        baybayinMap.put("DI", "\u0044"+"\u0069");
        baybayinMap.put("DO", "\u0044"+"\u006F");
        baybayinMap.put("DU", "\u0044"+"\u0075");

        baybayinMap.put("GA", "\u0047");
        baybayinMap.put("GE", "\u0047"+"\u0065");
        baybayinMap.put("GI", "\u0047"+"\u0069");
        baybayinMap.put("GO", "\u0047"+"\u006F");
        baybayinMap.put("GU", "\u0047"+"\u0075");

        baybayinMap.put("HA", "\u0048");
        baybayinMap.put("HE", "\u0048"+"\u0065");
        baybayinMap.put("HI", "\u0048"+"\u0069");
        baybayinMap.put("HO", "\u0048"+"\u006F");
        baybayinMap.put("HU", "\u0048"+"\u0075");

        baybayinMap.put("LA", "\u004C");
        baybayinMap.put("LE", "\u004C"+"\u0065");
        baybayinMap.put("LI", "\u004C"+"\u0069");
        baybayinMap.put("LO", "\u004C"+"\u006F");
        baybayinMap.put("LU", "\u004C"+"\u0075");

        baybayinMap.put("MA", "\u006D");
        baybayinMap.put("ME", "\u006D"+"\u0065");
        baybayinMap.put("MI", "\u006D"+"\u0069");
        baybayinMap.put("MO", "\u006D"+"\u006F");
        baybayinMap.put("MU", "\u006D"+"\u0075");

        baybayinMap.put("NA", "\u006E");
        baybayinMap.put("NE", "\u006E"+"\u0065");
        baybayinMap.put("NI", "\u006E"+"\u0069");
        baybayinMap.put("NO", "\u006E"+"\u006F");
        baybayinMap.put("NU", "\u006E"+"\u0075");

        baybayinMap.put("PA", "\u0050");
        baybayinMap.put("PE", "\u0050"+"\u0065");
        baybayinMap.put("PI", "\u0050"+"\u0069");
        baybayinMap.put("PO", "\u0050"+"\u006F");
        baybayinMap.put("PU", "\u0050"+"\u0075");

        baybayinMap.put("RA", "\u0052");
        baybayinMap.put("RE", "\u0052"+"\u0065");
        baybayinMap.put("RI", "\u0052"+"\u0069");
        baybayinMap.put("RO", "\u0052"+"\u006F");
        baybayinMap.put("RU", "\u0052"+"\u0075");

        baybayinMap.put("SA", "\u0053");
        baybayinMap.put("SE", "\u0053"+"\u0065");
        baybayinMap.put("SI", "\u0053"+"\u0069");
        baybayinMap.put("SO", "\u0053"+"\u006F");
        baybayinMap.put("SU", "\u0053"+"\u0075");

        baybayinMap.put("TA", "\u0054");
        baybayinMap.put("TE", "\u0054"+"\u0065");
        baybayinMap.put("TI", "\u0054"+"\u0069");
        baybayinMap.put("TO", "\u0054"+"\u006F");
        baybayinMap.put("TU", "\u0054"+"\u0075");

        baybayinMap.put("WA", "\u0057");
        baybayinMap.put("WE", "\u0057"+"\u0065");
        baybayinMap.put("WI", "\u0057"+"\u0069");
        baybayinMap.put("WO", "\u0057"+"\u006F");
        baybayinMap.put("WU", "\u0057"+"\u0075");

        baybayinMap.put("YA", "\u0059");
        baybayinMap.put("YE", "\u0059"+"\u0065");
        baybayinMap.put("YI", "\u0059"+"\u0069");
        baybayinMap.put("YO", "\u0059"+"\u006F");
        baybayinMap.put("YU", "\u0059"+"\u0075");

        baybayinMap.put("NG", "\u004E"+"\u002B");
        baybayinMap.put("B", "\u0042"+"\u002B");
        baybayinMap.put("K", "\u004B"+"\u002B");
        baybayinMap.put("D", "\u0044"+"\u002B");
        baybayinMap.put("G", "\u0047"+"\u002B");
        baybayinMap.put("H", "\u0048"+"\u002B");
        baybayinMap.put("L", "\u004C"+"\u002B");
        baybayinMap.put("M", "\u004D"+"\u002B");
        baybayinMap.put("N", "\u006E"+"\u002B");
        baybayinMap.put("P", "\u0050"+"\u002B");
        baybayinMap.put("R", "\u0052"+"\u002B");
        baybayinMap.put("S", "\u0053"+"\u002B");
        baybayinMap.put("T", "\u0054"+"\u002B");
        baybayinMap.put("W", "\u0057"+"\u002B");
        baybayinMap.put("Y", "\u0059"+"\u002B");

        baybayinMap.put("A", "\u0041");
        baybayinMap.put("E", "\u0045");
        baybayinMap.put("I", "\u0049");
        baybayinMap.put("O", "\u004F");
        baybayinMap.put("U", "\u0055");

        baybayinMap.put(",", "\u002C");
        baybayinMap.put(".", "\u002E");
    }

    public static String mapToRobotika(String toConvert) {
        StringBuilder mappedText = new StringBuilder();
        String upperCaseInput = toConvert.toUpperCase();

        for (int i = 0; i < upperCaseInput.length(); i++) {
            // Handle the next three characters
            if (i + 2 < upperCaseInput.length()) {
                String threeChars = upperCaseInput.substring(i, i + 3);
                if (baybayinMap.containsKey(threeChars)) {
                    mappedText.append(baybayinMap.get(threeChars));
                    i += 2;
                    continue;
                }
            }

            // Handle the next two characters
            if (i + 1 < upperCaseInput.length()) {
                String twoChars = upperCaseInput.substring(i, i + 2);
                if (baybayinMap.containsKey(twoChars)) {
                    mappedText.append(baybayinMap.get(twoChars));
                    i += 1;
                    continue;
                }
            }

            // Handle the current character
            String oneChar = String.valueOf(upperCaseInput.charAt(i));
            if (baybayinMap.containsKey(oneChar)) {
                mappedText.append(baybayinMap.get(oneChar));
            } else {
                mappedText.append(oneChar);
            }
        }

        return mappedText.toString();
    }

}
