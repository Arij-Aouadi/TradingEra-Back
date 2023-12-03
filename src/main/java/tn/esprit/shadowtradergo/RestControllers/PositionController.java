package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.Position;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.Services.Classes.PositionService;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;
import tn.esprit.shadowtradergo.Services.Interfaces.IPositionService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge=3600)
public class PositionController {
    private static final Logger log = LoggerFactory.getLogger(PositionController.class);

    private IPositionService iPositionService ;
    private PositionService positionService ;

    @GetMapping("/Position/showall")
    public List<Position> afficherPosition() {
        List <Position> list= iPositionService.selectAll();
        return list ;
    }

    @PostMapping("/Position/add")
    public Position ajouter(@RequestBody Position position){
        return iPositionService.add(position);
//

    }

    @GetMapping("/afficherPositionAvecId/{idP}")
    public Position afficherPositionAvecIdP(@PathVariable long idP)
    {
        return iPositionService.selectById(idP);
    }

    @PutMapping("/modifierPosition")
    public Position edit(@RequestBody Position position){
        return iPositionService.edit(position);}
    @DeleteMapping ("/deletePositionbyidP/{idP}")
    public void deletebyid (@PathVariable long idP){
        iPositionService.deleteById(idP);}
    @DeleteMapping ("/deletePosition")
    public void deletebyobject (@RequestBody Position position){
        iPositionService.delete(position);}
    @GetMapping("/calculerRevenuTotal")
    public ResponseEntity<Double> calculerRevenu() {
        double totalRevenu = positionService.calculerRevenu();
        return ResponseEntity.ok(totalRevenu);
    }
    @PostMapping("/mise-a-jour-solde")
    public ResponseEntity<String> miseAjourSolde(@RequestBody Ordre ordre) {
        try {
            positionService.miseAjourSolde(ordre);
            return new ResponseEntity<>("Solde mis à jour avec succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la mise à jour du solde : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/current-user-positions")
    public ResponseEntity<List<Position>> getCurrentUserPositions() {
        List<Position> userPositions = positionService.getCurrentUserPositions();

        if (!userPositions.isEmpty()) {
            return ResponseEntity.ok(userPositions);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/count")
    public int getCurrentUserPositionsCount() {
        return positionService.countCurrentUserPositions();
    }

    @GetMapping("/pourcentages-revenu")
    public ResponseEntity<List<Map<String, Object>>> getPourcentagesRevenuParPosition() {
        List<Map<String, Object>> pourcentages = positionService.calculerPourcentageRevenuParPosition();
        return new ResponseEntity<>(pourcentages, HttpStatus.OK);
    }

}
