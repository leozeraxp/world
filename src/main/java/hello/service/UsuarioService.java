package hello.service;

import hello.exceptions.RegraNegocioException;
import hello.model.dto.DtoUsuario;
import hello.model.entity.Usuario;
import hello.repository.UsuarioRepository;
import hello.util.ValidarCpf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario create(DtoUsuario dtoUsuario) {
        Usuario usuario = Usuario.builder()
                .cpf(dtoUsuario.getCpf())
                .nome(dtoUsuario.getNome())
                .anoNascimento(dtoUsuario.getAnoNascimento())
                .build();

        validarCpf(usuario.getCpf());

        usuarioRepository.persistAndFlush(usuario);
        return usuario;
    }

    public Usuario get(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario);

        if(Objects.isNull(usuario)){
            throw new NotFoundException("Usuário não encontrado!");
        }

        return usuario;
    }

    public List<Usuario> list() {
        List<Usuario> usuarios = usuarioRepository.listAll();
        return usuarios;
    }
    @Transactional
    public Usuario update(DtoUsuario dtoUsuario) {
        Usuario usuario = Usuario.builder()
                .id(dtoUsuario.getId())
                .cpf(dtoUsuario.getCpf())
                .nome(dtoUsuario.getNome())
                .anoNascimento(dtoUsuario.getAnoNascimento())
                .build();

        Usuario usuarioBancoDeDados = usuarioRepository.findById(usuario.getId());

        usuarioBancoDeDados.setNome(usuario.getNome());
        usuarioBancoDeDados.setCpf(usuario.getCpf());
        usuarioBancoDeDados.setAnoNascimento(usuario.getAnoNascimento());

        validarCpf(usuario.getCpf());

        usuarioRepository.persistAndFlush(usuarioBancoDeDados);

        return usuario;
    }

    @Transactional
    public void delete(Long idusuario) {
        usuarioRepository.deleteById(idusuario);
    }

    private void validarCpf(String cpf){
        ValidarCpf validarCpf = new ValidarCpf();
        validarCpf.executar(cpf);

        if(usuarioRepository.cpfJaExiste(cpf)){
            throw new RegraNegocioException("ESTE_CPF_JA_ESTA_CADASTRADO");
        };
    }
}
