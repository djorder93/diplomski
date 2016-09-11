package domen.jaf;

import domen.Medsestra;
import domen.Pacijent;
import domen.Zub;
import domen.ZubPK;
import domen.jaf.util.JsfUtil;
import domen.jaf.util.JsfUtil.PersistAction;
import domen.beans.PacijentFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

@Named("pacijentController")
@SessionScoped
public class PacijentController implements Serializable {

    @EJB
    private domen.beans.PacijentFacade ejbFacade;
    private List<Pacijent> items = null;
    private Pacijent selected;
    private Zub zub;
    private List<Zub> lz;

    public PacijentController() {
        zub = new Zub();
        lz = new ArrayList<>();
    }

    public List<Zub> getLz() {
        return lz;
    }

    public void setLz(List<Zub> lz) {
        this.lz = lz;
    }

    public Zub getZub() {
        return zub;
    }

    public void setZub(Zub zub) {
        this.zub = zub;
    }

    public Pacijent getSelected() {
        return selected;
    }

    public void setSelected(Pacijent selected) {
        this.selected = selected;
        if (selected.getZubList() != null) {
            lz = selected.getZubList();
        }
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PacijentFacade getFacade() {
        return ejbFacade;
    }

    public void insertZub() {
        ZubPK zpk = new ZubPK(zub.getZubPK().getOznaka(), selected.getSifrapacijenta());
        zub.setZubPK(zpk);
        zub.setPacijent1(selected);
        lz.add(zub);
        zub = new Zub();
    }

    public void onRowEdit(RowEditEvent e) {
        Zub z = (Zub) e.getObject();
        lz.add(z);
    }

    public void onRowCancel(RowEditEvent e) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Zub) e.getObject()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void datFilter(SelectEvent event) {
        RequestContext.getCurrentInstance().execute("PF('pacijenti').filter()");
    }

    public Pacijent prepareCreate() {
        selected = new Pacijent();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PacijentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PacijentUpdated"));

    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PacijentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Pacijent> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            selected.setZubList(lz);
            selected.setSiframedsestre((Medsestra) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logged"));
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Pacijent getPacijent(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Pacijent> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Pacijent> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Pacijent.class)
    public static class PacijentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PacijentController controller = (PacijentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pacijentController");
            return controller.getPacijent(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pacijent) {
                Pacijent o = (Pacijent) object;
                return getStringKey(o.getSifrapacijenta());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pacijent.class.getName()});
                return null;
            }
        }

    }

}
