package hello.service;

import hello.mailer.MyMailer;
import hello.model.entity.Email;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;


@ApplicationScoped
public class EmailService {

    @Inject
    MyMailer myMailer;

    public String enviarEmail(String texto){

        try {
            String username = "lealves3098@gmail.com";
            String password = "pdrr xoyw jslr rsvy";

            myMailer.updateConfig(username, password);


            Email email = Email.builder()
                    .from("lealves3098@gmail.com")
                    .to(new ArrayList<>(Arrays.asList(
                            "danilocarsan@gmail.com",
                            "aevpclovis67@gmail.com"
                    )))
                    .cc(new ArrayList<>(Arrays.asList(
                            "leonardo.alves@tecnogroup.com"
                    )))
                    .subject("Testando lista de destinatarios")
                    .text(texto)
                    .build();

            /*mailer1.send(Mail.withText(email.getTo().getEmail(),
                    email.getSubject(),
                    email.getText()));*/

            Uni<Void> voidUni = myMailer.sendMail(email);

            voidUni.toMulti();
        }catch (Exception e){
            e.printStackTrace();
        }


            return "Enviando e-mail!";
    }
}
