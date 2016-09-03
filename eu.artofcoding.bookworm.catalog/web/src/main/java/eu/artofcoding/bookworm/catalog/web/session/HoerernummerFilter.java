package eu.artofcoding.bookworm.catalog.web.session;

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

    private static final String HNR_KEY = "hnr";

    @Override
    public void destroy() {
        /* Do nothing */
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        final HttpSession session = ((HttpServletRequest) req).getSession();
        final Object sessionHnr = session.getAttribute(HNR_KEY);
        if (null == sessionHnr) {
            final String hnr = req.getParameter(HNR_KEY);
            session.setAttribute(HNR_KEY, hnr);
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        /* Do nothing */
    }

}
