/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.azone.test;

import com.yakut.azone.beans.Grup;
import com.yakut.azone.beans.Personel;
import com.yakut.azone.controller.GrupController;
import com.yakut.azone.controller.PersonelController;
import com.yakut.azone.util.DateUtil;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author yakut
 */
public class Main {

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static void main(String[] args) throws Exception {

        System.out.println("perf " + round(32.569547f, 2));
    }
}
