package hello.api.controller;

import hello.api.dto.DtoUsuario;
import hello.model.entity.Usuario;
import hello.service.impl.UsuarioServiceImpl;
import hello.util.ValidarCpf;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/usuario")
public class UserResource {

    @Inject
    private UsuarioServiceImpl usuarioService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid DtoUsuario dtoUsuario){
        Usuario usuario = Usuario.builder()
                .cpf(dtoUsuario.getCpf())
                .nome(dtoUsuario.getNome())
                .anoNascimento(dtoUsuario.getAnoNascimento())
                .build();

        usuarioService.create(usuario);

        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response get(@PathParam("id") Long id){
        Usuario usuario = usuarioService.get(id);

        return Response.status(Response.Status.OK).entity(usuario).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listar")
    public Response list(){
        List<Usuario> usuarios = usuarioService.list();

        return Response.status(Response.Status.OK).entity(usuarios).build();
    }

    @POST
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(DtoUsuario dtoUsuario){
        Usuario usuario = Usuario.builder()
                .id(dtoUsuario.getId())
                .cpf(dtoUsuario.getCpf())
                .nome(dtoUsuario.getNome())
                .anoNascimento(dtoUsuario.getAnoNascimento())
                .build();

        usuarioService.update(usuario);

        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    @GET
    @Path("/teste")
    @Produces(MediaType.APPLICATION_JSON)
    public Response teste(){
        ValidarCpf validarCpf = new ValidarCpf();

        validarCpf.executar("");
        return Response.status(Response.Status.OK).entity("funcionou?").build();
    }
}
