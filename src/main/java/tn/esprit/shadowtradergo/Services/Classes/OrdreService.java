package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.*;
import tn.esprit.shadowtradergo.DAO.Repositories.ActionRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OptionRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OrdreRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;

import java.util.*;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class OrdreService implements IOrdreService {

    @Autowired
    private OrdreRepository ordreRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private OptionRepository optionRepository;

    @Override
    public List<Ordre> selectall() {
        return ordreRepository.findAll();
    }

    @Override
    public Ordre add(Ordre ordre) {
        return ordreRepository.save(ordre);
    }
    @Override
    public Ordre edit(Ordre ordre) {
        return ordreRepository.save(ordre);
    }

    @Override
    public List<Ordre> selectAll() {
        return (List<Ordre>)ordreRepository.findAll();
    }

    @Override
    public Ordre selectById(Long idO) {
        return ordreRepository.findById(idO).get();    }

    @Override
    public void deleteById(Long idO) {ordreRepository.deleteById(idO);

    }



    @Override
    public void delete(Ordre ordre) {

        ordreRepository.delete(ordre);

    }
    @Override
    public Ordre getById(long idO) {
        return ordreRepository.findById(idO).get();
    }

    @Override
    public void deleteAll(List<Ordre> list) {
        ordreRepository.deleteAll(list);
    }
    @Override
    public Ordre AssignOrdretoidAction(Ordre ordre, long idAction) {
        // Retrieve the Option by ID
        Action action=actionRepository.findById(idAction).orElse(null);

        if (action != null) {
            // Check the type of symbol
            TypeSymbol typeSymbol = ordre.getTypeSymbol();

            if (typeSymbol == TypeSymbol.Action) {
                // Assign the Option to the Ordre for actions
                ordre.setAction(action);
                ordreRepository.save(ordre);

                return ordre;
            } else {
                // Handle the case for options
                // You might want to log a warning or apply a different logic
                throw new IllegalArgumentException("action are not allowed for this type of order");
                // ordre.setOption(null); // or throw new IllegalArgumentException("Options are not allowed for this type of order");
            }

            // Save the updates in the database

        } else {
            // Handle the case where the action with the given ID is not found
            return null;
        }
    }
@Override
public Ordre AssignOptionToOrdre(Ordre ordre, long optionId) {
    // Retrieve the Option by ID
    Option option = optionRepository.findById(optionId).orElse(null);

    if (option != null) {
        // Check the type of symbol
        TypeSymbol typeSymbol = ordre.getTypeSymbol();

        if (typeSymbol == TypeSymbol.Option) {
            // Assign the Option to the Ordre for actions
            ordre.setOption(option);
            ordreRepository.save(ordre);

            return ordre;
        } else {
            // Handle the case for options
            // You might want to log a warning or apply a different logic
            throw new IllegalArgumentException("Options are not allowed for this type of order");
            // ordre.setOption(null); // or throw new IllegalArgumentException("Options are not allowed for this type of order");
        }

        // Save the updates in the database

    } else {
        // Handle the case where the Option with the given ID is not found
        return null;
    }
}
    @Override

    public Ordre passerOrdre(Ordre ordre) {
        // Logique de traitement de l'ordre (vérifications, validations, etc.)
        if (ordre.getTypetransaction() == TypeTransaction.Sell || ordre.getTypetransaction() == TypeTransaction.Buy) {

            if (ordre.getTypeordre() == TypeOrdre.Marché) {

                return executerOrdreMarketOrder(ordre);
            } else if (ordre.getTypeordre() == TypeOrdre.Limite) {
                //  return ajouterOrdreAuCarnet(ordre, action);
                return ordre;     ////hedha temporaire star li foukou howa l mrigel just lin nekhdm l methode
            } else if (ordre.getTypeordre() == TypeOrdre.Stop) {
                //  return gererOrdreSeuilDeclenchement(ordre, action);
                return ordre;     ////hedha temporaire star li foukou howa l mrigel just lin nekhdm l methode

            }
        }

        return ordreRepository.save(ordre);
    }

    //////////////////////////////executerOrdreMarketOrder feha mochkol tekhdem w b shih ama tetzd ligne mtaa ordre fel action//////////////
    // Import statements (assuming you have these classes in your project)

    @Override
    public Ordre executerOrdreMarketOrder(Ordre ordre) {
        // Get the type of symbol of the order
        TypeSymbol typeSymbol = ordre.getTypeSymbol();
        System.out.println("TypeSymbol: " + typeSymbol);
        if (typeSymbol == TypeSymbol.Option) {
            // Rest of the code specific to options...
            float meilleurPrixOption = 30;
            TypeTransaction typeTransactionOption = ordre.getTypetransaction();
            System.out.println("Ordre object: " + ordre);
            System.out.println("Option object: " + ordre.getOption());
            // Replace this method call with your actual implementation
            int quantiteDisponibleOption = quantiteDisponibleDansCarnetPourPrixDynamique(
                    ordre.getOption().getIdOption(), meilleurPrixOption, typeSymbol, typeTransactionOption);

            // Rest of the code...
            if (typeTransactionOption == TypeTransaction.Buy && quantiteDisponibleOption >= ordre.getQuantiteAchat()) {
                // Execute completely for Buy order (options)
                // ... rest of the code for buying options ...
                ordre.setPrixLimite(meilleurPrixOption);
                ordre.setDateOrdre(new Date());
                ordre.setStatut(TypeStatut.ferme);
            //    ordre.setDateAchatVente(new Date());

                // Save the updates in the database
                ordreRepository.save(ordre);

                // Update the order book
                mettreAJourCarnetOrdres(ordre.getOption().getIdOption(), meilleurPrixOption,
                        ordre.getQuantiteAchat(), typeTransactionOption, typeSymbol);

                // Logique spécifique aux options
                long idOption = ordre.getOption().getIdOption();
                Option option = optionRepository.findById(idOption).orElse(null);
                float montantTotalOption = ordre.getQuantiteAchat() * meilleurPrixOption;
                System.out.println("Montant total de l'option: " + montantTotalOption);

                // Save the updates in the database
                optionRepository.save(option);
            } else if (typeTransactionOption == TypeTransaction.Sell && quantiteDisponibleOption >= ordre.getQuantiteVente()) {
                // Execute completely for Sell order (options)
                // ... rest of the code for selling options ...
                ordre.setPrixLimite(meilleurPrixOption);
                ordre.setDateOrdre(new Date());
                ordre.setStatut(TypeStatut.ferme);
                // ordre.setDateAchatVente(new Date());

                // Save the updates in the database
                ordreRepository.save(ordre);

                // Update the order book
                mettreAJourCarnetOrdres(ordre.getOption().getIdOption(), meilleurPrixOption,
                        ordre.getQuantiteVente(), typeTransactionOption, typeSymbol);

                // Logique spécifique aux options
                long idOption = ordre.getOption().getIdOption();
                Option option = optionRepository.findById(idOption).orElse(null);
                float montantTotalOption = ordre.getQuantiteVente() * meilleurPrixOption;
                System.out.println("Montant total de l'option: " + montantTotalOption);

                // Save the updates in the database
                optionRepository.save(option);
            } else if (quantiteDisponibleOption > 0) {
                // Execute partially (options)
                // ... rest of the code for partial execution of options ...
                ordre.setPrixLimite(meilleurPrixOption);
                ordre.setDateOrdre(new Date());
                ordre.setStatut(TypeStatut.partiel);
        //        ordre.setDateAchatVente(new Date());

                // Update the remaining quantity in the order
                int remainingQuantityOption = typeTransactionOption == TypeTransaction.Buy
                        ? ordre.getQuantiteAchat() - quantiteDisponibleOption
                        : ordre.getQuantiteVente() - quantiteDisponibleOption;

                ordre.setQuantite(remainingQuantityOption);

                // Save the updates in the database
                ordreRepository.save(ordre);

                // Update the order book
                mettreAJourCarnetOrdres(ordre.getOption().getIdOption(), meilleurPrixOption,
                        quantiteDisponibleOption, typeTransactionOption, typeSymbol);

                // Logique spécifique aux options
                long idOption = ordre.getOption().getIdOption();
                Option option = optionRepository.findById(idOption).orElse(null);
                float montantPartielOption = quantiteDisponibleOption * meilleurPrixOption;
                System.out.println("Montant partiel de l'option: " + montantPartielOption);

                // Save the updates in the database
                optionRepository.save(option);

                // Repeat the process with the new remaining quantity
                Ordre remainingOrdre = new Ordre();
                remainingOrdre.setOption(ordre.getOption());  // Copy necessary information for the new order
                remainingOrdre.setQuantite(remainingQuantityOption);  // Set the remaining quantity
                remainingOrdre = executerOrdreMarketOrder(remainingOrdre);
            }

        }

        else if (typeSymbol == TypeSymbol.Action) {
            // Rest of the code specific to actions...
            float meilleurPrixAction = 30;
            TypeTransaction typeTransactionAction = ordre.getTypetransaction();

            // Replace this method call with your actual implementation
            int quantiteDisponibleAction = quantiteDisponibleDansCarnetPourPrixDynamique(
                    ordre.getAction().getIdAction(), meilleurPrixAction, typeSymbol, typeTransactionAction);

            // Rest of the code...
            if (typeTransactionAction == TypeTransaction.Buy && quantiteDisponibleAction >= ordre.getQuantiteAchat()) {
                // Execute completely for Buy order
                // ... rest of the code for buying ...
                ordre.setPrixLimite(meilleurPrixAction);
                ordre.setDateOrdre(new Date());
                ordre.setStatut(TypeStatut.ferme);
            //    ordre.setDateAchatVente(new Date());

                // Save the updates in the database
                ordreRepository.save(ordre);

                // Update the order book
                mettreAJourCarnetOrdres(ordre.getAction().getIdAction(), meilleurPrixAction,
                        ordre.getQuantiteAchat(), typeTransactionAction, typeSymbol);

                // Logique spécifique aux actions
                long idAction = ordre.getAction().getIdAction();
                Action action = actionRepository.findById(idAction).orElse(null);
                float montantTotalAction = ordre.getQuantiteAchat() * meilleurPrixAction;
                System.out.println("Montant total de l'action: " + montantTotalAction);

                // Save the updates in the database
                actionRepository.save(action);
            } else if (typeTransactionAction == TypeTransaction.Sell && quantiteDisponibleAction >= ordre.getQuantiteVente()) {
                // Execute completely for Sell order
                // ... rest of the code for selling ...
                ordre.setPrixLimite(meilleurPrixAction);
                ordre.setDateOrdre(new Date());
                ordre.setStatut(TypeStatut.ferme);
            //    ordre.setDateAchatVente(new Date());

                // Save the updates in the database
                ordreRepository.save(ordre);

                // Update the order book
                mettreAJourCarnetOrdres(ordre.getAction().getIdAction(), meilleurPrixAction,
                        ordre.getQuantiteVente(), typeTransactionAction, typeSymbol);

                // Logique spécifique aux actions
                long idAction = ordre.getAction().getIdAction();
                Action action = actionRepository.findById(idAction).orElse(null);
                float montantTotalAction = ordre.getQuantiteVente() * meilleurPrixAction;
                System.out.println("Montant total de l'action: " + montantTotalAction);

                // Save the updates in the database
                actionRepository.save(action);
            } else if (quantiteDisponibleAction > 0) {
                // Execute partially
                // ... rest of the code for partial execution ...
                ordre.setPrixLimite(meilleurPrixAction);
                ordre.setDateOrdre(new Date());
                ordre.setStatut(TypeStatut.partiel);
             //   ordre.setDateAchatVente(new Date());

                // Update the remaining quantity in the order
                int remainingQuantityAction = typeTransactionAction == TypeTransaction.Buy
                        ? ordre.getQuantiteAchat() - quantiteDisponibleAction
                        : ordre.getQuantiteVente() - quantiteDisponibleAction;

                ordre.setQuantite(remainingQuantityAction);

                // Save the updates in the database
                ordreRepository.save(ordre);

                // Update the order book
                mettreAJourCarnetOrdres(ordre.getAction().getIdAction(), meilleurPrixAction,
                        quantiteDisponibleAction, typeTransactionAction, typeSymbol);

                // Logique spécifique aux actions
                long idAction = ordre.getAction().getIdAction();
                Action action = actionRepository.findById(idAction).orElse(null);
                float montantPartielAction = quantiteDisponibleAction * meilleurPrixAction;
                System.out.println("Montant partiel de l'action: " + montantPartielAction);

                // Save the updates in the database
                actionRepository.save(action);

                // Repeat the process with the new remaining quantity
                Ordre remainingOrdre = new Ordre();
                remainingOrdre.setAction(ordre.getAction());  // Copy necessary information for the new order
                remainingOrdre.setQuantite(remainingQuantityAction);  // Set the remaining quantity
                remainingOrdre = executerOrdreMarketOrder(remainingOrdre);
            }
        }


        return ordre;
    }



    @Override
public void mettreAJourCarnetOrdres(long idSousJacent, float meilleurPrix, int quantiteExecutee, TypeTransaction typeTransaction, TypeSymbol typeSymbol) {
        if (typeSymbol == TypeSymbol.Action) {
            Action action = actionRepository.findById(idSousJacent)
                    .orElseThrow(() -> new ExpressionException("Action non trouvée avec l'ID : " + idSousJacent));

            List<Ordre> carnetOrdres = action.getOrdreList();

            // Recherchez l'ordre correspondant au meilleur prix
            Ordre ordreMeilleurPrix = null;

            for (Ordre o : carnetOrdres) {
                if (o.getPrixLimite() == meilleurPrix && o.getTypetransaction() != typeTransaction) {
                    ordreMeilleurPrix = o;
                    break;  // Sortez de la boucle dès que l'ordre correspondant est trouvé
                }
            }
            if (ordreMeilleurPrix != null && quantiteExecutee > 0) {
                if (typeTransaction != ordreMeilleurPrix.getTypetransaction() && typeTransaction == TypeTransaction.Buy) {

                    if (ordreMeilleurPrix.getQuantiteVente() <= quantiteExecutee) {

                        int quantiteRestante = quantiteExecutee - ordreMeilleurPrix.getQuantiteVente();
                        ordreMeilleurPrix.setQuantiteVente(0);

                        // Traitez la quantité restante avec une récursion
                        mettreAJourCarnetOrdres(idSousJacent, meilleurPrix, quantiteRestante, typeTransaction, typeSymbol);
                    } else {
                        // Pour un ordre d'achat, diminuez la quantité
                        ordreMeilleurPrix.setQuantiteVente(ordreMeilleurPrix.getQuantiteVente() - quantiteExecutee);

                    }   // Enregistrez les mises à jour dans la base de données
                    ordreRepository.save(ordreMeilleurPrix);
                    actionRepository.save(action);
                } else if (typeTransaction != ordreMeilleurPrix.getTypetransaction() && typeTransaction == TypeTransaction.Sell) {

                    if (ordreMeilleurPrix.getQuantiteAchat() <= quantiteExecutee) {

                        int quantiteRestante = quantiteExecutee - ordreMeilleurPrix.getQuantiteAchat();
                        ordreMeilleurPrix.setQuantiteAchat(0);

                        // Traitez la quantité restante avec une récursion
                        mettreAJourCarnetOrdres(idSousJacent, meilleurPrix, quantiteRestante, typeTransaction, typeSymbol);
                    } else {
                        ordreMeilleurPrix.setQuantiteAchat(ordreMeilleurPrix.getQuantiteAchat() - quantiteExecutee);

                    }   // Enregistrez les mises à jour dans la base de données
                    ordreRepository.save(ordreMeilleurPrix);
                    actionRepository.save(action);
                }
            }
        }

        if (typeSymbol == TypeSymbol.Option) {
            Option option = optionRepository.findById(idSousJacent)
                    .orElseThrow(() -> new ExpressionException("option non trouvée avec l'ID : " + idSousJacent));

            List<Ordre> carnetOrdres = option.getOrdreOption();
            // Recherchez l'ordre correspondant au meilleur prix
            Ordre ordreMeilleurPrix = null;

            for (Ordre o : carnetOrdres) {
                if (o.getPrixLimite() == meilleurPrix && o.getTypetransaction() != typeTransaction) {
                    ordreMeilleurPrix = o;
                    break;  // Sortez de la boucle dès que l'ordre correspondant est trouvé
                }
            }
            if (ordreMeilleurPrix != null && quantiteExecutee > 0) {
                if (typeTransaction != ordreMeilleurPrix.getTypetransaction() && typeTransaction == TypeTransaction.Buy) {

                    if (ordreMeilleurPrix.getQuantiteVente() <= quantiteExecutee) {

                        int quantiteRestante = quantiteExecutee - ordreMeilleurPrix.getQuantiteVente();
                        ordreMeilleurPrix.setQuantiteVente(0);

                        // Traitez la quantité restante avec une récursion
                        mettreAJourCarnetOrdres(idSousJacent, meilleurPrix, quantiteRestante, typeTransaction, typeSymbol);
                    } else {
                        // Pour un ordre d'achat, diminuez la quantité
                        ordreMeilleurPrix.setQuantiteVente(ordreMeilleurPrix.getQuantiteVente() - quantiteExecutee);

                    }   // Enregistrez les mises à jour dans la base de données
                    ordreRepository.save(ordreMeilleurPrix);
                    optionRepository.save(option);
                } else if (typeTransaction != ordreMeilleurPrix.getTypetransaction() && typeTransaction == TypeTransaction.Sell) {

                    if (ordreMeilleurPrix.getQuantiteAchat() <= quantiteExecutee) {

                        int quantiteRestante = quantiteExecutee - ordreMeilleurPrix.getQuantiteAchat();
                        ordreMeilleurPrix.setQuantiteAchat(0);

                        // Traitez la quantité restante avec une récursion
                        mettreAJourCarnetOrdres(idSousJacent, meilleurPrix, quantiteRestante, typeTransaction, typeSymbol);
                    } else {
                        ordreMeilleurPrix.setQuantiteAchat(ordreMeilleurPrix.getQuantiteAchat() - quantiteExecutee);

                    }   // Enregistrez les mises à jour dans la base de données
                    ordreRepository.save(ordreMeilleurPrix);
                    optionRepository.save(option);
                }
            }
        }
    }

    @Override
    public int quantiteDisponibleDansCarnetPourPrixDynamique(long idSousJacent, float meilleurPrix, TypeSymbol typeSymbol, TypeTransaction typeTransactionordre  ) {
        int quantiteDisponible = 0;

        if (typeSymbol == TypeSymbol.Action) {
            Action action = actionRepository.findById(idSousJacent)
                    .orElseThrow(() -> new ExpressionException("Action non trouvée avec l'ID : " + idSousJacent));

            // Supposons que le carnet d'ordres de l'action est représenté par la liste ordreList dans l'entité Action
            List<Ordre> carnetOrdres = action.getOrdreList();

            // Parcourez le carnet d'ordres
            for (Ordre ordre : carnetOrdres) {
                // Vérifiez si l'ordre est actif et a un prix égal au meilleur prix
                if (ordre.getPrixLimite() == meilleurPrix) {
                    // Ajoutez la quantité de cet ordre à la quantité disponible
                    if (ordre.getTypetransaction() == TypeTransaction.Buy &&  typeTransactionordre==TypeTransaction.Sell) {
                        quantiteDisponible += ordre.getQuantiteAchat();
                    } else if (ordre.getTypetransaction() == TypeTransaction.Sell &&  typeTransactionordre==TypeTransaction.Buy) {
                        quantiteDisponible += ordre.getQuantiteVente();
                    }                }
            }
        } else if (typeSymbol == TypeSymbol.Option) {
            Option option = optionRepository.findById(idSousJacent)
                    .orElseThrow(() -> new ExpressionException("Option non trouvée avec l'ID : " + idSousJacent));

            // Supposons que le carnet d'ordres de l'option est représenté par la liste ordreOption dans l'entité Option
            List<Ordre> carnetOrdres = option.getOrdreOption();

            // Parcourez le carnet d'ordres
            for (Ordre ordre : carnetOrdres) {
                // Vérifiez si l'ordre est actif et a un prix égal au meilleur prix
                if (ordre.getPrixLimite() == meilleurPrix) {
                    // Ajoutez la quantité de cet ordre à la quantité disponible
                    if (ordre.getTypetransaction() == TypeTransaction.Buy &&  typeTransactionordre==TypeTransaction.Sell) {
                        quantiteDisponible += ordre.getQuantiteAchat();
                    } else if (ordre.getTypetransaction() == TypeTransaction.Sell) {
                        quantiteDisponible += ordre.getQuantiteVente();
                    }                }
            }
        }

        return quantiteDisponible;
    }
    @Override
    public Ordre ajouterOrdreAuCarnet(Ordre ordre, Action action) {
        float prixLimite = ordre.getPrixLimite();
        TypeTransaction typeTransaction = ordre.getTypetransaction();

        // Récupérer le carnet d'ordres associé à l'action
        List<Ordre> carnetOrdres = action.getOrdreList();

        // Vérifier s'il y a des correspondances immédiates dans le carnet d'ordres
        if (correspondanceImmediateDansCarnet(ordre)) {
            // Exécuter les transactions avec les ordres correspondants
            executerTransactions(ordre, carnetOrdres);

            // Retirer les ordres complètement exécutés du carnet d'ordres
            carnetOrdres.removeIf(o -> o.getQuantite() == 0);

        } else {
            // Aucune correspondance immédiate, ajouter l'ordre au carnet d'ordres
            carnetOrdres.add(ordre);
        }

        // Enregistrer les mises à jour dans la base de données
        ordreRepository.save(ordre);
        actionRepository.save(action);

        return ordre;
    }
//mezelt makhdmtch b shih

    ///ki nzid id l action manrch aleh dima twali trajaa faux
   @Override
    public boolean correspondanceImmediateDansCarnet(Ordre ordre) {
        List<Ordre> carnetOrdres = ordreRepository.findAll(); // Remplacez cela par votre logique pour obtenir le carnet d'ordres

        float prixNouvelOrdre = ordre.getPrixLimite();
        TypeTransaction typeTransactionNouvelOrdre = ordre.getTypetransaction();

        for (Ordre ordreCarnet : carnetOrdres) {
            float prixOrdreCarnet = ordreCarnet.getPrixLimite();
            TypeTransaction typeTransactionCarnet = ordreCarnet.getTypetransaction();
            //  long idActionNouvelOrdre  = ordreCarnet.getTypetransaction();


            // Vérifier les conditions de correspondance en comparant le type de transaction
            if((typeTransactionNouvelOrdre == TypeTransaction.Sell && prixNouvelOrdre <= prixOrdreCarnet &&
                    typeTransactionCarnet == TypeTransaction.Buy )
                    ||
                    (typeTransactionNouvelOrdre == TypeTransaction.Buy && prixNouvelOrdre >= prixOrdreCarnet &&
                            typeTransactionCarnet == TypeTransaction.Sell)
            ) {
                return true;
            }
        }

        return false;
    }
    //j ai pas encore tester cette methode
    @Override
    public void executerTransactions(Ordre nouvelOrdre, List<Ordre> carnetOrdres) {
        float prixNouvelOrdre = nouvelOrdre.getPrixLimite();
        TypeTransaction typeTransactionNouvelOrdre = nouvelOrdre.getTypetransaction();
        int quantiteRestante = nouvelOrdre.getQuantite();

        for (Ordre ordreCarnet : carnetOrdres) {
            if (quantiteRestante == 0) {
                break; // Toutes les transactions ont été effectuées
            }

            float prixOrdreCarnet = ordreCarnet.getPrixLimite();
            TypeTransaction typeTransactionCarnet = ordreCarnet.getTypetransaction();

            // Vérifier si le nouvel ordre correspond à l'ordre du carnet
            if ((typeTransactionNouvelOrdre == TypeTransaction.Buy && prixNouvelOrdre >= prixOrdreCarnet) ||
                    (typeTransactionNouvelOrdre == TypeTransaction.Sell && prixNouvelOrdre <= prixOrdreCarnet)) {

                // Calculer la quantité à échanger
                int quantiteTransaction = Math.min(quantiteRestante, ordreCarnet.getQuantite());

                // Mettre à jour les quantités des ordres
                ordreCarnet.setQuantite(ordreCarnet.getQuantite() - quantiteTransaction);
                nouvelOrdre.setQuantite(quantiteRestante - quantiteTransaction);

                // Effectuer la transaction
                // (vous devrez peut-être ajuster cela selon la logique de votre application)
                // transactionService.effectuerTransaction(ordreCarnet, nouvelOrdre, quantiteTransaction);

                // Mettre à jour la quantité restante
                quantiteRestante -= quantiteTransaction;
            }
        }
    }



}

