package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import tn.esprit.shadowtradergo.DAO.Entities.*;

import tn.esprit.shadowtradergo.DAO.Entities.Option;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.User;

import tn.esprit.shadowtradergo.Services.Interfaces.IOptionService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge=3600)

public class OptionController {

    private IOptionService iOptionService;



    @GetMapping("/Option/Order")
    public List<Option> afficherOption() {

        List <Option> list= iOptionService.selectAll();
        return list ;
    }

    @GetMapping("/afficherOptionAvecId/{id}")
    public Option afficherOptionAvecId(@PathVariable long id)
    {
        return iOptionService.selectById(id);

    }

    @PutMapping("/modifierOption")
    public Option edit(@RequestBody Option option){
        return iOptionService.edit(option);}
    @DeleteMapping ("/deleteOptionbyid/{id}")
    public void deletebyid (@PathVariable long id){
        iOptionService.deleteById(id);}
    @DeleteMapping ("/deleteOption")
    public void deletebyobject (@RequestBody Option option){
        iOptionService.delete(option);}


    @PostMapping("/addOption/{actionId}")
    public ResponseEntity<Option> addOptionToAction(@RequestBody Option option, @PathVariable long actionId) {
        // Call the service to add the Option and associate it with the Action
        Option addedOption = iOptionService.addOption(option, actionId);

        if (addedOption != null) {
            // Return a success response with the added Option
            return ResponseEntity.ok(addedOption);
        } else {
            // Return a not found response if the Action with the given ID is not found
            return ResponseEntity.notFound().build();
        }
    }



        @GetMapping("/calculate-profit-loss")
        public Float calculateProfitOrLoss(
                @RequestParam long optionId,
                @RequestParam TypeOption typeOption,
                @RequestParam TypeTransaction typeTransaction,
                @RequestParam float prixSousJacentExpiration
        ) {

                return  ( iOptionService.CalculerProfitOuPerteOption(optionId,typeOption,typeTransaction,prixSousJacentExpiration));
            }
        }




