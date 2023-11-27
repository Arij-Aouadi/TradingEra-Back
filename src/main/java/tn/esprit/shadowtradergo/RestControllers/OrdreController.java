package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Game;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;
import tn.esprit.shadowtradergo.Services.Interfaces.IUserService;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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

}


