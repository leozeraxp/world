package hello.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import hello.model.entity.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.transaction.Transactional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public boolean cpfJaExiste(String cpf) {
        long count = count("cpf", cpf);
        return count > 0;
    }
}
