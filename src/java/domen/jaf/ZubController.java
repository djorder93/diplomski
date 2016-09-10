package domen.jaf;

import domen.Zub;
import domen.jaf.util.JsfUtil;
import domen.jaf.util.JsfUtil.PersistAction;
import domen.beans.ZubFacade;

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

@Named("zubController")
@SessionScoped
public class ZubController implements Serializable {

    @EJB
    private domen.beans.ZubFacade ejbFacade;
    private List<Zub> items = null;
    private Zub selected;

    public ZubController() {
    }

    public Zub getSelected() {
        return selected;
    }

    public void setSelected(Zub selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getZubPK().setPacijent(selected.getPacijent1().getSifrapacijenta());
    }

    protected void initializeEmbeddableKey() {
        selected.setZubPK(new domen.ZubPK());
    }

    private ZubFacade getFacade() {
        return ejbFacade;
    }

    public Zub prepareCreate() {
        selected = new Zub();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ZubCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ZubUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ZubDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Zub> getItems() {
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

    public Zub getZub(domen.ZubPK id) {
        return getFacade().find(id);
    }

    public List<Zub> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Zub> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Zub.class)
    public static class ZubControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ZubController controller = (ZubController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "zubController");
            return controller.getZub(getKey(value));
        }

        domen.ZubPK getKey(String value) {
            domen.ZubPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new domen.ZubPK();
            key.setOznaka(Integer.parseInt(values[0]));
            key.setPacijent(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(domen.ZubPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getOznaka());
            sb.append(SEPARATOR);
            sb.append(value.getPacijent());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Zub) {
                Zub o = (Zub) object;
                return getStringKey(o.getZubPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Zub.class.getName()});
                return null;
            }
        }

    }

}
