package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Action;
import tn.esprit.shadowtradergo.DAO.Entities.Option;
import tn.esprit.shadowtradergo.DAO.Repositories.ActionRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OptionRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IOptionService;

import java.util.List;
@AllArgsConstructor
@Service
@NoArgsConstructor
public class OptionService implements IOptionService {
@Autowired
    OptionRepository optionRepository ;
    @Autowired
    ActionRepository actionRepository;

    @Override
    public Option add(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public List<Option> selectall() {
        return optionRepository.findAll();
    }



    @Override
    public Option edit(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public List<Option> selectAll() {
      return   optionRepository.findAll();
    }

    @Override
    public Option selectById(Long id) {
        return optionRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {optionRepository.deleteById(id);

    }

    @Override
    public void delete(Option option) {  optionRepository.delete(option);

    }



    @Override
    public Option getById(long id) {
        return optionRepository.findById(id).get();

    }
    @Override
    public Option addOption(Option option, long actionId) {
        // Retrieve the Action by ID
        Action action = actionRepository.findById(actionId).orElse(null);

        if (action != null) {
            // Set the Action for the Option
            option.setAction1(action);

            // Update the Action to include the new Option
            action.getOptionList().add(option);
            actionRepository.save(action);

            // Save the Option in the database
            optionRepository.save(option);

            return option;
        } else {
            // Handle the case where the Action with the given ID is not found
            throw new IllegalArgumentException("Action with ID " + actionId + " not found. Options are not allowed for this type of order");
        }
    }

}
