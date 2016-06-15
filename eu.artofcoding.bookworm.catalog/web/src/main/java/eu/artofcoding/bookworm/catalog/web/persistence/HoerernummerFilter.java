package eu.artofcoding.bookworm.catalog.web.persistence;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "HoerernummerFilter", urlPatterns = "/*")
public class HoerernummerFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        final HttpSession session = ((HttpServletRequest) req).getSession();
        final Object sessionHnr = session.getAttribute("hnr");
        if (null == sessionHnr) {
            final String hnr = req.getParameter("hnr");
            session.setAttribute("hnr", hnr);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public static boolean hasHoerernummer() {
        final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        final String hoerernummer = (String) req.getSession().getAttribute("hnr");
        return null != hoerernummer && !hoerernummer.isEmpty();
    }

    public static String getHoerernummer() {
        final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return (String) req.getSession().getAttribute("hnr");
    }


}
