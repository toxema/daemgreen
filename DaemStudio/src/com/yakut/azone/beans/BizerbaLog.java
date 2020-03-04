package com.yakut.azone.beans;

import com.yakut.azone.util.DateUtil;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author yakut
 */
@Entity
public class BizerbaLog implements Serializable {

    @Id
    @GenericGenerator(name = "generator2", strategy = "increment")
    @GeneratedValue(generator = "generator2")
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    Date tarih;
    @Column(name = "MAKINA_ADI", length = 20)
    private String makinaAdi;
    @Column(length = 20, name = "URUN_KODU")
    private String urunKodu;
    @Column(length = 40, name = "URUN_ADI")
    private String urunAdi;
    @Column(name = "GERCEK_AGIRLIK")
    private int gercekAgirlik = 0;
    @Column(name = "BARKOD_AGIRLIK")
    private int barkodAgirlik = 0;
    @Column(name = "FARK_AGIRLIK")
    private int agirlikFark = 0;
    @Column(name = "YUZDELIK")
    private float yuzdelik = 0;

    @Transient
    String rowData;
    @Transient
    DecimalFormat format2 = new DecimalFormat("#.00");//.format(1.199);

    public String getRowData() {
        return rowData;
    }

    public void setRowData(String rowData) {
        this.rowData = rowData;
    }

    public BizerbaLog() {
        tarih = new Date();
    }

    public String[] getData() {
        return new String[]{
            makinaAdi,
            DateUtil.fullFormatDate(tarih),
            urunKodu,
            urunAdi,
            gercekAgirlik + " gr",
            barkodAgirlik + " gr",
            agirlikFark + " gr",
            format2.format(yuzdelik) + " gr"
        };
    }

    public void print(String[] d) {
        for (int k = 0; k < d.length; k++) {
            System.out.println("" + d[k]);
        }
    }

    static SimpleDateFormat format = new SimpleDateFormat("ddMMyy HHmm");

    public static Date parseDate(String data) {
        Date d = null;
        try {
            d = format.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(BizerbaLog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    public void parse(String data) {
        //     2|190717|3449|^10;TZ. TB. P�L�� KAL�A ��� 750 GR (M)|KG|-3|750|KG|-3|761|1442
        //           ^tarih                                                                                                                       ^saat
        String log = data;
        try {
            setRowData(data);
            data = data.trim().replace('|', ',');
            String row[] = data.trim().split(",");

            //08
//                        String tarihData = row[1];
//                        
//                        if(tarihData.length()==5){
//                                tarihData="0"+tarihData;
//                        }
//                        
//                        String saatData = row[10];
//                        
//                        if(saatData.length()==3)
//                                saatData="0"+saatData;
//                        
//                        tarihData = tarihData + " " + saatData;
//                         setTarih(parseDate(tarihData));
            setTarih(new Date());
            urunKodu = row[2];
            urunAdi = row[3];
            int pos = urunAdi.indexOf(';');
            urunAdi = urunAdi.substring(pos + 1);

            barkodAgirlik = Integer.parseInt(row[6]);
            gercekAgirlik = Integer.parseInt(row[9]);
            agirlikFark = gercekAgirlik - barkodAgirlik;
            yuzdelik = ((float) agirlikFark / (float) barkodAgirlik) * 100.0f;

        } catch (Exception ex) {
            System.out.println("hatalı data=" + log);
            ex.printStackTrace();
        }
    }

    public float getYuzdelik() {
        return yuzdelik;
    }

    public void setYuzdelik(float yuzdelik) {
        this.yuzdelik = yuzdelik;
    }

    public String getMakinaAdi() {
        return makinaAdi;
    }

    public void setMakinaAdi(String makinaAdi) {
        this.makinaAdi = makinaAdi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrunKodu() {
        return urunKodu;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public void setUrunKodu(String urunKodu) {
        this.urunKodu = urunKodu;
    }

    public float getGercekAgirlik() {
        return gercekAgirlik;
    }

    public int getAgirlikFark() {
        return agirlikFark;
    }

    public void setAgirlikFark(int agirlikFark) {
        this.agirlikFark = agirlikFark;
    }

    public int getBarkodAgirlik() {
        return barkodAgirlik;
    }

    public void setBarkodAgirlik(int barkodAgirlik) {
        this.barkodAgirlik = barkodAgirlik;
    }

    public void setGercekAgirlik(int gercekAgirlik) {
        this.gercekAgirlik = gercekAgirlik;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    @Override
    public String toString() {
        return "BizerbaLog{" + "id=" + id + ", tarih=" + tarih + ", makinaAdi=" + makinaAdi + ", urunKodu=" + urunKodu + ", urunAdi=" + urunAdi + ", gercekAgirlik=" + gercekAgirlik + ", barkodAgirlik=" + barkodAgirlik + ", agirlikFark=" + agirlikFark + ", yuzdelik=" + yuzdelik + '}';
    }
}
