package tn.esprit.shadowtradergo.openai;

import lombok.Getter;

@Getter
public class Choice {
    private String text;
    private Integer index;
    private Integer logprobs;
    private String finish_reason;
}