package hello.mailer;

import hello.model.entity.Email;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.mailer.runtime.MailConfig;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.inject.ConfigProperty;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.StringJoiner;

@ApplicationScoped
public class MyMailer{

    @Inject
    Mailer mailer;

    @Inject
    MailConfig config;
    private String username;

    private String password;

    public MyMailer() {

    }

    public Uni<Void> sendMail(Email email) {
        Mail mail = new Mail()
                .setFrom(email.getFrom())
                .addTo(email.getTo().toArray(new String[0]))
                .setSubject(email.getSubject())
                .setCc(email.getCc())
                .setText(email.getText())
                .setHtml(email.getText());


        mailer.send(mail);

        return null;
    }

    public void updateConfig(String username, String password){
        newConfig(username, password);
    }

    public void newConfig(String username, String password) {
        config.from = Optional.of(username);
        config.port = OptionalInt.of(465);
        config.username = Optional.of(username);
        config.password = Optional.of(password);
        config.ssl=true;
        config.startTLS="REQUIRED";
        config.login="REQUIRED";
        config.host = "smtp.gmail.com";
    }
}
