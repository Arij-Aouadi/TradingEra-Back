package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Action;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.TypeTransaction;
import tn.esprit.shadowtradergo.DAO.Entities.User;

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

    /////////////////////////////////////:
    Ordre passerOrdre(Ordre ordre);

    //////////////////////////////executerOrdreMarketOrder//////////////
    Ordre executerOrdreMarketOrder(Ordre ordre, long idAction);

    //////////////////mettre a jour
    void mettreAJourCarnetOrdres(long idAction , float meilleurPrix, int quantiteExecutee, TypeTransaction typeTransaction);

    //////////////////////::
    int quantiteDisponibleDansCarnetPourPrixDynamique(long idAction, float meilleurPrix);

    Ordre ajouterOrdreAuCarnet(Ordre ordre, Action action);

    boolean correspondanceImmediateDansCarnet(Ordre ordre);

    void executerTransactions(Ordre nouvelOrdre, List<Ordre> carnetOrdres);
}
