package domen.jaf;

import domen.Stavkaintervencije;
import domen.jaf.util.JsfUtil;
import domen.jaf.util.JsfUtil.PersistAction;
import domen.beans.StavkaintervencijeFacade;

import java.io.Serializable;
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

@Named("stavkaintervencijeController")
@SessionScoped
public class StavkaintervencijeController implements Serializable {

    @EJB
    private domen.beans.StavkaintervencijeFacade ejbFacade;
    private List<Stavkaintervencije> items = null;
    private Stavkaintervencije selected;

    public StavkaintervencijeController() {
    }

    public Stavkaintervencije getSelected() {
        return selected;
    }

    public void setSelected(Stavkaintervencije selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getStavkaintervencijePK().setSifraintervencije(selected.getIntervencija().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setStavkaintervencijePK(new domen.StavkaintervencijePK());
    }

    private StavkaintervencijeFacade getFacade() {
        return ejbFacade;
    }

    public Stavkaintervencije prepareCreate() {
        selected = new Stavkaintervencije();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("StavkaintervencijeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("StavkaintervencijeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("StavkaintervencijeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Stavkaintervencije> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public Stavkaintervencije getStavkaintervencije(domen.StavkaintervencijePK id) {
        return getFacade().find(id);
    }

    public List<Stavkaintervencije> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Stavkaintervencije> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Stavkaintervencije.class)
    public static class StavkaintervencijeControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StavkaintervencijeController controller = (StavkaintervencijeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "stavkaintervencijeController");
            return controller.getStavkaintervencije(getKey(value));
        }

        domen.StavkaintervencijePK getKey(String value) {
            domen.StavkaintervencijePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new domen.StavkaintervencijePK();
            key.setRbStavke(Integer.parseInt(values[0]));
            key.setSifraintervencije(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(domen.StavkaintervencijePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getRbStavke());
            sb.append(SEPARATOR);
            sb.append(value.getSifraintervencije());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Stavkaintervencije) {
                Stavkaintervencije o = (Stavkaintervencije) object;
                return getStringKey(o.getStavkaintervencijePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Stavkaintervencije.class.getName()});
                return null;
            }
        }

    }

}
