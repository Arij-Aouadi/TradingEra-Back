package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.Position;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;
import tn.esprit.shadowtradergo.Services.Interfaces.IPositionService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)


public class PositionController {
    private IPositionService iPositionService ;
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
}
