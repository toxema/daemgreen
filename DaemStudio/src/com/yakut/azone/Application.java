package com.yakut.azone;

import com.yakut.azone.lisans.Lisanser;
import com.yakut.azone.util.Utils;

/**
 *
 * @author yakut
 */
public class Application {

    public static void main(String[] args) {
        Utils.startNimbus();
        Lisanser.lisansiKontrolEt(args);
        MainStudio.main(args);
    }
}
