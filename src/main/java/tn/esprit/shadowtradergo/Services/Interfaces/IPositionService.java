package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Position;

import java.util.List;
import java.util.Optional;

public interface IPositionService {
    List<Position> selectall();

    Position add(Position position);

    Position edit(Position position);

    List<Position> selectAll();

    Position selectById(Long idP);

    void deleteById(Long idP);

    void delete(Position position);

    Position getById(long idP);

    void deleteAll(List<Position> list);

    public double calculerProfit(Position position) ;

}
