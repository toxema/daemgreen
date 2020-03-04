package com.yakut.azone.util;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author yakut
 */
public class ImageLoader {

    ImageIcon image;
    ImageIcon imageDefault;

    public static ImageIcon getImage(String path) {
        File imageFile = new File(path);

        if (imageFile.exists()) {
            return new javax.swing.ImageIcon(path);
        } else {
            return new javax.swing.ImageIcon("/home/yakut/Masaüstü/ARAC1.PNG");
        }
    }
}
