package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.Action;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.Services.Interfaces.IActionService;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge=3600)
public class ActionController {
    private IActionService iActionService ;
    @PostMapping("/Action/add")
    public Action ajouter(@RequestBody Action action){
        return iActionService.add(action);
//

    }
    @GetMapping("/action/showall")
    public List<Action> afficherAction() {
        List <Action> list= iActionService.selectAll();
        return list ;
    }

    @GetMapping("/afficherActionAvecId/{idA}")
    public Action afficherActionAvecId(@PathVariable long idA)
    {
        return iActionService.selectById(idA);

    }

    @PutMapping("/modifierAction")
    public Action edit(@RequestBody Action action){
        return iActionService.edit(action);}
    @DeleteMapping ("/deleteActionbyidA/{idA}")
    public void deletebyid (@PathVariable long idA){
        iActionService.deleteById(idA);}
    @DeleteMapping ("/deleteAction")
    public void deletebyobject (@RequestBody Action action){
        iActionService.delete(action);}

}
