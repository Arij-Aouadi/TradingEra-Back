package tn.esprit.shadowtradergo.Services.Classes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.shadowtradergo.DAO.Entities.Ordre;
import tn.esprit.shadowtradergo.DAO.Entities.Position;
import tn.esprit.shadowtradergo.DAO.Repositories.PositionRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.IPositionService;

import java.util.List;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class PositionService implements IPositionService {
    @Autowired
    private PositionRepository positionRepository;
    @Override
    public List<Position> selectall() {
        return positionRepository.findAll();
    }

    @Override
    public Position add(Position position) {
        return positionRepository.save(position);
    }
    @Override
    public Position edit(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public List<Position> selectAll() {
        return (List<Position>)positionRepository.findAll();
    }

    @Override
    public Position selectById(Long idP) {
        return positionRepository.findById(idP).get();    }

    @Override
    public void deleteById(Long idP) {positionRepository.deleteById(idP);

    }



    @Override
    public void delete(Position position) {

        positionRepository.delete(position);

    }
    @Override
    public Position getById(long idP) {
        return positionRepository.findById(idP).get();
    }

    @Override
    public void deleteAll(List<Position> list) {
        positionRepository.deleteAll(list);
    }

}
