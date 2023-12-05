package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Game;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.DAO.Repositories.OrdreRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IOrdreService;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class OrdreService implements IOrdreService {

    @Autowired
    private OrdreRepository ordreRepository;

    @Override
    public List<Ordre> selectall() {
        return ordreRepository.findAll();
    }

    @Override
    public Ordre add(Ordre ordre) {
        return ordreRepository.save(ordre);
    }
    @Override
    public Ordre edit(Ordre ordre) {
        return ordreRepository.save(ordre);
    }

    @Override
    public List<Ordre> selectAll() {
        return (List<Ordre>)ordreRepository.findAll();
    }

    @Override
    public Ordre selectById(Long idO) {
        return ordreRepository.findById(idO).get();    }

    @Override
    public void deleteById(Long idO) {ordreRepository.deleteById(idO);

    }



    @Override
    public void delete(Ordre ordre) {

        ordreRepository.delete(ordre);

    }
    @Override
    public Ordre getById(long idO) {
        return ordreRepository.findById(idO).get();
    }

    @Override
    public void deleteAll(List<Ordre> list) {
        ordreRepository.deleteAll(list);
    }



}

