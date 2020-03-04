

package com.yakut.azone.beans;

 
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author yakut
 */
@Entity
@Table(name="OGS_GRUPHAK")
public class GrupHak implements Serializable {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", nullable = false)
    private int id;
    @ManyToOne
    private Grup grup;
    @ManyToOne
    private Bolge bolge;
    private String gunler = "0 1 2 3 4 5 6";
    private boolean mukerrerSerbest = false;
    private boolean daireyeGoreKontrolet = false;
    private int daireBasiKapasite = 0;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date basla;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date bitis;

    public Bolge getBolge() {
        return bolge;
    }

    public void setBolge(Bolge bolge) {
        this.bolge = bolge;
    }

    public int getDaireBasiKapasite() {
        return daireBasiKapasite;
    }

    public void setDaireBasiKapasite(int daireBasiKapasite) {
        this.daireBasiKapasite = daireBasiKapasite;
    }

    public boolean isDaireyeGoreKontrolet() {
        return daireyeGoreKontrolet;
    }

    public void setDaireyeGoreKontrolet(boolean daireyeGoreKontrolet) {
        this.daireyeGoreKontrolet = daireyeGoreKontrolet;
    }

    public Grup getGrup() {
        return grup;
    }

    public void setGrup(Grup grup) {
        this.grup = grup;
    }

    public String getGunler() {
        return gunler;
    }

    public void setGunler(String gunler) {
        this.gunler = gunler;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMukerrerSerbest() {
        return mukerrerSerbest;
    }

    public void setMukerrerSerbest(boolean mukerrerSerbest) {
        this.mukerrerSerbest = mukerrerSerbest;
    }

    public Date getBasla() {
        return basla;
    }

    public void setBasla(Date basla) {
        this.basla = basla;
    }

    public Date getBitis() {
        return bitis;
    }

    public void setBitis(Date bitis) {
        this.bitis = bitis;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GrupHak other = (GrupHak) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        return hash;
    }
    
    
}
