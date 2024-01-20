package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.User;

import java.util.List;
import java.util.Map;

public interface IGameService {
    public List<Map<String, Object>> getRankedPlayers() ;

}
