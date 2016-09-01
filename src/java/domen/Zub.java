/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author radovanovic
 */
@Entity
@Table(name = "zub")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zub.findAll", query = "SELECT z FROM Zub z"),
    @NamedQuery(name = "Zub.findBySifraZuba", query = "SELECT z FROM Zub z WHERE z.zubPK.sifraZuba = :sifraZuba"),
    @NamedQuery(name = "Zub.findByPacijent", query = "SELECT z FROM Zub z WHERE z.zubPK.pacijent = :pacijent"),
    @NamedQuery(name = "Zub.findByOznaka", query = "SELECT z FROM Zub z WHERE z.oznaka = :oznaka"),
    @NamedQuery(name = "Zub.findByStanjeZuba", query = "SELECT z FROM Zub z WHERE z.stanjeZuba = :stanjeZuba")})
public class Zub implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ZubPK zubPK;
    @Size(max = 15)
    @Column(name = "oznaka")
    private String oznaka;
    @Size(max = 100)
    @Column(name = "stanjeZuba")
    private String stanjeZuba;
    @OneToMany(mappedBy = "zub", fetch = FetchType.LAZY)
    private List<Intervencija> intervencijaList;
    @JoinColumn(name = "pacijent", referencedColumnName = "sifrapacijenta", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pacijent pacijent1;

    public Zub() {
    }

    public Zub(ZubPK zubPK) {
        this.zubPK = zubPK;
    }

    public Zub(int sifraZuba, int pacijent) {
        this.zubPK = new ZubPK(sifraZuba, pacijent);
    }

    public ZubPK getZubPK() {
        return zubPK;
    }

    public void setZubPK(ZubPK zubPK) {
        this.zubPK = zubPK;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public String getStanjeZuba() {
        return stanjeZuba;
    }

    public void setStanjeZuba(String stanjeZuba) {
        this.stanjeZuba = stanjeZuba;
    }

    @XmlTransient
    public List<Intervencija> getIntervencijaList() {
        return intervencijaList;
    }

    public void setIntervencijaList(List<Intervencija> intervencijaList) {
        this.intervencijaList = intervencijaList;
    }

    public Pacijent getPacijent1() {
        return pacijent1;
    }

    public void setPacijent1(Pacijent pacijent1) {
        this.pacijent1 = pacijent1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zubPK != null ? zubPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zub)) {
            return false;
        }
        Zub other = (Zub) object;
        if ((this.zubPK == null && other.zubPK != null) || (this.zubPK != null && !this.zubPK.equals(other.zubPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return oznaka;
    }
    
}
