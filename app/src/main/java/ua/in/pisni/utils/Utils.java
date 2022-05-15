package ua.in.pisni.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String getFirstTwoRows(String song) {
        String[] lines = song.split("\\n");
        List<String> rows = new ArrayList<>();
        for(int i = 0;i < lines.length;i++) {
            String line = lines[i];
            if("".equals(line)) {
                continue;
            }
            boolean latinLetter = false;
            for(int j = 0;j < line.length();j++) {
                char c = line.charAt(j);
                if((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                    latinLetter = true;
                }
            }
            if(latinLetter) continue;

            rows.add(line);
            if(rows.size() == 2) {
                break;
            }
        }

        StringBuilder rowsBuilder = new StringBuilder();
        for(int i = 0;i < rows.size();i++) {
            if(i != 0) {
                rowsBuilder.append("\n");
            }
            rowsBuilder.append(rows.get(i));
        }
        return rowsBuilder.toString();
    }

}
