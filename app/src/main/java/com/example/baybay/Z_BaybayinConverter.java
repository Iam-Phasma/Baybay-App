package com.example.baybay;

public class Z_BaybayinConverter {

    static String mapToBaybayinB20Plus(String toConvert) {
        StringBuilder mappedText = new StringBuilder();

        toConvert = toConvert.replaceAll("(?i)NGA", "ᜅ");
        toConvert = toConvert.replaceAll("(?i)NGE", "ᜅ\u1715");
        toConvert = toConvert.replaceAll("(?i)NGI", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGO", "ᜅ\u1716");
        toConvert = toConvert.replaceAll("(?i)NGU", "ᜅᜓ");

        toConvert = toConvert.replaceAll("(?i)BA", "ᜊ");
        toConvert = toConvert.replaceAll("(?i)BE", "ᜊ\u1715");
        toConvert = toConvert.replaceAll("(?i)BI", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BO", "ᜊ\u1716");
        toConvert = toConvert.replaceAll("(?i)BU", "ᜊᜓ");

        toConvert = toConvert.replaceAll("(?i)KA", "ᜃ");
        toConvert = toConvert.replaceAll("(?i)KE", "ᜃ\u1715");
        toConvert = toConvert.replaceAll("(?i)KI", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KO", "ᜃ\u1716");
        toConvert = toConvert.replaceAll("(?i)KU", "ᜃᜓ");

        toConvert = toConvert.replaceAll("(?i)DA", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)DE", "ᜇ\u1715");
        toConvert = toConvert.replaceAll("(?i)DI", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DO", "ᜇ\u1716");
        toConvert = toConvert.replaceAll("(?i)DU", "ᜇᜓ");

        toConvert = toConvert.replaceAll("(?i)GA", "ᜄ");
        toConvert = toConvert.replaceAll("(?i)GE", "ᜄ\u1715");
        toConvert = toConvert.replaceAll("(?i)GI", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GO", "ᜄ\u1716");
        toConvert = toConvert.replaceAll("(?i)GU", "ᜄᜓ");

        toConvert = toConvert.replaceAll("(?i)HA", "ᜑ");
        toConvert = toConvert.replaceAll("(?i)HE", "ᜑ\u1715");
        toConvert = toConvert.replaceAll("(?i)HI", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HO", "ᜑ\u1716");
        toConvert = toConvert.replaceAll("(?i)HU", "ᜑᜓ");

        toConvert = toConvert.replaceAll("(?i)LA", "ᜎ");
        toConvert = toConvert.replaceAll("(?i)LE", "ᜎ\u1715");
        toConvert = toConvert.replaceAll("(?i)LI", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LO", "ᜎ\u1716");
        toConvert = toConvert.replaceAll("(?i)LU", "ᜎᜓ");

        toConvert = toConvert.replaceAll("(?i)MA", "ᜋ");
        toConvert = toConvert.replaceAll("(?i)ME", "ᜋ\u1715");
        toConvert = toConvert.replaceAll("(?i)MI", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MO", "ᜋ\u1716");
        toConvert = toConvert.replaceAll("(?i)MU", "ᜋᜓ");

        toConvert = toConvert.replaceAll("(?i)NA", "ᜈ");
        toConvert = toConvert.replaceAll("(?i)NE", "ᜈ\u1715");
        toConvert = toConvert.replaceAll("(?i)NI", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NO", "ᜈ\u1716");
        toConvert = toConvert.replaceAll("(?i)NU", "ᜈᜓ");

        toConvert = toConvert.replaceAll("(?i)PA", "ᜉ");
        toConvert = toConvert.replaceAll("(?i)PE", "ᜉ\u1715");
        toConvert = toConvert.replaceAll("(?i)PI", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PO", "ᜉ\u1716");
        toConvert = toConvert.replaceAll("(?i)PU", "ᜉᜓ");

        toConvert = toConvert.replaceAll("(?i)RA", "\ue006");
        toConvert = toConvert.replaceAll("(?i)RE", "\ue006"+"\u1715");
        toConvert = toConvert.replaceAll("(?i)RI", "\ue006ᜒ"); //with dot
        toConvert = toConvert.replaceAll("(?i)RO", "\ue006"+"\u1716");
        toConvert = toConvert.replaceAll("(?i)RU", "\ue006"+"\u1713");

        toConvert = toConvert.replaceAll("(?i)SA", "ᜐ");
        toConvert = toConvert.replaceAll("(?i)SE", "ᜐ\u1715");
        toConvert = toConvert.replaceAll("(?i)SI", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SO", "ᜐ\u1716");
        toConvert = toConvert.replaceAll("(?i)SU", "ᜐᜓ");

        toConvert = toConvert.replaceAll("(?i)TA", "ᜆ");
        toConvert = toConvert.replaceAll("(?i)TE", "ᜆ\u1715");
        toConvert = toConvert.replaceAll("(?i)TI", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TO", "ᜆ\u1716");
        toConvert = toConvert.replaceAll("(?i)TU", "ᜆᜓ");

        toConvert = toConvert.replaceAll("(?i)WA", "ᜏ");
        toConvert = toConvert.replaceAll("(?i)WE", "ᜏ\u1715");
        toConvert = toConvert.replaceAll("(?i)WI", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WO", "ᜏ\u1716");
        toConvert = toConvert.replaceAll("(?i)WU", "ᜏᜓ");

        toConvert = toConvert.replaceAll("(?i)YA", "ᜌ");
        toConvert = toConvert.replaceAll("(?i)YE", "ᜌ\u1715");
        toConvert = toConvert.replaceAll("(?i)YI", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YO", "ᜌ\u1716");
        toConvert = toConvert.replaceAll("(?i)YU", "ᜌᜓ");

        toConvert = toConvert.replaceAll("(?i)NG", "ᜅ᜔");
        toConvert = toConvert.replaceAll("(?i)B", "ᜊ᜔");
        toConvert = toConvert.replaceAll("(?i)K", "ᜃ᜔");
        toConvert = toConvert.replaceAll("(?i)D", "ᜇ᜔");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ᜔");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ᜔");
        toConvert = toConvert.replaceAll("(?i)H", "ᜑ᜔");
        toConvert = toConvert.replaceAll("(?i)L", "ᜎ᜔");
        toConvert = toConvert.replaceAll("(?i)M", "ᜋ᜔");
        toConvert = toConvert.replaceAll("(?i)N", "ᜈ᜔");
        toConvert = toConvert.replaceAll("(?i)P", "ᜉ᜔");
        toConvert = toConvert.replaceAll("(?i)R", "\ue006 ᜔");
        toConvert = toConvert.replaceAll("(?i)S", "ᜐ᜔");
        toConvert = toConvert.replaceAll("(?i)T", "ᜆ᜔");
        toConvert = toConvert.replaceAll("(?i)W", "ᜏ᜔");
        toConvert = toConvert.replaceAll("(?i)Y", "ᜌ᜔");

        for (int i = 0; i < toConvert.length(); i++) {
            char character = toConvert.toUpperCase().charAt(i);
            switch (character) {
                case 'A':
                    mappedText.append("\u1700");
                    break;
                case 'E':
                    mappedText.append("\u1717");
                    break;
                case 'I':
                    mappedText.append("\u1701");
                    break;
                case 'O':
                    mappedText.append("\u1718");
                    break;
                case 'U':
                    mappedText.append("\u1702");
                    break;
                case '.':
                    mappedText.append("᜶");
                    break;
                case ',':
                    mappedText.append("᜵");
                    break;

                default:
                    mappedText.append(character);
                    break;
            }
        }
        return mappedText.toString().trim();
    }

    static String mapToBaybayinB18(String toConvert) {
        StringBuilder mappedText = new StringBuilder();

        toConvert = toConvert.replaceAll("(?i)NGA", "ᜅ");
        toConvert = toConvert.replaceAll("(?i)NGE", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGI", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGO", "ᜅᜓ");
        toConvert = toConvert.replaceAll("(?i)NGU", "ᜅᜓ");

        toConvert = toConvert.replaceAll("(?i)BA", "ᜊ");
        toConvert = toConvert.replaceAll("(?i)BE", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BI", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BO", "ᜊᜓ");
        toConvert = toConvert.replaceAll("(?i)BU", "ᜊᜓ");

        toConvert = toConvert.replaceAll("(?i)KA", "ᜃ");
        toConvert = toConvert.replaceAll("(?i)KE", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KI", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KO", "ᜃᜓ");
        toConvert = toConvert.replaceAll("(?i)KU", "ᜃᜓ");

        toConvert = toConvert.replaceAll("(?i)DA", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)DE", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DI", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DO", "ᜇᜓ");
        toConvert = toConvert.replaceAll("(?i)DU", "ᜇᜓ");

        toConvert = toConvert.replaceAll("(?i)GA", "ᜄ");
        toConvert = toConvert.replaceAll("(?i)GE", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GI", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GO", "ᜄᜓ");
        toConvert = toConvert.replaceAll("(?i)GU", "ᜄᜓ");

        toConvert = toConvert.replaceAll("(?i)HA", "ᜑ");
        toConvert = toConvert.replaceAll("(?i)HE", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HI", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HO", "ᜑᜓ");
        toConvert = toConvert.replaceAll("(?i)HU", "ᜑᜓ");

        toConvert = toConvert.replaceAll("(?i)LA", "ᜎ");
        toConvert = toConvert.replaceAll("(?i)LE", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LI", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LO", "ᜎᜓ");
        toConvert = toConvert.replaceAll("(?i)LU", "ᜎᜓ");

        toConvert = toConvert.replaceAll("(?i)MA", "ᜋ");
        toConvert = toConvert.replaceAll("(?i)ME", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MI", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MO", "ᜋᜓ");
        toConvert = toConvert.replaceAll("(?i)MU", "ᜋᜓ");

        toConvert = toConvert.replaceAll("(?i)NA", "ᜈ");
        toConvert = toConvert.replaceAll("(?i)NE", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NI", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NO", "ᜈᜓ");
        toConvert = toConvert.replaceAll("(?i)NU", "ᜈᜓ");

        toConvert = toConvert.replaceAll("(?i)PA", "ᜉ");
        toConvert = toConvert.replaceAll("(?i)PE", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PI", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PO", "ᜉᜓ");
        toConvert = toConvert.replaceAll("(?i)PU", "ᜉᜓ");

        toConvert = toConvert.replaceAll("(?i)RA", "\u170D");
        toConvert = toConvert.replaceAll("(?i)RE", "\u170Dᜒ");
        toConvert = toConvert.replaceAll("(?i)RI", "\u170Dᜒ");
        toConvert = toConvert.replaceAll("(?i)RO", "\u170Dᜓ");
        toConvert = toConvert.replaceAll("(?i)RU", "\u170Dᜓ");

        toConvert = toConvert.replaceAll("(?i)SA", "ᜐ");
        toConvert = toConvert.replaceAll("(?i)SE", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SI", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SO", "ᜐᜓ");
        toConvert = toConvert.replaceAll("(?i)SU", "ᜐᜓ");

        toConvert = toConvert.replaceAll("(?i)TA", "ᜆ");
        toConvert = toConvert.replaceAll("(?i)TE", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TI", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TO", "ᜆᜓ");
        toConvert = toConvert.replaceAll("(?i)TU", "ᜆᜓ");

        toConvert = toConvert.replaceAll("(?i)WA", "ᜏ");
        toConvert = toConvert.replaceAll("(?i)WE", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WI", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WO", "ᜏᜓ");
        toConvert = toConvert.replaceAll("(?i)WU", "ᜏᜓ");

        toConvert = toConvert.replaceAll("(?i)YA", "ᜌ");
        toConvert = toConvert.replaceAll("(?i)YE", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YI", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YO", "ᜌᜓ");
        toConvert = toConvert.replaceAll("(?i)YU", "ᜌᜓ");

        toConvert = toConvert.replaceAll("(?i)NG", "ᜅ᜔");
        toConvert = toConvert.replaceAll("(?i)B", "ᜊ᜔");
        toConvert = toConvert.replaceAll("(?i)K", "ᜃ᜔");
        toConvert = toConvert.replaceAll("(?i)D", "ᜇ᜔");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ᜔");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ᜔");
        toConvert = toConvert.replaceAll("(?i)H", "ᜑ᜔");
        toConvert = toConvert.replaceAll("(?i)L", "ᜎ᜔");
        toConvert = toConvert.replaceAll("(?i)M", "ᜋ᜔");
        toConvert = toConvert.replaceAll("(?i)N", "ᜈ᜔");
        toConvert = toConvert.replaceAll("(?i)P", "ᜉ᜔");
        toConvert = toConvert.replaceAll("(?i)R", "\u170D ᜔");
        toConvert = toConvert.replaceAll("(?i)S", "ᜐ᜔");
        toConvert = toConvert.replaceAll("(?i)T", "ᜆ᜔");
        toConvert = toConvert.replaceAll("(?i)W", "ᜏ᜔");
        toConvert = toConvert.replaceAll("(?i)Y", "ᜌ᜔");

        for (int i = 0; i < toConvert.length(); i++) {
            char character = toConvert.toUpperCase().charAt(i);
            switch (character) {
                case 'A':
                    mappedText.append("ᜀ");
                    break;
                case 'E':
                case 'I':
                    mappedText.append("ᜁ");
                    break;
                case 'O':
                case 'U':
                    mappedText.append("ᜂ");
                    break;
                case '.':
                    mappedText.append("᜶");
                    break;
                case ',':
                    mappedText.append("᜵");
                    break;

                default:
                    mappedText.append(character);
                    break;
            }
        }

        return mappedText.toString().trim();
    }

    static String mapToBaybayinB17Plus(String toConvert) {
        StringBuilder mappedText = new StringBuilder();

        toConvert = toConvert.replaceAll("(?i)NGA", "ᜅ");
        toConvert = toConvert.replaceAll("(?i)NGE", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGI", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGO", "ᜅᜓ");
        toConvert = toConvert.replaceAll("(?i)NGU", "ᜅᜓ");

        toConvert = toConvert.replaceAll("(?i)BA", "ᜊ");
        toConvert = toConvert.replaceAll("(?i)BE", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BI", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BO", "ᜊᜓ");
        toConvert = toConvert.replaceAll("(?i)BU", "ᜊᜓ");

        toConvert = toConvert.replaceAll("(?i)KA", "ᜃ");
        toConvert = toConvert.replaceAll("(?i)KE", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KI", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KO", "ᜃᜓ");
        toConvert = toConvert.replaceAll("(?i)KU", "ᜃᜓ");

        toConvert = toConvert.replaceAll("(?i)DA", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)DE", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DI", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DO", "ᜇᜓ");
        toConvert = toConvert.replaceAll("(?i)DU", "ᜇᜓ");

        toConvert = toConvert.replaceAll("(?i)GA", "ᜄ");
        toConvert = toConvert.replaceAll("(?i)GE", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GI", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GO", "ᜄᜓ");
        toConvert = toConvert.replaceAll("(?i)GU", "ᜄᜓ");

        toConvert = toConvert.replaceAll("(?i)HA", "ᜑ");
        toConvert = toConvert.replaceAll("(?i)HE", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HI", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HO", "ᜑᜓ");
        toConvert = toConvert.replaceAll("(?i)HU", "ᜑᜓ");

        toConvert = toConvert.replaceAll("(?i)LA", "ᜎ");
        toConvert = toConvert.replaceAll("(?i)LE", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LI", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LO", "ᜎᜓ");
        toConvert = toConvert.replaceAll("(?i)LU", "ᜎᜓ");

        toConvert = toConvert.replaceAll("(?i)MA", "ᜋ");
        toConvert = toConvert.replaceAll("(?i)ME", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MI", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MO", "ᜋᜓ");
        toConvert = toConvert.replaceAll("(?i)MU", "ᜋᜓ");

        toConvert = toConvert.replaceAll("(?i)NA", "ᜈ");
        toConvert = toConvert.replaceAll("(?i)NE", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NI", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NO", "ᜈᜓ");
        toConvert = toConvert.replaceAll("(?i)NU", "ᜈᜓ");

        toConvert = toConvert.replaceAll("(?i)PA", "ᜉ");
        toConvert = toConvert.replaceAll("(?i)PE", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PI", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PO", "ᜉᜓ");
        toConvert = toConvert.replaceAll("(?i)PU", "ᜉᜓ");

        toConvert = toConvert.replaceAll("(?i)RA", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)RE", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)RI", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)RO", "ᜇᜓ");
        toConvert = toConvert.replaceAll("(?i)RU", "ᜇᜓ");

        toConvert = toConvert.replaceAll("(?i)SA", "ᜐ");
        toConvert = toConvert.replaceAll("(?i)SE", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SI", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SO", "ᜐᜓ");
        toConvert = toConvert.replaceAll("(?i)SU", "ᜐᜓ");

        toConvert = toConvert.replaceAll("(?i)TA", "ᜆ");
        toConvert = toConvert.replaceAll("(?i)TE", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TI", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TO", "ᜆᜓ");
        toConvert = toConvert.replaceAll("(?i)TU", "ᜆᜓ");

        toConvert = toConvert.replaceAll("(?i)WA", "ᜏ");
        toConvert = toConvert.replaceAll("(?i)WE", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WI", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WO", "ᜏᜓ");
        toConvert = toConvert.replaceAll("(?i)WU", "ᜏᜓ");

        toConvert = toConvert.replaceAll("(?i)YA", "ᜌ");
        toConvert = toConvert.replaceAll("(?i)YE", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YI", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YO", "ᜌᜓ");
        toConvert = toConvert.replaceAll("(?i)YU", "ᜌᜓ");

        toConvert = toConvert.replaceAll("(?i)NG", "ᜅ᜔");
        toConvert = toConvert.replaceAll("(?i)B", "ᜊ᜔");
        toConvert = toConvert.replaceAll("(?i)K", "ᜃ᜔");
        toConvert = toConvert.replaceAll("(?i)D", "ᜇ᜔");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ᜔");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ᜔");
        toConvert = toConvert.replaceAll("(?i)H", "ᜑ᜔");
        toConvert = toConvert.replaceAll("(?i)L", "ᜎ᜔");
        toConvert = toConvert.replaceAll("(?i)M", "ᜋ᜔");
        toConvert = toConvert.replaceAll("(?i)N", "ᜈ᜔");
        toConvert = toConvert.replaceAll("(?i)P", "ᜉ᜔");
        toConvert = toConvert.replaceAll("(?i)R", "ᜇ᜔");
        toConvert = toConvert.replaceAll("(?i)S", "ᜐ᜔");
        toConvert = toConvert.replaceAll("(?i)T", "ᜆ᜔");
        toConvert = toConvert.replaceAll("(?i)W", "ᜏ᜔");
        toConvert = toConvert.replaceAll("(?i)Y", "ᜌ᜔");

        for (int i = 0; i < toConvert.length(); i++) {
            char character = toConvert.toUpperCase().charAt(i);
            switch (character) {
                case 'A':
                    mappedText.append("ᜀ");
                    break;
                case 'E':
                case 'I':
                    mappedText.append("ᜁ");
                    break;
                case 'O':
                case 'U':
                    mappedText.append("ᜂ");
                    break;
                case '.':
                    mappedText.append("᜶");
                    break;
                case ',':
                    mappedText.append("᜵");
                    break;

                default:
                    mappedText.append(character);
                    break;
            }
        }

        return mappedText.toString().trim();
    }

    static String mapToBaybayinB17(String toConvert) {
        StringBuilder mappedText = new StringBuilder();

        toConvert = toConvert.replaceAll("(?i)NGA", "ᜅ");
        toConvert = toConvert.replaceAll("(?i)NGE", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGI", "ᜅᜒ");
        toConvert = toConvert.replaceAll("(?i)NGO", "ᜅᜓ");
        toConvert = toConvert.replaceAll("(?i)NGU", "ᜅᜓ");

        toConvert = toConvert.replaceAll("(?i)BA", "ᜊ");
        toConvert = toConvert.replaceAll("(?i)BE", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BI", "ᜊᜒ");
        toConvert = toConvert.replaceAll("(?i)BO", "ᜊᜓ");
        toConvert = toConvert.replaceAll("(?i)BU", "ᜊᜓ");

        toConvert = toConvert.replaceAll("(?i)KA", "ᜃ");
        toConvert = toConvert.replaceAll("(?i)KE", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KI", "ᜃᜒ");
        toConvert = toConvert.replaceAll("(?i)KO", "ᜃᜓ");
        toConvert = toConvert.replaceAll("(?i)KU", "ᜃᜓ");

        toConvert = toConvert.replaceAll("(?i)DA", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)DE", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DI", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)DO", "ᜇᜓ");
        toConvert = toConvert.replaceAll("(?i)DU", "ᜇᜓ");

        toConvert = toConvert.replaceAll("(?i)GA", "ᜄ");
        toConvert = toConvert.replaceAll("(?i)GE", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GI", "ᜄᜒ");
        toConvert = toConvert.replaceAll("(?i)GO", "ᜄᜓ");
        toConvert = toConvert.replaceAll("(?i)GU", "ᜄᜓ");

        toConvert = toConvert.replaceAll("(?i)HA", "ᜑ");
        toConvert = toConvert.replaceAll("(?i)HE", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HI", "ᜑᜒ");
        toConvert = toConvert.replaceAll("(?i)HO", "ᜑᜓ");
        toConvert = toConvert.replaceAll("(?i)HU", "ᜑᜓ");

        toConvert = toConvert.replaceAll("(?i)LA", "ᜎ");
        toConvert = toConvert.replaceAll("(?i)LE", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LI", "ᜎᜒ");
        toConvert = toConvert.replaceAll("(?i)LO", "ᜎᜓ");
        toConvert = toConvert.replaceAll("(?i)LU", "ᜎᜓ");

        toConvert = toConvert.replaceAll("(?i)MA", "ᜋ");
        toConvert = toConvert.replaceAll("(?i)ME", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MI", "ᜋᜒ");
        toConvert = toConvert.replaceAll("(?i)MO", "ᜋᜓ");
        toConvert = toConvert.replaceAll("(?i)MU", "ᜋᜓ");

        toConvert = toConvert.replaceAll("(?i)NA", "ᜈ");
        toConvert = toConvert.replaceAll("(?i)NE", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NI", "ᜈᜒ");
        toConvert = toConvert.replaceAll("(?i)NO", "ᜈᜓ");
        toConvert = toConvert.replaceAll("(?i)NU", "ᜈᜓ");

        toConvert = toConvert.replaceAll("(?i)PA", "ᜉ");
        toConvert = toConvert.replaceAll("(?i)PE", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PI", "ᜉᜒ");
        toConvert = toConvert.replaceAll("(?i)PO", "ᜉᜓ");
        toConvert = toConvert.replaceAll("(?i)PU", "ᜉᜓ");

        toConvert = toConvert.replaceAll("(?i)RA", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)RE", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)RI", "ᜇᜒ");
        toConvert = toConvert.replaceAll("(?i)RO", "ᜇᜓ");
        toConvert = toConvert.replaceAll("(?i)RU", "ᜇᜓ");

        toConvert = toConvert.replaceAll("(?i)SA", "ᜐ");
        toConvert = toConvert.replaceAll("(?i)SE", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SI", "ᜐᜒ");
        toConvert = toConvert.replaceAll("(?i)SO", "ᜐᜓ");
        toConvert = toConvert.replaceAll("(?i)SU", "ᜐᜓ");

        toConvert = toConvert.replaceAll("(?i)TA", "ᜆ");
        toConvert = toConvert.replaceAll("(?i)TE", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TI", "ᜆᜒ");
        toConvert = toConvert.replaceAll("(?i)TO", "ᜆᜓ");
        toConvert = toConvert.replaceAll("(?i)TU", "ᜆᜓ");

        toConvert = toConvert.replaceAll("(?i)WA", "ᜏ");
        toConvert = toConvert.replaceAll("(?i)WE", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WI", "ᜏᜒ");
        toConvert = toConvert.replaceAll("(?i)WO", "ᜏᜓ");
        toConvert = toConvert.replaceAll("(?i)WU", "ᜏᜓ");

        toConvert = toConvert.replaceAll("(?i)YA", "ᜌ");
        toConvert = toConvert.replaceAll("(?i)YE", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YI", "ᜌᜒ");
        toConvert = toConvert.replaceAll("(?i)YO", "ᜌᜓ");
        toConvert = toConvert.replaceAll("(?i)YU", "ᜌᜓ");

        toConvert = toConvert.replaceAll("(?i)NG", "ᜅ");
        toConvert = toConvert.replaceAll("(?i)B", "ᜊ");
        toConvert = toConvert.replaceAll("(?i)K", "ᜃ");
        toConvert = toConvert.replaceAll("(?i)D", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ");
        toConvert = toConvert.replaceAll("(?i)G", "ᜄ");
        toConvert = toConvert.replaceAll("(?i)H", "ᜑ");
        toConvert = toConvert.replaceAll("(?i)L", "ᜎ");
        toConvert = toConvert.replaceAll("(?i)M", "ᜋ");
        toConvert = toConvert.replaceAll("(?i)N", "ᜈ");
        toConvert = toConvert.replaceAll("(?i)P", "ᜉ");
        toConvert = toConvert.replaceAll("(?i)R", "ᜇ");
        toConvert = toConvert.replaceAll("(?i)S", "ᜐ");
        toConvert = toConvert.replaceAll("(?i)T", "ᜆ");
        toConvert = toConvert.replaceAll("(?i)W", "ᜏ");
        toConvert = toConvert.replaceAll("(?i)Y", "ᜌ");

        for (int i = 0; i < toConvert.length(); i++) {
            char character = toConvert.toUpperCase().charAt(i);
            switch (character) {
                case 'A':
                    mappedText.append("ᜀ");
                    break;
                case 'E':
                case 'I':
                    mappedText.append("ᜁ");
                    break;
                case 'O':
                case 'U':
                    mappedText.append("ᜂ");
                    break;
                case '.':
                    mappedText.append("᜶");
                    break;
                case ',':
                    mappedText.append("᜵");
                    break;

                default:
                    mappedText.append(character);
                    break;
            }
        }

        return mappedText.toString().trim();
    }




    static String mapToRobotika(String toConvert) {
        StringBuilder mappedText = new StringBuilder();

        toConvert = toConvert.replaceAll("(?i)NGA", "\u004E");
        toConvert = toConvert.replaceAll("(?i)NGE", "\u004E"+"\u0065");
        toConvert = toConvert.replaceAll("(?i)NGI", "\u004E"+"\u0065");
        toConvert = toConvert.replaceAll("(?i)NGO", "\u004E"+"\u006F");
        toConvert = toConvert.replaceAll("(?i)NGU", "\u004E"+"\u006F");

        toConvert = toConvert.replaceAll("(?i)BA", "");
        toConvert = toConvert.replaceAll("(?i)BE", "");
        toConvert = toConvert.replaceAll("(?i)BI", "");
        toConvert = toConvert.replaceAll("(?i)BO", "");
        toConvert = toConvert.replaceAll("(?i)BU", "");

        toConvert = toConvert.replaceAll("(?i)KA", "");
        toConvert = toConvert.replaceAll("(?i)KE", "");
        toConvert = toConvert.replaceAll("(?i)KI", "");
        toConvert = toConvert.replaceAll("(?i)KO", "");
        toConvert = toConvert.replaceAll("(?i)KU", "");

        toConvert = toConvert.replaceAll("(?i)DA", "");
        toConvert = toConvert.replaceAll("(?i)DE", "");
        toConvert = toConvert.replaceAll("(?i)DI", "");
        toConvert = toConvert.replaceAll("(?i)DO", "");
        toConvert = toConvert.replaceAll("(?i)DU", "");

        toConvert = toConvert.replaceAll("(?i)GA", "");
        toConvert = toConvert.replaceAll("(?i)GE", "");
        toConvert = toConvert.replaceAll("(?i)GI", "");
        toConvert = toConvert.replaceAll("(?i)GO", "");
        toConvert = toConvert.replaceAll("(?i)GU", "");

        toConvert = toConvert.replaceAll("(?i)HA", "");
        toConvert = toConvert.replaceAll("(?i)HE", "");
        toConvert = toConvert.replaceAll("(?i)HI", "");
        toConvert = toConvert.replaceAll("(?i)HO", "");
        toConvert = toConvert.replaceAll("(?i)HU", "");

        toConvert = toConvert.replaceAll("(?i)LA", "");
        toConvert = toConvert.replaceAll("(?i)LE", "");
        toConvert = toConvert.replaceAll("(?i)LI", "");
        toConvert = toConvert.replaceAll("(?i)LO", "");
        toConvert = toConvert.replaceAll("(?i)LU", "");

        toConvert = toConvert.replaceAll("(?i)MA", "ᜋ");
        toConvert = toConvert.replaceAll("(?i)ME", "");
        toConvert = toConvert.replaceAll("(?i)MI", "");
        toConvert = toConvert.replaceAll("(?i)MO", "");
        toConvert = toConvert.replaceAll("(?i)MU", "");

        toConvert = toConvert.replaceAll("(?i)NA", "");
        toConvert = toConvert.replaceAll("(?i)NE", "");
        toConvert = toConvert.replaceAll("(?i)NI", "");
        toConvert = toConvert.replaceAll("(?i)NO", "");
        toConvert = toConvert.replaceAll("(?i)NU", "");

        toConvert = toConvert.replaceAll("(?i)PA", "");
        toConvert = toConvert.replaceAll("(?i)PE", "");
        toConvert = toConvert.replaceAll("(?i)PI", "");
        toConvert = toConvert.replaceAll("(?i)PO", "");
        toConvert = toConvert.replaceAll("(?i)PU", "");

        toConvert = toConvert.replaceAll("(?i)RA", "");
        toConvert = toConvert.replaceAll("(?i)RE", "ᜒ");
        toConvert = toConvert.replaceAll("(?i)RI", "ᜒ");
        toConvert = toConvert.replaceAll("(?i)RO", "");
        toConvert = toConvert.replaceAll("(?i)RU", "");

        toConvert = toConvert.replaceAll("(?i)SA", "ᜐ");
        toConvert = toConvert.replaceAll("(?i)SE", "");
        toConvert = toConvert.replaceAll("(?i)SI", "");
        toConvert = toConvert.replaceAll("(?i)SO", "");
        toConvert = toConvert.replaceAll("(?i)SU", "");

        toConvert = toConvert.replaceAll("(?i)TA", "");
        toConvert = toConvert.replaceAll("(?i)TE", "");
        toConvert = toConvert.replaceAll("(?i)TI", "");
        toConvert = toConvert.replaceAll("(?i)TO", "");
        toConvert = toConvert.replaceAll("(?i)TU", "");

        toConvert = toConvert.replaceAll("(?i)WA", "");
        toConvert = toConvert.replaceAll("(?i)WE", "");
        toConvert = toConvert.replaceAll("(?i)WI", "");
        toConvert = toConvert.replaceAll("(?i)WO", "");
        toConvert = toConvert.replaceAll("(?i)WU", "");

        toConvert = toConvert.replaceAll("(?i)YA", "");
        toConvert = toConvert.replaceAll("(?i)YE", "");
        toConvert = toConvert.replaceAll("(?i)YI", "");
        toConvert = toConvert.replaceAll("(?i)YO", "");
        toConvert = toConvert.replaceAll("(?i)YU", "");

        toConvert = toConvert.replaceAll("(?i)NG", "");
        toConvert = toConvert.replaceAll("(?i)B", "");
        toConvert = toConvert.replaceAll("(?i)K", "");
        toConvert = toConvert.replaceAll("(?i)D", "");
        toConvert = toConvert.replaceAll("(?i)G", "");
        toConvert = toConvert.replaceAll("(?i)G", "");
        toConvert = toConvert.replaceAll("(?i)H", "");
        toConvert = toConvert.replaceAll("(?i)L", "");
        toConvert = toConvert.replaceAll("(?i)M", "");
        toConvert = toConvert.replaceAll("(?i)N", "");
        toConvert = toConvert.replaceAll("(?i)P", "");
        toConvert = toConvert.replaceAll("(?i)R", "");
        toConvert = toConvert.replaceAll("(?i)S", "");
        toConvert = toConvert.replaceAll("(?i)T", "");
        toConvert = toConvert.replaceAll("(?i)W", "");
        toConvert = toConvert.replaceAll("(?i)Y", "");

        for (int i = 0; i < toConvert.length(); i++) {
            char character = toConvert.toUpperCase().charAt(i);
            switch (character) {
                case 'A':
                    mappedText.append("");
                    break;
                case 'E':
                case 'I':
                    mappedText.append("");
                    break;
                case 'O':
                case 'U':
                    mappedText.append("");
                    break;
                case '.':
                    mappedText.append("");
                    break;
                case ',':
                    mappedText.append("");
                    break;

                default:
                    mappedText.append(character);
                    break;
            }
        }

        return mappedText.toString().trim();
    }
}
