/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domen.Medsestra;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import kontroler.Kontroler;
import org.primefaces.context.RequestContext;

/**
 *
 * @author radovanovic
 */
@ManagedBean(name = "mbLogin")
@SessionScoped
public class MbLogin implements Serializable {

    /**
     * Creates a new instance of MbLogin
     */
    Medsestra ms;

    public MbLogin() {
        ms = new Medsestra();
    }

    public Medsestra getMs() {
        return ms;
    }

    public void setMs(Medsestra ms) {
        this.ms = ms;
    }

    public String login() {

        try {
            System.out.println("Korinik: korisnicko ime:" + ms.getKorisnickoIme() + ", kosrisnicka sifra:" + ms.getLozinka());
            ms = Kontroler.vratiInstancu().login(ms);
            RequestContext.getCurrentInstance().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Korisnik je uspesno autentifikovan!!!", "Detalji"));
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            session.setAttribute("logged", ms);
            return "/stranice/template.xhtml";

        } catch (Exception ex) {
            Logger.getLogger(MbLogin.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "GRESKA:" + ex.getMessage(), "Detalji"));
        }

        return null;
    }

    public String logout() {
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,"/login.xhtml");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("logged", ms);
        session.invalidate();
        return "/login.xhtml";
    }
}
