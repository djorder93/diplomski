/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author radovanovic
 */
@Entity
@Table(name = "intervencija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intervencija.listForPacijent", query = "SELECT i FROM Intervencija i where i.pacijent= :pacijent"),
    @NamedQuery(name = "Intervencija.listForDoktor", query = "SELECT i FROM Intervencija i where i.doktor= :doktor"),
    @NamedQuery(name = "Intervencija.findAll", query = "SELECT i FROM Intervencija i"),
    @NamedQuery(name = "Intervencija.findById", query = "SELECT i FROM Intervencija i WHERE i.id = :id"),
    @NamedQuery(name = "Intervencija.findByNaziv", query = "SELECT i FROM Intervencija i WHERE i.naziv = :naziv"),
    @NamedQuery(name = "Intervencija.findByDatum", query = "SELECT i FROM Intervencija i WHERE i.datum = :datum")})
public class Intervencija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @JoinColumns({
        @JoinColumn(name = "zub", referencedColumnName = "sifraZuba"),
        @JoinColumn(name = "pac", referencedColumnName = "pacijent")})
    @ManyToOne(fetch = FetchType.EAGER)
    private Zub zub;
    @JoinColumn(name = "doktor", referencedColumnName = "sifradoktora")
    @ManyToOne(fetch = FetchType.EAGER)
    private Doktor doktor;
    @JoinColumn(name = "pacijent", referencedColumnName = "sifrapacijenta")
    @ManyToOne(fetch = FetchType.EAGER)
    private Pacijent pacijent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intervencija", fetch = FetchType.EAGER)
    private List<Stavkaintervencije> stavkaintervencijeList;

    public Intervencija() {
    }

    public Intervencija(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Zub getZub() {
        
        return zub;
    }

    public void setZub(Zub zub) {
        this.zub = zub;
    }

    public Doktor getDoktor() {
        return doktor;
    }

    public void setDoktor(Doktor doktor) {
        this.doktor = doktor;
    }

    public Pacijent getPacijent() {
        return pacijent;
    }

    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    @XmlTransient
    public List<Stavkaintervencije> getStavkaintervencijeList() {
        return stavkaintervencijeList;
    }

    public void setStavkaintervencijeList(List<Stavkaintervencije> stavkaintervencijeList) {
        this.stavkaintervencijeList = stavkaintervencijeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intervencija)) {
            return false;
        }
        Intervencija other = (Intervencija) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Intervencija[ id=" + id + " ]";
    }
    
}
