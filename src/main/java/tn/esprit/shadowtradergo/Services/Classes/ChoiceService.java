package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Choice;
import tn.esprit.shadowtradergo.DAO.Repositories.ChoiceRepository;

import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class ChoiceService {
    @Autowired
    private ChoiceRepository choiceRepository;

    public List<Choice> getAllChoices() {
        return choiceRepository.findAll();
    }

    public Choice getChoiceById(Long choiceId) {
        return choiceRepository.findById(choiceId).orElse(null);
    }

    public Choice createChoice(Choice choice) {
        return choiceRepository.save(choice);
    }

    public Choice updateChoice(Long choiceId, Choice updatedChoice) {
        Choice existingChoice = choiceRepository.findById(choiceId).orElse(null);

        if (existingChoice != null) {
            updatedChoice.setId(choiceId);
            return choiceRepository.save(updatedChoice);
        } else {
            return null;
        }
    }

    public boolean deleteChoice(Long choiceId) {
        if (choiceRepository.existsById(choiceId)) {
            choiceRepository.deleteById(choiceId);
            return true;
        }
        return false;
    }
}
