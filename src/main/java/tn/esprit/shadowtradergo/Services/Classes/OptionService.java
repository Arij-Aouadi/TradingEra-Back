package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Option;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.DAO.Repositories.OptionRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IOptionService;

import java.util.List;
@AllArgsConstructor
@Service
@NoArgsConstructor
public class OptionService implements IOptionService {
    @Autowired
    OptionRepository optionRepository ;

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
}