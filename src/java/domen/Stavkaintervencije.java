/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author radovanovic
 */
@Entity
@Table(name = "stavkaintervencije")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavkaintervencije.findAll", query = "SELECT s FROM Stavkaintervencije s"),
    @NamedQuery(name = "Stavkaintervencije.findByRbStavke", query = "SELECT s FROM Stavkaintervencije s WHERE s.stavkaintervencijePK.rbStavke = :rbStavke"),
    @NamedQuery(name = "Stavkaintervencije.findBySifraintervencije", query = "SELECT s FROM Stavkaintervencije s WHERE s.stavkaintervencijePK.sifraintervencije = :sifraintervencije"),
    @NamedQuery(name = "Stavkaintervencije.findByNaziv", query = "SELECT s FROM Stavkaintervencije s WHERE s.naziv = :naziv"),
    @NamedQuery(name = "Stavkaintervencije.findByKolicina", query = "SELECT s FROM Stavkaintervencije s WHERE s.kolicina = :kolicina")})
public class Stavkaintervencije implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StavkaintervencijePK stavkaintervencijePK;
    @Size(max = 50)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "kolicina")
    private Integer kolicina;
    @JoinColumn(name = "sifraintervencije", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Intervencija intervencija;

    public Stavkaintervencije() {
    }

    public Stavkaintervencije(StavkaintervencijePK stavkaintervencijePK) {
        this.stavkaintervencijePK = stavkaintervencijePK;
    }

    public Stavkaintervencije(int rbStavke, int sifraintervencije) {
        this.stavkaintervencijePK = new StavkaintervencijePK(rbStavke, sifraintervencije);
    }

    public StavkaintervencijePK getStavkaintervencijePK() {
        return stavkaintervencijePK;
    }

    public void setStavkaintervencijePK(StavkaintervencijePK stavkaintervencijePK) {
        this.stavkaintervencijePK = stavkaintervencijePK;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Intervencija getIntervencija() {
        return intervencija;
    }

    public void setIntervencija(Intervencija intervencija) {
        this.intervencija = intervencija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaintervencijePK != null ? stavkaintervencijePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavkaintervencije)) {
            return false;
        }
        Stavkaintervencije other = (Stavkaintervencije) object;
        if ((this.stavkaintervencijePK == null && other.stavkaintervencijePK != null) || (this.stavkaintervencijePK != null && !this.stavkaintervencijePK.equals(other.stavkaintervencijePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Stavkaintervencije[ stavkaintervencijePK=" + stavkaintervencijePK + " ]";
    }
    
}
