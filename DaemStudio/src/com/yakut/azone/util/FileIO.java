package com.yakut.azone.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class FileIO {

    static Logger logger = Logger.getLogger(FileIO.class);

    public static List<String> getLines(String file) {
        return getLines(new File(file));
    }

    public static List<String> getLines(File file) {
        List<String> list = new ArrayList<String>();
        InputStream i;
        try {
            i = new FileInputStream(file);
            InputStreamReader in = new InputStreamReader(i, "utf-8");
            Scanner s = new Scanner(in);
            int k = 0;
            while (s.hasNextLine()) {
                String line = (s.nextLine());
                list.add(line);
            }
            s.close();
        } catch (Exception ex) {
            logger.error("dosya açılamadı ", ex);
        }
        return list;
    }
}
