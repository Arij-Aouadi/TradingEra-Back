package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Game;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.User;

import java.util.Date;
import java.util.List;

public interface IOrdreService {
    List<Ordre> selectall ();
    Ordre add(Ordre ordre);
    Ordre edit(Ordre ordre);
    List<Ordre> selectAll();
    Ordre selectById(Long idO);
    void deleteById(Long idO);
    void delete(Ordre ordre);

    Ordre getById(long id);

    void deleteAll(List<Ordre> list);

}
