/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author radovanovic
 */
@Embeddable
public class StavkaintervencijePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "rbStavke")
    private int rbStavke;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sifraintervencije")
    private int sifraintervencije;

    public StavkaintervencijePK() {
    }

    public StavkaintervencijePK(int rbStavke, int sifraintervencije) {
        this.rbStavke = rbStavke;
        this.sifraintervencije = sifraintervencije;
    }

    public int getRbStavke() {
        return rbStavke;
    }

    public void setRbStavke(int rbStavke) {
        this.rbStavke = rbStavke;
    }

    public int getSifraintervencije() {
        return sifraintervencije;
    }

    public void setSifraintervencije(int sifraintervencije) {
        this.sifraintervencije = sifraintervencije;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rbStavke;
        hash += (int) sifraintervencije;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaintervencijePK)) {
            return false;
        }
        StavkaintervencijePK other = (StavkaintervencijePK) object;
        if (this.rbStavke != other.rbStavke) {
            return false;
        }
        if (this.sifraintervencije != other.sifraintervencije) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.StavkaintervencijePK[ rbStavke=" + rbStavke + ", sifraintervencije=" + sifraintervencije + " ]";
    }
    
}
