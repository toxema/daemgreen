package app.codesys;

/**
 *
 * @author yakut
 */
public class Array {

    int size = 1;

    public Array(int size) {
        this.size = size;
    }

    public int[] fill(int defaultValue) {
        int[] array = new int[this.size];
        for (int k = 0; k < this.size; k++) {
            array[k] = defaultValue;
        }
        return array;
    }

    public Grup[] fill(Grup defaultValue) {
        Grup[] array = new Grup[this.size];
        for (int k = 0; k < this.size; k++) {
            array[k] = defaultValue;
        }
        return array;
    }

    public Kapi[] fill(Kapi defaultValue) {
        Kapi[] array = new Kapi[this.size];
        for (int k = 0; k < this.size; k++) {
            array[k] = defaultValue;
        }
        return array;
    }

    public Urun[] fill(Urun defaultValue) {
        Urun[] array = new Urun[this.size];
        for (int k = 0; k < this.size; k++) {
            array[k] = defaultValue;
        }
        return array;
    }
}
