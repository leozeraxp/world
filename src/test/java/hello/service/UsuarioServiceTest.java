package hello.service;

import hello.model.dto.DtoUsuario;
import hello.model.entity.Usuario;
import hello.repository.UsuarioRepository;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UsuarioServiceTest {
    @Inject
    UsuarioService usuarioService;

    @InjectMock
    UsuarioRepository usuarioRepository;
    @Test
    void deveCriarUmUsuario() {
        DtoUsuario dtoUsuario = DtoUsuario.builder()
                .cpf("52538569001")
                .nome("João")
                .anoNascimento(2000)
                .build();


        Usuario usuario = usuarioService.create(dtoUsuario);

        assertNotNull(usuario);
        assertEquals(dtoUsuario.getCpf(), usuario.getCpf());
        assertEquals(dtoUsuario.getNome(), usuario.getNome());
        assertEquals(dtoUsuario.getAnoNascimento(), usuario.getAnoNascimento());

        verify(usuarioRepository, times(1)).persistAndFlush(ArgumentMatchers.any(Usuario.class));
    }

    @Test
    void deveRetornarUsuarioExistente(){
        Long idUsuario = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setNome("Fulano");
        usuario.setCpf("111.222.333-44");
        usuario.setAnoNascimento(2000);

        when(usuarioRepository.findById(idUsuario)).thenReturn(usuario);

        Usuario usuarioRetornado = usuarioService.get(idUsuario);

        assertNotNull(usuarioRetornado);
        assertEquals(idUsuario, usuarioRetornado.getId());
        assertEquals("Fulano", usuarioRetornado.getNome());
        assertEquals("111.222.333-44", usuarioRetornado.getCpf());
        assertEquals(2000, usuarioRetornado.getAnoNascimento());
    }

    @Test
    public void deveLancarNotFoundExceptionAoBuscarUsuarioInexistente() {
        Long idUsuario = 1L;
        when(usuarioRepository.findById(idUsuario)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> usuarioService.get(idUsuario));

        verify(usuarioRepository, Mockito.times(1)).findById(idUsuario);
    }

    @Test
    void deveRetornarUmaListaDeUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario());
        usuarios.add(new Usuario());

        when(usuarioRepository.listAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.list();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void deveAtualizarUmUsuario() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("Alice")
                .anoNascimento(1990)
                .cpf("36692452007")
                .build();
        usuarioRepository.persist(usuario);

        DtoUsuario dtoUsuario = DtoUsuario.builder()
                .id(usuario.getId())
                .nome("Alicent")
                .anoNascimento(1995)
                .build();

        Mockito.when(usuarioRepository.findById(any())).thenReturn(usuario);

        Usuario result = usuarioService.update(dtoUsuario);

        assertEquals(dtoUsuario.getId(), result.getId());
        assertEquals(usuario.getCpf(),result.getCpf());
        assertEquals(dtoUsuario.getNome(), result.getNome());
        assertEquals(dtoUsuario.getAnoNascimento(), result.getAnoNascimento());
    }

}