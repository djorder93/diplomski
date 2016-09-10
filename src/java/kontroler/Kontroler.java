/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Doktor;
import domen.Intervencija;
import domen.Medsestra;
import domen.Pacijent;
import domen.Termin;
import domen.Zub;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author radovanovic
 */
public class Kontroler {

    private static Kontroler instanca;

    private Kontroler() {

    }

    public static Kontroler vratiInstancu() {
        if (instanca == null) {
            instanca = new Kontroler();
        }
        return instanca;
    }

    public Medsestra login(Medsestra ms) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();
        Medsestra meds;
        meds = em.createNamedQuery("Medsestra.login", Medsestra.class).setParameter("korisnickoIme", ms.getKorisnickoIme()).setParameter("lozinka", ms.getLozinka()).getSingleResult();
        em.close();
        emf.close();

        return meds;
    }

    public List<Pacijent> getPacijentList(Doktor d) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();
        List<Pacijent> pacijentList = em.createNamedQuery("Pacijent.list", Pacijent.class).setParameter("doktor", d).getResultList();
        em.close();
        emf.close();
        return pacijentList;
    }

    public List<Intervencija> getIntervencijaList(Doktor d) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();
        List<Intervencija> intervencijaList = em.createNamedQuery("Intervencija.listForDoktor", Intervencija.class).setParameter("doktor", d).getResultList();
        em.close();
        emf.close();
        return intervencijaList;
    }

    public List<Zub> getZubList(Pacijent p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();
        List<Zub> zubList = em.createNamedQuery("Zub.findByPacijent", Zub.class).setParameter("pacijent", p).getResultList();
        em.close();
        emf.close();
        return zubList;
    }

    public List<Termin> getTerminList(Pacijent p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();
        List<Termin> terminList = em.createNamedQuery("Termin.findByPacijent", Termin.class).setParameter("pacijent", p).getResultList();
        em.close();
        emf.close();
        return terminList;
    }

    public List<Intervencija> getIntervencijaList(Pacijent p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();

        List<Intervencija> intervencijaList = em.createNamedQuery("Intervencija.listForPacijent", Intervencija.class).setParameter("pacijent", p).getResultList();
        em.close();
        emf.close();
        return intervencijaList;
    }

}
