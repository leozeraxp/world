package hello.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class DtoUsuario {

    private Long id;

    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @NotNull(message = "O ano de nascimento não pode estar vazio")
    private Integer anoNascimento;

    @NotBlank(message = "O cpf não pode estar vazio")
    private String cpf;
}
