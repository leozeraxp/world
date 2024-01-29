package hello.processor;

import hello.model.dto.DtoUsuario;
import hello.model.entity.Usuario;
import hello.service.EmailService;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.inject.Inject;

public class EmailProcessor {

    @Inject
    EmailService emailService;

    @Incoming("send-email")
    @Blocking
    public String process(String texto) throws InterruptedException{
        //Thread.sleep(5000);

        System.out.println("ENVIANDO EMAIL?");

        String s = emailService.enviarEmail(texto);

        System.out.println("E-MAIL ENVIADO!? "+s);

        return s+" com o conteúdo "+texto;
    }


}
