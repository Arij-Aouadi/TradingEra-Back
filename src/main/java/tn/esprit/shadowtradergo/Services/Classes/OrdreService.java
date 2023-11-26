package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.*;
import tn.esprit.shadowtradergo.DAO.Repositories.ActionRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OrdreRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class OrdreService implements IOrdreService {

    @Autowired
    private OrdreRepository ordreRepository;
    @Autowired
    private ActionRepository actionRepository;

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
        return (List<Ordre>) ordreRepository.findAll();
    }

    @Override
    public Ordre selectById(Long idO) {
        return ordreRepository.findById(idO).get();
    }

    @Override
    public void deleteById(Long idO) {
        ordreRepository.deleteById(idO);

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

    /////////////////////////////////////:
    @Override

    public Ordre passerOrdre(Ordre ordre) {
        // Logique de traitement de l'ordre (vérifications, validations, etc.)
        if (ordre.getTypetransaction() == TypeTransaction.Sell || ordre.getTypetransaction() == TypeTransaction.Buy) {
            Action action = ordre.getAction();

            if (ordre.getTypeordre() == TypeOrdre.Marché) {
                long idAction = action.getIdAction();  // Obtenez l'id de l'objet Action

                return executerOrdreMarketOrder(ordre, idAction);
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
    @Override
    public Ordre executerOrdreMarketOrder(Ordre ordre, long idAction) {
        Action action = actionRepository.findById(idAction).orElse(null);

        ///     float meilleurPrix = determinerMeilleurPrix(action, ordre.getTypetransaction()); ///lin tekhdmha arij
        float meilleurPrix = 30;
        TypeTransaction typeTransaction = ordre.getTypetransaction();
        // Vérifiez si la quantité à exécuter est positive
        if (ordre.getQuantite() > 0) {
            // Obtenez la quantité disponible au meilleur prix
            int quantiteDisponible = quantiteDisponibleDansCarnetPourPrixDynamique(idAction, meilleurPrix);

            // Vérifiez si la quantité disponible est suffisante
            if (quantiteDisponible >= ordre.getQuantite()) {
                // Exécutez complètement l'ordre car la quantité disponible est suffisante
                ordre.setPrixLimite(meilleurPrix);
                ordre.setDateOrdre(new Date());
                ordre.setStatut(TypeStatut.ferme);
                ordre.setDateAchatVente(new Date());

                // Enregistrez les mises à jour dans la base de données
                ordreRepository.save(ordre);
                actionRepository.save(action);

                // La quantité correspondante est retirée du carnet d'ordres
                mettreAJourCarnetOrdres(idAction, meilleurPrix, ordre.getQuantite(), typeTransaction);
            } else if (quantiteDisponible > 0) {
                // Exécutez partiellement l'ordre car la quantité disponible est partielle
                ordre.setPrixLimite(meilleurPrix);
                ordre.setDateOrdre(new Date());
                ordre.setStatut(TypeStatut.partiel);
                ordre.setDateAchatVente(new Date());

                // Mise à jour de la quantité restante dans l'ordre
                int quantiteRestante = ordre.getQuantite() - quantiteDisponible;
                ordre.setQuantite(quantiteRestante);

                // Enregistrez les mises à jour dans la base de données
                ordreRepository.save(ordre);
                actionRepository.save(action);

                // La quantité exécutée est retirée du carnet d'ordres
                mettreAJourCarnetOrdres(idAction, meilleurPrix, quantiteDisponible, typeTransaction);
                //    jarrbhtha fibeli bech tsalahli hkeyt  l ordre li tzed yekhi chaaaay
                //    if (ordre.getQuantite() - quantiteDisponible > 0) {

                // Répétez le processus avec la nouvelle quantité restante
                executerOrdreMarketOrder(ordre, idAction);
                //    }
            }
        }

        return ordre;
    }

    //////////////////mettre a jour
    @Override
    public void mettreAJourCarnetOrdres(long idAction, float meilleurPrix, int quantiteExecutee, TypeTransaction typeTransaction) {
        // Supposons que le carnet d'ordres de l'action est représenté par la liste ordreList dans l'entité Action
        Action action = actionRepository.findById(idAction)
                .orElseThrow(() -> new ExpressionException("Action non trouvée avec l'ID : " + idAction));

        List<Ordre> carnetOrdres = action.getOrdreList();

        // Recherchez l'ordre correspondant au meilleur prix
        Ordre ordreMeilleurPrix = null;

        for (Ordre o : carnetOrdres) {
            if (o.getPrixLimite() == meilleurPrix) {
                ordreMeilleurPrix = o;
                break;  // Sortez de la boucle dès que l'ordre correspondant est trouvé
            }
        }

  /*      METHODE HEDHI tekhdm b shih just chnjrb wahda ashl
        if (ordreMeilleurPrix != null && quantiteExecutee > 0) {

            if (ordreMeilleurPrix.getQuantite() <= quantiteExecutee) {
                // L'ordre peut être complètement exécuté
                int quantiteRestante = quantiteExecutee - ordreMeilleurPrix.getQuantite();
                ordreMeilleurPrix.setQuantite(0);

                // Traitez la quantité restante avec une récursion
                mettreAJourCarnetOrdres(idAction, meilleurPrix, quantiteRestante, typeTransaction);
            } else {
                // Exécution partielle de l'ordre
                ordreMeilleurPrix.setQuantite(ordreMeilleurPrix.getQuantite() - quantiteExecutee);
            }

            // Enregistrez les mises à jour dans la base de données
            ordreRepository.save(ordreMeilleurPrix);
            actionRepository.save(action);
        }*/
        // Mise à jour de la quantité exécutée sur l'ordre correspondant au meilleur prix
        if (ordreMeilleurPrix != null && quantiteExecutee > 0) {
            if (typeTransaction == TypeTransaction.Buy) {
                // Pour un ordre d'achat, diminuez la quantité
                ordreMeilleurPrix.setQuantite(ordreMeilleurPrix.getQuantite() - quantiteExecutee);
            } else if (typeTransaction == TypeTransaction.Sell) {
                // Pour un ordre de vente, augmentez la quantité
                ordreMeilleurPrix.setQuantite(ordreMeilleurPrix.getQuantite() + quantiteExecutee);
            }

            // Enregistrez les mises à jour dans la base de données
            ordreRepository.save(ordreMeilleurPrix);
            actionRepository.save(action);
        }
    }

    //////////////////////::
    @Override
    public int quantiteDisponibleDansCarnetPourPrixDynamique(long idAction, float meilleurPrix) {
        Action action = actionRepository.findById(idAction)
                .orElseThrow(() -> new ExpressionException("Action non trouvée avec l'ID : " + idAction));
        int quantiteDisponible = 0;

        // Supposons que le carnet d'ordres de l'a
        // ction est représenté par la liste ordreList dans l'entité Action
        List<Ordre> carnetOrdres = action.getOrdreList();

        // Parcourez le carnet d'ordres
        for (Ordre ordre : carnetOrdres) {
            // Vérifiez si l'ordre est actif et a un prix égal au meilleur prix
            if (ordre.getPrixLimite() == meilleurPrix) {
                // Ajoutez la quantité de cet ordre à la quantité disponible
                quantiteDisponible += ordre.getQuantite();
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

    @Override
    public boolean correspondanceImmediateDansCarnet(Ordre ordre) {
        List<Ordre> carnetOrdres = ordreRepository.findAll(); // Remplacez cela par votre logique pour obtenir le carnet d'ordres

        Long idActionNouvelOrdre = ordre.getAction().getIdAction(); // Obtenez l'idAction du nouvel ordre

        float prixNouvelOrdre = ordre.getPrixLimite();
        TypeTransaction typeTransactionNouvelOrdre = ordre.getTypetransaction();

        for (Ordre ordreCarnet : carnetOrdres) {
            Long idActionCarnet = ordreCarnet.getAction().getIdAction(); // Obtenez l'idAction de l'ordre dans le carnet

            // Vérifier si l'idAction du nouvel ordre correspond à un idAction dans le carnet
            if (idActionNouvelOrdre.equals(idActionCarnet)) {
                float prixOrdreCarnet = ordreCarnet.getPrixLimite();
                TypeTransaction typeTransactionCarnet = ordreCarnet.getTypetransaction();

                // Vérifier les conditions de correspondance en comparant le type de transaction
                if((typeTransactionNouvelOrdre == TypeTransaction.Sell && prixNouvelOrdre <= prixOrdreCarnet &&
                        typeTransactionCarnet == TypeTransaction.Buy)
                        ||
                        (typeTransactionNouvelOrdre == TypeTransaction.Buy && prixNouvelOrdre >= prixOrdreCarnet &&
                                typeTransactionCarnet == TypeTransaction.Sell)
                ) {
                    return true;
                }
            }
        }

        return false;
    }


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


