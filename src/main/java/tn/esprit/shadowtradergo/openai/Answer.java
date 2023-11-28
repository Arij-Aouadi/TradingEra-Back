package tn.esprit.shadowtradergo.openai;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class Answer {
    String id;
    String object;
    LocalDate created;
    String model;
    List<Choice> choices;
    Usage usage;

}
