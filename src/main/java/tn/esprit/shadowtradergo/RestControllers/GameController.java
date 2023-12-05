package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.Services.Interfaces.IGameService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class GameController {
    private IGameService iGameService;
    /*
    @GetMapping("/{id}/historique-achat-vente")

    public List<Ordre> getHistoriqueAchatVente(@PathVariable Long id) {
        List<Ordre> historique = iGameService.getHistoriqueAchatVente(id);
      return  historique;
    }*/





}
