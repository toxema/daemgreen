
package app.codesys;

/**
 *
 * @author yakut
 */
public class Grup {

    public int no;
    int[] kapilar=new Array(16).fill(-1);
    int kapiCount;

    Grup() {
        this(0);
    }

    Grup(int no) {
        this.no = no;
    }

}
