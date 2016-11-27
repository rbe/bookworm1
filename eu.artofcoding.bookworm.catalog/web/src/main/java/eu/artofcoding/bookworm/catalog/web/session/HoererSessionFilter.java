package eu.artofcoding.bookworm.catalog.web.session;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// See web.xml @WebFilter(filterName = "HoererSessionFilter", urlPatterns = "/*")
public class HoererSessionFilter implements Filter {

    private static final String HNR_KEY = "hnr";

    @Override
    public void destroy() {
        /* Do nothing */
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpSession session = ((HttpServletRequest) req).getSession(true);
        if (null != session) {
            if (request.getRequestURI().endsWith("logout")) {
                session.invalidate();
            } else {
                final String sessionHnr = (String) session.getAttribute(HNR_KEY);
                final boolean sessionHasNoHnr = null == sessionHnr || sessionHnr.isEmpty();
                if (sessionHasNoHnr) {
                    final String hnr = req.getParameter(HNR_KEY);
                    session.setAttribute(HNR_KEY, hnr);
                }
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        /* Do nothing */
    }

}
