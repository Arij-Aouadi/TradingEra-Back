package tn.esprit.shadowtradergo.openai;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutputDto {
    private String question;
    private String answer;
}

