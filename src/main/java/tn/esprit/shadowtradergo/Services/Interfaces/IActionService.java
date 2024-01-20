package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Action;

import java.util.List;

public interface IActionService {
    List<Action> selectall();

    Action add(Action action);

    Action edit(Action action);

    List<Action> selectAll();

    Action selectById(Long idA);

    void deleteById(Long idA);

    void delete(Action action);

    Action getById(long idA);

    void deleteAll(List<Action> list);
    
}
