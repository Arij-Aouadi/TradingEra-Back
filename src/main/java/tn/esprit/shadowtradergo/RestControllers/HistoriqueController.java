package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.shadowtradergo.DAO.Entities.Historique;
import tn.esprit.shadowtradergo.Services.Interfaces.IHistoriqueService;

import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge=3600)
public class HistoriqueController {
    @Autowired
    private IHistoriqueService ihistoriqueService;

    @GetMapping("/historique")
    public List<Historique> TableauHistorique(){
       return ihistoriqueService.getTableauHistorique();}

}
