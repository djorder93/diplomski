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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author radovanovic
 */
@Embeddable
public class ZubPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "oznaka")
    private int oznaka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pacijent")
    private int pacijent;

    public ZubPK() {
    }

    public ZubPK(int sifraZuba, int pacijent) {
        this.oznaka = sifraZuba;
        this.pacijent = pacijent;
    }

    public int getOznaka() {
        return oznaka;
    }

    public void setOznaka(int sifraZuba) {
        this.oznaka = sifraZuba;
    }

    public int getPacijent() {
        return pacijent;
    }

    public void setPacijent(int pacijent) {
        this.pacijent = pacijent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) oznaka;
        hash += (int) pacijent;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZubPK)) {
            return false;
        }
        ZubPK other = (ZubPK) object;
        if (this.oznaka != other.oznaka) {
            return false;
        }
        if (this.pacijent != other.pacijent) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ""+oznaka;
    }

}
