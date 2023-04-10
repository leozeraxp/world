package hello.resources;

import hello.model.dto.DtoUsuario;
import hello.model.entity.Usuario;
import hello.service.UsuarioService;
import hello.util.ValidarCpf;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UsuarioService usuarioService;

    @POST
    public Response create(@Valid DtoUsuario dtoUsuario){
        Usuario usuario = usuarioService.create(dtoUsuario);

        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id){
        Usuario usuario = usuarioService.get(id);

        return Response.status(Response.Status.OK).entity(usuario).build();
    }

    @GET
    @Path("/listar")
    public Response list(){
        List<Usuario> usuarios = usuarioService.list();

        return Response.status(Response.Status.OK).entity(usuarios).build();
    }

    @PUT
    @Path("/atualizar")
    public Response update(DtoUsuario dtoUsuario){
        Usuario usuario = usuarioService.update(dtoUsuario);

        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    @GET
    @Path("/teste")
    public Response teste(){
        ValidarCpf validarCpf = new ValidarCpf();

        validarCpf.executar("53240390043");
        return Response.status(Response.Status.OK).entity("funcionou?").build();
    }
}
