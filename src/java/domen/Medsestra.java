/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "medsestra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name="Medsestra.login", query = "SELECT m FROM Medsestra m WHERE m.lozinka = :lozinka and  m.korisnickoIme = :korisnickoIme" ),
    @NamedQuery(name = "Medsestra.findAll", query = "SELECT m FROM Medsestra m"),
    @NamedQuery(name = "Medsestra.findBySifraMedSestre", query = "SELECT m FROM Medsestra m WHERE m.sifraMedSestre = :sifraMedSestre"),
    @NamedQuery(name = "Medsestra.findByJmbg", query = "SELECT m FROM Medsestra m WHERE m.jmbg = :jmbg"),
    @NamedQuery(name = "Medsestra.findByIme", query = "SELECT m FROM Medsestra m WHERE m.ime = :ime"),
    @NamedQuery(name = "Medsestra.findByPrezime", query = "SELECT m FROM Medsestra m WHERE m.prezime = :prezime"),
    @NamedQuery(name = "Medsestra.findByKorisnickoIme", query = "SELECT m FROM Medsestra m WHERE m.korisnickoIme = :korisnickoIme"),
    @NamedQuery(name = "Medsestra.findByLozinka", query = "SELECT m FROM Medsestra m WHERE m.lozinka = :lozinka")})
public class Medsestra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sifraMedSestre")
    private Integer sifraMedSestre;
    @Size(max = 13)
    @Column(name = "jmbg")
    private String jmbg;
    @Size(max = 20)
    @Column(name = "ime")
    private String ime;
    @Size(max = 20)
    @Column(name = "prezime")
    private String prezime;
    @Size(max = 50)
    @Column(name = "korisnickoIme")
    private String korisnickoIme;
    @Size(max = 50)
    @Column(name = "lozinka")
    private String lozinka;
    @OneToMany(mappedBy = "siframedsestre", fetch = FetchType.EAGER)
    private List<Pacijent> pacijentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medsestra", fetch = FetchType.EAGER)
    private List<Termin> terminList;

    public Medsestra() {
    }

    public Medsestra(Integer sifraMedSestre) {
        this.sifraMedSestre = sifraMedSestre;
    }

    public Integer getSifraMedSestre() {
        return sifraMedSestre;
    }

    public void setSifraMedSestre(Integer sifraMedSestre) {
        this.sifraMedSestre = sifraMedSestre;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    @XmlTransient
    public List<Pacijent> getPacijentList() {
        return pacijentList;
    }

    public void setPacijentList(List<Pacijent> pacijentList) {
        this.pacijentList = pacijentList;
    }

    @XmlTransient
    public List<Termin> getTerminList() {
        return terminList;
    }

    public void setTerminList(List<Termin> terminList) {
        this.terminList = terminList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifraMedSestre != null ? sifraMedSestre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medsestra)) {
            return false;
        }
        Medsestra other = (Medsestra) object;
        if ((this.sifraMedSestre == null && other.sifraMedSestre != null) || (this.sifraMedSestre != null && !this.sifraMedSestre.equals(other.sifraMedSestre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ime+" "+prezime;
    }
    
}
