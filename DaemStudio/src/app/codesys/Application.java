package app.codesys;

import com.yakut.azone.util.Utils;

public class Application implements Runnable {

    GVL gvl = null;
    boolean isFreeWhelling = false;
    int cycleTime = 2;

    public static void main(String[] args) {
        new Application().begin();
    }

    private void init() {
        gvl = GVL.getGVL();

        //isFreeWhelling = Resource.get("is_freewillig").toBool();
        //cycleTime = Resource.get("cycle_time").toInt(); 
        //gateCount = Resource.get("gate_count").toInt(); 
        for (int k = 0; k < gvl.gateCount; k++) {
            gvl.gruplar[k] = new Grup(k);
            gvl.kapilar[k] = new Kapi(k);
            gvl.urunler[k] = new Urun(k);
        }
    }

    public void begin() {
        init();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {

            if (isFreeWhelling) {
                loop();
            } else {
                Utils.sleep(cycleTime);
            }

        }
    }

    public void loop() {

        if (gvl.grupStateChanged) {
            gvl.grupStateChanged = false;
            for (int k = 0; k < gvl.gateCount; k++) {
                Grup g = gvl.gruplar[k];
                if(g.no==gvl.activeGrupNo){
                    int kapiCount=0;
                    for(int m=0;m<16;m++){
                        int kapiNo=gvl.secilenKapilar[m];
                        if(m>-1){
                            g.kapilar[kapiCount]=kapiNo;
                            kapiCount++;
                        }
                    }
                    g.kapiCount=kapiCount;
                    break;
                }
            }
        }
    }
}
