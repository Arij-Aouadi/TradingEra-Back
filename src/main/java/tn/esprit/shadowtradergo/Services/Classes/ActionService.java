package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Action;
import tn.esprit.shadowtradergo.DAO.Repositories.ActionRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IActionService;

import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class ActionService implements IActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Override
    public List<Action> selectall() {
        return actionRepository.findAll();
    }

    @Override
    public Action add(Action action) {
        return actionRepository.save(action);
    }
    @Override
    public Action edit(Action action) {
        return actionRepository.save(action);
    }

    @Override
    public List<Action> selectAll() {
        return (List<Action>)actionRepository.findAll();
    }

    @Override
    public Action selectById(Long idA) {
        return actionRepository.findById(idA).get();    }

    @Override
    public void deleteById(Long idA) {actionRepository.deleteById(idA);

    }

    @Override
    public void delete(Action action) {

        actionRepository.delete(action);

    }
    @Override
    public Action getById(long idA) {
        return actionRepository.findById(idA).get();
    }

    @Override
    public void deleteAll(List<Action> list) {
        actionRepository.deleteAll(list);
    }

}
