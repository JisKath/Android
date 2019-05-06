package com.toolbartabs.toolbartabs.Activities;

import java.util.ArrayList;
import java.util.List;

public class Comandos {

    public static List<String> Listrall(String buffer) {
        List<String> names;

        String Temp = "";
        int tempindexespacio, tempindexcoma, tempindexrenglon, bufferLenght, index = 0;

        bufferLenght = buffer.length();
        names = new ArrayList<String>();

        while (index < bufferLenght) {
            tempindexespacio = buffer.indexOf(" ", index);
            tempindexcoma = buffer.indexOf(", ", index);
            tempindexrenglon = buffer.indexOf(":", index);

            if (tempindexcoma <= 0 || tempindexespacio <= 0 || names.size()>=13) {
                index = bufferLenght;
            } else {

                Temp = buffer.substring(tempindexespacio, tempindexcoma);
                names.add(Temp);
                index = tempindexrenglon + 1;
            }
        }

        return names;
    }




}
