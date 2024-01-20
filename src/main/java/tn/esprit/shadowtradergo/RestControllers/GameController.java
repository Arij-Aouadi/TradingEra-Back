package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.Services.Interfaces.IGameService;
import tn.esprit.shadowtradergo.Services.Interfaces.IUserService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge=3600)

public class GameController {
    private IGameService iGameService;
    @GetMapping("/ranku")
    public List<Map<String, Object>> getRankedPlayers() {

        return  iGameService.getRankedPlayers();
    }





}
