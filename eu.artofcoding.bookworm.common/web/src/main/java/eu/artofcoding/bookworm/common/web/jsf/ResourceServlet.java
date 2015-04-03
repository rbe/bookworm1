/*
 * Bookworm
 *
 * Copyright (C) 2011-2015 art of coding UG, http://www.art-of-coding.eu
 * Alle Rechte vorbehalten. Nutzung unterliegt Lizenzbedingungen.
 * All rights reserved. Use is subject to license terms.
 */

package eu.artofcoding.bookworm.common.web.jsf;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/css/*", "/js/*", "/img/*", "/fonts/*"})
public final class ResourceServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ResourceServlet.class.toString());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedResource = request.getRequestURI();
        final String contextPath = request.getContextPath();
        if (null != contextPath && !contextPath.isEmpty()) {
            requestedResource = requestedResource.substring(contextPath.length());
        }
        final boolean hasRequestedResource = requestedResource != null;
        if (!hasRequestedResource) {
            LOGGER.severe("No requested resource: " + requestedResource);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            final String decodeResourceName = URLDecoder.decode(requestedResource, "UTF-8");
            final File resource = new File(WebFilesystem.BASE_PATH, decodeResourceName);
            final boolean resourceReadable = resource.exists() && resource.canRead();
            if (!resourceReadable) {
                LOGGER.severe("Requested resource is not readable: " + resource.getAbsolutePath());
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                final String contentType = getServletContext().getMimeType(resource.getName());
                final boolean contentTypeAllowed = contentType != null && (contentType.startsWith("text") || contentType.startsWith("image"));
                LOGGER.fine("Requested resource " + resource.getAbsolutePath() + " has allowed mime type (" + contentType + "): " + contentTypeAllowed);
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
