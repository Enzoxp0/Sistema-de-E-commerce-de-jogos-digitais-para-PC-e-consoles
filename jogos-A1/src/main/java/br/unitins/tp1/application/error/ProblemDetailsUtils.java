package br.unitins.tp1.application.error;

import java.net.URI;
import jakarta.ws.rs.core.UriInfo;

public final class ProblemDetailsUtils {

    private ProblemDetailsUtils() {
    }

    public static URI getRequestPath(UriInfo uriInfo) {
        if (uriInfo == null) {
            return null;
        }
        try {
            String path = uriInfo.getPath();
            if (path == null || path.isBlank()) {
                return URI.create("/");
            }
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            return URI.create(path);
        } catch (Exception e) {
            return null;
        }
    }
}
