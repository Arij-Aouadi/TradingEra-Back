package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Historique;

import java.util.Date;
import java.util.List;

public interface IHistoriqueService {


     //List<Historique> TableauHistorique(Long userId, Date startDate, Date endDate);

     List<Historique> getTableauHistorique() ;
}
