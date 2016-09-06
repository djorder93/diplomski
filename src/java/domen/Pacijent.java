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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
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
@Table(name = "pacijent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pacijent.list", query = "SELECT p FROM Pacijent p where p.sifradoktora= :doktor"),
    @NamedQuery(name = "Pacijent.findAll", query = "SELECT p FROM Pacijent p"),
    @NamedQuery(name = "Pacijent.findBySifrapacijenta", query = "SELECT p FROM Pacijent p WHERE p.sifrapacijenta = :sifrapacijenta"),
    @NamedQuery(name = "Pacijent.findByJmbg", query = "SELECT p FROM Pacijent p WHERE p.jmbg = :jmbg"),
    @NamedQuery(name = "Pacijent.findByIme", query = "SELECT p FROM Pacijent p WHERE p.ime = :ime"),
    @NamedQuery(name = "Pacijent.findByPrezime", query = "SELECT p FROM Pacijent p WHERE p.prezime = :prezime"),
    @NamedQuery(name = "Pacijent.findByTelefon", query = "SELECT p FROM Pacijent p WHERE p.telefon = :telefon"),
    @NamedQuery(name = "Pacijent.findByDatumrodjenja", query = "SELECT p FROM Pacijent p WHERE p.datumrodjenja = :datumrodjenja")})
public class Pacijent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sifrapacijenta")
    private Integer sifrapacijenta;
    @Size(max = 13)
    @Column(name = "jmbg")
    private String jmbg;
    @Size(max = 20)
    @Column(name = "ime")
    private String ime;
    @Size(max = 20)
    @Column(name = "prezime")
    private String prezime;
    @Size(max = 10)
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "datumrodjenja")
    @Temporal(TemporalType.DATE)
    private Date datumrodjenja;
    @JoinColumn(name = "sifradoktora", referencedColumnName = "sifradoktora")
    @ManyToOne(fetch = FetchType.EAGER)
    private Doktor sifradoktora;
    @JoinColumn(name = "siframedsestre", referencedColumnName = "sifraMedSestre")
    @ManyToOne(fetch = FetchType.EAGER)
    private Medsestra siframedsestre;
    @OneToMany(mappedBy = "pacijent", fetch = FetchType.EAGER)
    private List<Intervencija> intervencijaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacijent1", fetch = FetchType.EAGER)
    private List<Zub> zubList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacijent1", fetch = FetchType.EAGER)
    private List<Termin> terminList;

    public Pacijent() {
    }

    public Pacijent(Integer sifrapacijenta) {
        this.sifrapacijenta = sifrapacijenta;
    }

    public Integer getSifrapacijenta() {
        return sifrapacijenta;
    }

    public void setSifrapacijenta(Integer sifrapacijenta) {
        this.sifrapacijenta = sifrapacijenta;
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Date getDatumrodjenja() {
        return datumrodjenja;
    }

    public void setDatumrodjenja(Date datumrodjenja) {
        this.datumrodjenja = datumrodjenja;
    }

    public Doktor getSifradoktora() {
        return sifradoktora;
    }

    public void setSifradoktora(Doktor sifradoktora) {
        this.sifradoktora = sifradoktora;
    }

    public Medsestra getSiframedsestre() {
        return siframedsestre;
    }

    public void setSiframedsestre(Medsestra siframedsestre) {
        this.siframedsestre = siframedsestre;
    }

    @XmlTransient
    public List<Intervencija> getIntervencijaList() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();

        intervencijaList = em.createNamedQuery("Intervencija.listForPacijent", Intervencija.class).setParameter("pacijent", this).getResultList();
        em.close();
        emf.close();
        return intervencijaList;
    }

    public void setIntervencijaList(List<Intervencija> intervencijaList) {
        this.intervencijaList = intervencijaList;
    }

    @XmlTransient
    public List<Zub> getZubList() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();

        zubList = em.createNamedQuery("Zub.findByPacijent", Zub.class).setParameter("pacijent", this).getResultList();
        em.close();
        emf.close();
        return zubList;
    }

    public void setZubList(List<Zub> zubList) {
        this.zubList = zubList;
    }

    @XmlTransient
    public List<Termin> getTerminList() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();

        terminList = em.createNamedQuery("Termin.findByPacijent", Termin.class).setParameter("pacijent", this).getResultList();
        em.close();
        emf.close();
        return terminList;
    }

    public void setTerminList(List<Termin> terminList) {
        this.terminList = terminList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifrapacijenta != null ? sifrapacijenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pacijent)) {
            return false;
        }
        Pacijent other = (Pacijent) object;
        if ((this.sifrapacijenta == null && other.sifrapacijenta != null) || (this.sifrapacijenta != null && !this.sifrapacijenta.equals(other.sifrapacijenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

}
