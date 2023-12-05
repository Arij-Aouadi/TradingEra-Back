package tn.esprit.shadowtradergo.RestControllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.Services.Interfaces.IUserService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge=3600)

public class UserController {
    private IUserService iUserService;
    @GetMapping("/admins/user/showall")
    public List<User> afficherUser() {

        List<User> list = iUserService.selectAll();
        return list;
    }

    @GetMapping("/afficherUserAvecId/{id}")
    public User afficherUserAvecId(@PathVariable long id) {
        return iUserService.selectById(id);

    }

    @PutMapping ("/modifierUser")
    public User edit(@RequestBody User user){
        return iUserService.edit(user);}
    @DeleteMapping ("/deleteUserbyid/{id}")
    public void deletebyid (@PathVariable long id){
        iUserService.deleteById(id);}
    @DeleteMapping ("/deleteUser")
    public void deletebyobject (@RequestBody User user){
        iUserService.delete(user);}

    @GetMapping("/currentUser")
    public  String current (){
        return iUserService.getCurrentUser().getUsername();
    }

    /*@PutMapping("/assignAccountToUser/{id}/{num}")
    public User assignSkierToPiste(@PathVariable long id, @PathVariable int num){
        return iUserService.assignAccountToUser(id,num);
    }*/
    @GetMapping("/rankuser")
    public List<User> getRankedPlayers() {
        return  iUserService.getRankedPlayers();
    }




}
