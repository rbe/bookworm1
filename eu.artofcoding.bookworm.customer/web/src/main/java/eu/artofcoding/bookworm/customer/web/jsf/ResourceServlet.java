/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.customer.web.jsf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

@WebServlet(urlPatterns = {"*.css", "/css/*", "/js/*", "/img/*", "/fonts/*"})
public final class ResourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedResource = request.getRequestURI();
        final String contextPath = request.getContextPath();
        if (null != contextPath && !contextPath.isEmpty()) {
            requestedResource = requestedResource.substring(contextPath.length());
        }
        final boolean hasRequestedResource = requestedResource != null;
        if (!hasRequestedResource) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            final String decodeResourceName = URLDecoder.decode(requestedResource, "UTF-8");
            final File resource = new File(WebFilesystem.BASE_PATH, decodeResourceName);
            final boolean resourceReadable = resource.exists() && resource.canRead();
            if (!resourceReadable) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                final String contentType = getServletContext().getMimeType(resource.getName());
                final boolean contentTypeAllowed = contentType != null && (contentType.startsWith("text") || contentType.startsWith("image"));
                if (!contentTypeAllowed) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    response.reset();
                    response.setContentType(contentType);
                    response.setHeader("Content-Length", String.valueOf(resource.length()));
                    Files.copy(resource.toPath(), response.getOutputStream());
                }
            }
        }
    }

}
