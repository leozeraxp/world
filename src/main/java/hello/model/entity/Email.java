package hello.model.entity;

import io.quarkus.mailer.Mail;
import lombok.*;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@Builder
@Data
public class Email{

    private String from;

    private List<String> to;

    private String subject;

    private String text;

    private List<String> cc;
}
