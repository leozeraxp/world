package hello.exceptions.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionHandler implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ex.getLocalizedMessage())
                .build();
    }
}
