package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Option;
import tn.esprit.shadowtradergo.DAO.Entities.TypeOption;
import tn.esprit.shadowtradergo.DAO.Entities.TypeTransaction;
import tn.esprit.shadowtradergo.DAO.Entities.User;

import java.util.List;

public interface IOptionService {


    List<Option> selectall ();
    Option add(Option option);
    Option edit(Option option);
    List<Option> selectAll();
    Option selectById(Long id);
    void deleteById(Long id);
    void delete(Option option);

    Option getById(long id);



    float CalculerProfitOuPerteOption(long optionId, TypeOption typeOption, TypeTransaction typeTransaction, float prixSousJacentExpiration);




    Option addOption(Option option, long actionId);

}
