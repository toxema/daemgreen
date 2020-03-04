package app.codesys;

/**
 *
 * @author yakut
 */
class GVL {

    public static GVL getGVL() {
        if (gvl == null) {
            gvl = new GVL();
        }
        return gvl;
    }
    public static GVL gvl = null;
    public boolean grupStateChanged = false;
    public Grup[] gruplar = new Array(16).fill(new Grup());
    public Kapi[] kapilar = new Array(16).fill(new Kapi());
    public Urun[] urunler = new Array(16).fill(new Urun());
    public int[] secilenKapilar = new Array(16).fill(-1);
    public int gateCount = 16;
    int activeGrupNo=1;

}
