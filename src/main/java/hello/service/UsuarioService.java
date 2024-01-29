package hello.service;

import hello.exceptions.RegraNegocioException;
import hello.model.dto.DtoUsuario;
import hello.model.entity.Usuario;
import hello.repository.UsuarioRepository;
import hello.util.ValidarCpf;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

//        /validarCpf(usuario.getCpf());

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
        Usuario usuario = usuarioRepository.findById(dtoUsuario.getId());

        usuario.setNome(dtoUsuario.getNome());
        usuario.setAnoNascimento(dtoUsuario.getAnoNascimento());

        usuarioRepository.persistAndFlush(usuario);

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

    public void gerarPdf() throws JRException, FileNotFoundException {
        JRDataSource dataSource = new JREmptyDataSource();

        String jrxml = "C:\\Users\\leonardo.alves\\Documents\\GitHub\\world\\src\\main\\java\\hello\\relatorios\\teste.jrxml";

        String jasper = JasperCompileManager.compileReportToFile(jrxml);

        System.out.println(jasper);

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("nome","Leonardo");
        /*parametros.put("$F{cpf}","123456789");
        parametros.put("$F{nascimento}", "30/08/1998");*/

        JasperPrint jasperPrint = JasperFillManager
                .fillReport(jasper, parameter, dataSource);

        //JasperExportManager.exportReportToPdfFile(jasper, "C:\\Users\\leonardo.alves\\Documents\\GitHub\\world\\src\\main\\java\\hello\\relatorios\\teste.pdf");

        OutputStream saida = new FileOutputStream("C:\\Users\\leonardo.alves\\Documents\\GitHub\\world\\src\\main\\java\\hello\\relatorios\\teste.pdf");


        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, saida);
        exporter.exportReport();

    }
}
