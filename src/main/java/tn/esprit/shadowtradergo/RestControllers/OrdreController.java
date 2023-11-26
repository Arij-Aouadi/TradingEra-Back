package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.*;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;
import tn.esprit.shadowtradergo.Services.Interfaces.IUserService;

import java.util.List;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class OrdreController {
    private IOrdreService iOrdreService ;
    @PostMapping("/Ordre/add")
    public Ordre ajouter(@RequestBody Ordre ordre){
        return iOrdreService.add(ordre);
//

    }
    @GetMapping("/ordre/showall")
    public List<Ordre> afficherOrdre() {

        List <Ordre> list= iOrdreService.selectAll();
        return list ;
    }

    @GetMapping("/afficherOrdreAvecId/{idO}")
    public Ordre afficherOrdreAvecIdO(@PathVariable long idO)
    {
        return iOrdreService.selectById(idO);

    }

    @PutMapping("/modifierOrdre")
    public Ordre edit(@RequestBody Ordre ordre){
        return iOrdreService.edit(ordre);}
    @DeleteMapping ("/deleteOrdrebyidO/{idO}")
    public void deletebyid (@PathVariable long idO){
        iOrdreService.deleteById(idO);}
    @DeleteMapping ("/deleteOrdre")
    public void deletebyobject (@RequestBody Ordre ordre){
        iOrdreService.delete(ordre);}

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
            Ordre resultatOrdre = iOrdreService.executerOrdreMarketOrder(ordre, actionId);

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

    @GetMapping("/quantiteDisponibleDansCarnet")
    public ResponseEntity<Integer> getQuantiteDisponibleDansCarnet(@RequestParam long actionId, @RequestParam float meilleurPrix) {
        try {
            // Appeler la méthode quantiteDisponibleDansCarnetPourPrixDynamique du service
            int quantiteDisponible = iOrdreService.quantiteDisponibleDansCarnetPourPrixDynamique(actionId, meilleurPrix);

            return ResponseEntity.ok(quantiteDisponible);
        } catch (ExpressionException e) {
            // Gérer l'exception si l'Action n'est pas trouvée
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/mettreAJourCarnetOrdres")
    public ResponseEntity<String> mettreAJourCarnetOrdres(
            @RequestParam long idAction,
            @RequestParam float meilleurPrix,
            @RequestParam int quantiteExecutee,
            @RequestParam TypeTransaction typeTransaction) {

        try {
            // Appeler la méthode mettreAJourCarnetOrdres du service
            iOrdreService.mettreAJourCarnetOrdres(idAction, meilleurPrix, quantiteExecutee, typeTransaction);

            // Retourner une réponse réussie
            return ResponseEntity.ok("Carnet d'ordres mis à jour avec succès");

        } catch (Exception e) {
            // Gérer d'autres exceptions selon vos besoins
            return ResponseEntity.status(500).body("Une erreur s'est produite lors de la mise à jour du carnet d'ordres");
        }
    }
    @PostMapping("/executerOrdreMarketOrder")
    public ResponseEntity<Ordre> executerOrdreMarketOrder(@RequestBody Ordre ordre, @RequestParam long idAction) {
        try {
            Ordre ordreExecute = iOrdreService.executerOrdreMarketOrder(ordre, idAction);
            return ResponseEntity.ok(ordreExecute);
        } catch (ExpressionException e) {
            // Gérer l'exception si l'Action n'est pas trouvée
            return ResponseEntity.notFound().build();
        }
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
}
