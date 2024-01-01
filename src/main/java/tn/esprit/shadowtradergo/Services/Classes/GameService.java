package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Game;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.DAO.Repositories.GameRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.OrdreRepository;
import tn.esprit.shadowtradergo.DAO.Repositories.UserRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IGameService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class GameService implements IGameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    OrdreRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Map<String, Object>> getRankedPlayers() {
        List<User> users = userRepository.findAll();

        // Triez les utilisateurs par revenu en ordre décroissant
        users.sort((u1, u2) -> Double.compare(u2.getRevenu(), u1.getRevenu()));

        List<Map<String, Object>> result = new ArrayList<>();

        for (User user : users) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("rank", user.getRank());
            userMap.put("username", user.getUsername());
            userMap.put("revenue", user.getRevenu());

            result.add(userMap);
        }

        return result;
    }




}






