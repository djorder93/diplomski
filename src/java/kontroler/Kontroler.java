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

    public Medsestra login(Medsestra ms)  {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diplomskiPU");
        EntityManager em = emf.createEntityManager();
        Medsestra meds;
             meds = em.createNamedQuery("Medsestra.login", Medsestra.class).setParameter("korisnickoIme", ms.getKorisnickoIme()).setParameter("lozinka", ms.getLozinka()).getSingleResult();
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
