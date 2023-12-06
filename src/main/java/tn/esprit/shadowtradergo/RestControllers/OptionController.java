package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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


    @PostMapping("/Option/add")
    public Option ajouter(@RequestBody Option option){
        return iOptionService.add(option);
//

    }
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


    //option princing
    @GetMapping("/option-prices")
    public String getOptionPrices() {
        final String flaskApiUrl = "http://127.0.0.1:5000/option-prices";

        // Make a request to the Flask API
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(flaskApiUrl, String.class);

        // Process the result as needed
        return result;
    }




}
