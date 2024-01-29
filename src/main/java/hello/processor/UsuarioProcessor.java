package hello.processor;

import hello.model.dto.DtoUsuario;
import hello.model.entity.Usuario;
import hello.service.UsuarioService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.reactive.messaging.rabbitmq.IncomingRabbitMQMessage;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped

public class UsuarioProcessor {

    @Inject
    UsuarioService usuarioService;

    @Incoming("request")
    @Blocking
    public CompletionStage<Void> processUsuario(Message<JsonObject> jsonUsuario) throws InterruptedException{
        System.out.println("Received message: " + jsonUsuario.getPayload());

        Thread.sleep(10000);

        JsonObject payload = jsonUsuario.getPayload();
        DtoUsuario dtoUsuario = DtoUsuario.
                builder().nome(payload.getString("nome"))
                        .cpf(payload.getString("cpf"))
                        .anoNascimento(payload.getInteger("anoNascimento"))
                                .build();

        Usuario usuario = usuarioService.create(dtoUsuario);

        System.out.println("NOVO USUÁRIO: ID:"+usuario.getId()+" NOME: "+usuario.getNome());

        return jsonUsuario.ack();
    }
}
