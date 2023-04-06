package hello.service;

import hello.model.entity.Usuario;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

public interface UsuarioService {

    public Usuario create(Usuario usuario);

    public Usuario get(Long idUsuario);

    public List<Usuario> list();

    public Usuario update(Usuario usuario);

    public void delete(Long idusuario);
}
