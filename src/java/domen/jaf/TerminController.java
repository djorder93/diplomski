package domen.jaf;

import domen.Medsestra;
import domen.Termin;
import domen.jaf.util.JsfUtil;
import domen.jaf.util.JsfUtil.PersistAction;
import domen.beans.TerminFacade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("terminController")
@SessionScoped
public class TerminController implements Serializable {

    @EJB
    private domen.beans.TerminFacade ejbFacade;
    private List<Termin> items = null;
    private Termin selected;

    public TerminController() {
    }

    public Termin getSelected() {
        return selected;
    }

    public void setSelected(Termin selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getTerminPK().setPacijent(selected.getPacijent1().getSifrapacijenta());
        selected.setMedsestra((Medsestra) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logged"));
        selected.getTerminPK().setMedSestra(selected.getMedsestra().getSifraMedSestre());
    }

    protected void initializeEmbeddableKey() {
        selected.setTerminPK(new domen.TerminPK());
    }

    private TerminFacade getFacade() {
        return ejbFacade;
    }

    public Termin prepareCreate() {
        selected = new Termin();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TerminCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TerminUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TerminDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Termin> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    public Date currentDate(){
        Calendar c =Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);  
        c.set(Calendar.MINUTE, 0);  
        c.set(Calendar.SECOND, 0);  
        c.set(Calendar.MILLISECOND, 0);  
        return c.getTime(); 
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
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

    public Termin getTermin(domen.TerminPK id) {
        return getFacade().find(id);
    }

    public List<Termin> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Termin> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Termin.class)
    public static class TerminControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TerminController controller = (TerminController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "terminController");
            return controller.getTermin(getKey(value));
        }

        domen.TerminPK getKey(String value) {
            domen.TerminPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new domen.TerminPK();
            key.setMedSestra(Integer.parseInt(values[0]));
            key.setPacijent(Integer.parseInt(values[1]));
            key.setDatum(java.sql.Date.valueOf(values[2]));
            return key;
        }

        String getStringKey(domen.TerminPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getMedSestra());
            sb.append(SEPARATOR);
            sb.append(value.getPacijent());
            sb.append(SEPARATOR);
            sb.append(value.getDatum());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Termin) {
                Termin o = (Termin) object;
                return getStringKey(o.getTerminPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Termin.class.getName()});
                return null;
            }
        }

    }

}
