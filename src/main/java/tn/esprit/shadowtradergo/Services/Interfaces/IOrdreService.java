package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.*;

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

    Ordre AssignOrdretoidAction(Ordre ordre, long idAction);

    Ordre AssignOptionToOrdre(Ordre ordre, long optionId);

    Ordre passerOrdre(Ordre ordre);

    Ordre executerOrdreMarketOrder(Ordre ordre);

    void mettreAJourCarnetOrdres(long idSousJacent, float meilleurPrix, int quantiteExecutee, TypeTransaction typeTransaction, TypeSymbol typeSymbol);

    int quantiteDisponibleDansCarnetPourPrixDynamique(long idSousJacent, float meilleurPrix, TypeSymbol typeSymbol, TypeTransaction typeTransactionordre);

    Ordre ajouterOrdreAuCarnet(Ordre ordre, Action action);

    ///ki nzid id l action manrch aleh dima twali trajaa faux
    boolean correspondanceImmediateDansCarnet(Ordre ordre);

    //j ai pas encore tester cette methode
    void executerTransactions(Ordre nouvelOrdre, List<Ordre> carnetOrdres);
}
