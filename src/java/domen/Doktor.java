/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "doktor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doktor.findAll", query = "SELECT d FROM Doktor d"),
    @NamedQuery(name = "Doktor.findBySifradoktora", query = "SELECT d FROM Doktor d WHERE d.sifradoktora = :sifradoktora"),
    @NamedQuery(name = "Doktor.findByJmbg", query = "SELECT d FROM Doktor d WHERE d.jmbg = :jmbg"),
    @NamedQuery(name = "Doktor.findByIme", query = "SELECT d FROM Doktor d WHERE d.ime = :ime"),
    @NamedQuery(name = "Doktor.findByPrezime", query = "SELECT d FROM Doktor d WHERE d.prezime = :prezime")})
public class Doktor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sifradoktora")
    private Integer sifradoktora;
    @Size(max = 13)
    @Column(name = "jmbg")
    private String jmbg;
    @Size(max = 20)
    @Column(name = "ime")
    private String ime;
    @Size(max = 20)
    @Column(name = "prezime")
    private String prezime;
    @OneToMany(mappedBy = "sifradoktora", fetch = FetchType.LAZY)
    private List<Pacijent> pacijentList;
    @OneToMany(mappedBy = "doktor", fetch = FetchType.LAZY)
    private List<Intervencija> intervencijaList;

    public Doktor() {
    }

    public Doktor(Integer sifradoktora) {
        this.sifradoktora = sifradoktora;
    }

    public Integer getSifradoktora() {
        return sifradoktora;
    }

    public void setSifradoktora(Integer sifradoktora) {
        this.sifradoktora = sifradoktora;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @XmlTransient
    public List<Pacijent> getPacijentList() {
        return pacijentList;
    }

    public void setPacijentList(List<Pacijent> pacijentList) {
        this.pacijentList = pacijentList;
    }

    @XmlTransient
    public List<Intervencija> getIntervencijaList() {
        return intervencijaList;
    }

    public void setIntervencijaList(List<Intervencija> intervencijaList) {
        this.intervencijaList = intervencijaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifradoktora != null ? sifradoktora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doktor)) {
            return false;
        }
        Doktor other = (Doktor) object;
        if ((this.sifradoktora == null && other.sifradoktora != null) || (this.sifradoktora != null && !this.sifradoktora.equals(other.sifradoktora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ime+" "+prezime;
    }
    
}
