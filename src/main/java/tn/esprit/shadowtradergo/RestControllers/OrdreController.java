package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.*;
import tn.esprit.shadowtradergo.Services.Interfaces.IOptionService;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;

import java.util.List;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class OrdreController {
    private IOrdreService iOrdreService;
    private IOptionService iOptionService;


    @PostMapping("/Ordre/add")
    public Ordre ajouter(@RequestBody Ordre ordre) {
        return iOrdreService.add(ordre);
//

    }

    @GetMapping("/ordre/showall")
    public List<Ordre> afficherOrdre() {

        List<Ordre> list = iOrdreService.selectAll();
        return list;
    }

    @GetMapping("/afficherOrdreAvecId/{idO}")
    public Ordre afficherOrdreAvecIdO(@PathVariable long idO) {
        return iOrdreService.selectById(idO);

    }

    @PutMapping("/modifierOrdre")
    public Ordre edit(@RequestBody Ordre ordre) {
        return iOrdreService.edit(ordre);
    }

    @DeleteMapping("/deleteOrdrebyidO/{idO}")
    public void deletebyid(@PathVariable long idO) {
        iOrdreService.deleteById(idO);
    }

    @DeleteMapping("/deleteOrdre")
    public void deletebyobject(@RequestBody Ordre ordre) {
        iOrdreService.delete(ordre);
    }

    /* @PostMapping("/passerOrdre")
     public ResponseEntity<Ordre> passerOrdre(@RequestBody Ordre ordre) {
         try {
             Ordre ordreExecute = iOrdreService.passerOrdre(ordre);
             return new ResponseEntity<>(ordreExecute, HttpStatus.OK);
         } catch (Exception e) {
             // Gérez l'exception appropriée en fonction de vos besoins
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }*/
    @PostMapping("/passerOrdre")
    public ResponseEntity<Ordre> passerOrdre(@RequestBody Ordre ordre, @RequestParam long actionId) {
        // Logique de traitement de l'ordre (vérifications, validations, etc.)
        if (ordre.getTypetransaction() == TypeTransaction.Sell || ordre.getTypetransaction() == TypeTransaction.Buy) {
            // Utilisez la nouvelle méthode en fournissant l'idAction
            Ordre resultatOrdre = iOrdreService.executerOrdreMarketOrder(ordre);

            // Vérifiez si l'opération a réussi
            if (resultatOrdre != null) {
                return new ResponseEntity<>(resultatOrdre, HttpStatus.OK);
            } else {
                // Gérer le cas où l'objet Action n'est pas trouvé ou d'autres erreurs
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        // Gérer d'autres types d'ordres ou transactions
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
        @GetMapping("/quantiteDisponibleDansCarnet")
        public ResponseEntity<Integer> getQuantiteDisponibleDansCarnet(
                @RequestParam float meilleurPrix,
                @RequestParam TypeSymbol typeSymbol,
                @RequestParam TypeTransaction typetransaction,
                @RequestParam long idSousJacent
        ) {
            try {
                // Utilisez ces paramètres pour construire votre objet Ordre
                Ordre ordre = new Ordre();
                // Initialisez les autres champs de l'ordre...

                // Appeler la méthode quantiteDisponibleDansCarnetPourPrixDynamique du service
                //int quantiteDisponible = iOrdreService.quantiteDisponibleDansCarnetPourPrixDynamique(meilleurPrix, ordre);
                int quantiteDisponible = iOrdreService.quantiteDisponibleDansCarnetPourPrixDynamique(ordre.getIdSousJacent(),  meilleurPrix,  typeSymbol);
                return ResponseEntity.ok(quantiteDisponible);
            } catch (ExpressionException e) {
                // Gérer l'exception si l'Action n'est pas trouvée
                return ResponseEntity.notFound().build();
            }
        }*/
    @PostMapping("/mettreAJourCarnetOrdres")
    public ResponseEntity<String> mettreAJourCarnetOrdres(
            @RequestParam long idSousJacent,
            @RequestParam float meilleurPrix,
            @RequestParam int quantiteExecutee,
            @RequestParam TypeTransaction typeTransaction,
            @RequestParam TypeSymbol typeSymbol) {

        try {
            // Appeler la méthode mettreAJourCarnetOrdres du service
            iOrdreService.mettreAJourCarnetOrdres(idSousJacent, meilleurPrix, quantiteExecutee, typeTransaction, typeSymbol);

            // Retourner une réponse réussie
            return ResponseEntity.ok("Carnet d'ordres mis à jour avec succès");

        } catch (Exception e) {
            // Gérer d'autres exceptions selon vos besoins
            return ResponseEntity.status(500).body("Une erreur s'est produite lors de la mise à jour du carnet d'ordres");
        }
    }

    /*  @PostMapping("/executerOrdreMarketOrder")
      public ResponseEntity<Ordre> executerOrdreMarketOrder(@RequestBody Ordre ordre) {
          try {
              Ordre ordreExecute = iOrdreService.executerOrdreMarketOrder(ordre);
              return ResponseEntity.ok(ordreExecute);
          } catch (ExpressionException e) {
              // Gérer l'exception si l'Action n'est pas trouvée
              return ResponseEntity.notFound().build();
          }
      }*/
 /* @PostMapping("/executeOrder/{actionId}")
  public ResponseEntity<String> executeOrder(@PathVariable long actionId, @RequestBody Ordre ordre) {
      // Assign the Action to the Ordre using the provided actionId
      ordre = iOrdreService.AssignOrdretoidAction(ordre,actionId);
      // Execute the market order
      ordre = iOrdreService.executerOrdreMarketOrder(ordre);

      // Return success response or handle errors accordingly
      return ResponseEntity.ok("Order executed successfully.");
  }*/
    @PostMapping("/executeOrder/{actionId}/{optionId}")
    public ResponseEntity<String> executeOrder(
            @PathVariable(required = false) Long actionId,
            @PathVariable(required = false) Long optionId,
            @RequestBody Ordre ordre) {

        if (actionId != null) {
            // Assign the Action to the Ordre using the provided actionId
            ordre = iOrdreService.AssignOrdretoidAction(ordre, actionId);
            // Execute the market order for action
            ordre = iOrdreService.executerOrdreMarketOrder(ordre);
        } else if (optionId != null) {
            // Assign the Option to the Ordre using the provided optionId
            ordre = iOrdreService.AssignOptionToOrdre(ordre, optionId);
            // Execute the market order for option
            ordre = iOrdreService.executerOrdreMarketOrder(ordre);
        } else {
            // Handle the case when neither actionId nor optionId is provided
            return ResponseEntity.badRequest().body("Invalid request. Please provide either actionId or optionId.");
        }

        // Return success response or handle errors accordingly
        return ResponseEntity.ok("Order executed successfully.");
    }

    @PostMapping("/correspondance")
    public ResponseEntity<Boolean> verifierCorrespondanceImmediate(@RequestBody Ordre ordre) {
        try {
            // Appel de la méthode de service pour vérifier la correspondance immédiate dans le carnet d'ordres
            boolean correspondance = iOrdreService.correspondanceImmediateDansCarnet(ordre);
            return new ResponseEntity<>(correspondance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/quantiteDisponibleDansCarnet")
    public ResponseEntity<Integer> quantiteDisponibleDansCarnet(
            @RequestParam long idSousJacent,
            @RequestParam float meilleurPrix,
            @RequestParam TypeSymbol typeSymbol,
            @RequestParam TypeTransaction typeTransactionordre) {
        try {
            // Appeler la méthode quantiteDisponibleDansCarnetPourPrixDynamique du service
            int quantiteDisponible = iOrdreService.quantiteDisponibleDansCarnetPourPrixDynamique(idSousJacent, meilleurPrix, typeSymbol, typeTransactionordre);

            return ResponseEntity.ok(quantiteDisponible);
        } catch (ExpressionException e) {
            // Gérer l'exception si l'Action ou l'Option n'est pas trouvée
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/assignOrdreToAction/{idAction}")
    public Ordre assignOrdreToAction(@RequestBody Ordre ordre,
                                     @PathVariable("idAction") long idAction){
        return  iOrdreService.AssignOrdretoidAction(ordre,idAction);

    }
    @PutMapping("/assignOrdreToOption/{idOption}")
    public Ordre assignOrdreToOption(@RequestBody Ordre ordre,
                                     @PathVariable("idOption") long idOption


    ) {
        return iOrdreService.AssignOptionToOrdre(ordre, idOption);
    }


}
 /*   @GetMapping("/availableQuantity/{orderId}/{meilleurPrix}")
    public ResponseEntity<Integer> getAvailableQuantity(@PathVariable Long orderId, @PathVariable float meilleurPrix) {
        try {
            // Retrieve the order from the database
            Ordre order = iOrdreService.getById(orderId);

            // Check if the order exists
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Call the method to get the available quantity
            int availableQuantity = iOrdreService.quantiteDisponibleDansCarnetPourPrixDynamique(order, meilleurPrix);

            // Return the result
            return new ResponseEntity<>(availableQuantity, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}*/

