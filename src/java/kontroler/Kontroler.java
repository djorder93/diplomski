/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Doktor;
import domen.Medsestra;
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

    public Medsestra login(Medsestra ms) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();

        Medsestra meds = em.createNamedQuery("Medsestra.login", Medsestra.class).setParameter("korisnickoIme", ms.getKorisnickoIme()).setParameter("lozinka", ms.getLozinka()).getSingleResult();

        if ((meds == null) || (!meds.getLozinka().equals(ms.getLozinka()))) {
            throw new Exception("Korisnik nije autentifikovan. Pogresno korisnicko ime i/ili sifra");
        }

        em.close();
        emf.close();

        return meds;
    }

    public void dodajDoktora(Doktor novi) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Doktor doktor = em.find(Doktor.class, novi.getSifradoktora());

        em.merge(novi);
        em.getTransaction().commit();
        em.close();
        emf.close();

    }
}
