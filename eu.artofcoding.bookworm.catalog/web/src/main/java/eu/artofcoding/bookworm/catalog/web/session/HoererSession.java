package eu.artofcoding.bookworm.catalog.web.session;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Named
@SessionScoped
public class HoererSession implements Serializable {

    public boolean hasHoerernummer() {
        final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        final String hoerernummer = (String) req.getSession().getAttribute("hnr");
        return null != hoerernummer && !hoerernummer.isEmpty();
    }

    public String getHoerernummer() {
        final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return (String) req.getSession().getAttribute("hnr");
    }

    public String logout() {
        final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        req.getSession().removeAttribute("hnr");
        return "/login.html";
    }

}
