package ca.uqam.projet.repositories;

import ca.uqam.projet.Application;
import java.util.List;
import ca.uqam.projet.resources.FoodTruck;
import ca.uqam.projet.resources.StationBixi;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import org.springframework.stereotype.Repository;


@Repository
public class BixiRepository {
    
    public List<StationBixi> getBixiForFoodTruck(FoodTruck ft)
    {
        return null;
    }
    
    public void update(List<StationBixi> stations)
    {
        List<StationBixi> toCreate = new LinkedList<>();
        List<StationBixi> toUpdate = new LinkedList<>();
        
        stations.forEach((s) -> {
            if( !stationExists(s) ) toCreate.add(s);
            else toUpdate.add(s);
        });
        
        createStations(toCreate);
        updateStations(toUpdate);
    }
    
    private boolean stationExists(StationBixi s)
    {
        List<Integer> result = Application.app.jdbcTemplate.query("SELECT id from bixi where id = ? ", new Object[]{ s.getId() },
                 (rs, rowNum) -> rs.getInt("id")  );
        
        return !result.isEmpty();
    }
    
    private void updateStations(List<StationBixi> stations)
    {
        List<Object[]> args = new LinkedList<>();
        stations.forEach((s) -> {
            args.add(new Object[] { s.getName(), s.getTerminalName(), s.getNbBikes(), s.getNbEmptyDocks(), s.getX(), s.getY(), s.getId() } );
        });
        
        Application.app.jdbcTemplate.batchUpdate(
                "UPDATE bixi SET " +
                "name = ?, terminal_name = ?, nb_bikes = ?, nb_empty_docks = ?, x = ?, y = ? where id = ?" 
                , args);
    }
    
    private void createStations(List<StationBixi> stations)
    {
        if( stations.isEmpty() ) return;
        
        List<Object[]> args = new LinkedList<>();
        stations.forEach((s) -> {
            args.add(new Object[] { s.getId(), s.getName(), s.getTerminalName(), s.getNbBikes(), s.getNbEmptyDocks(), s.getX(), s.getY() } );
        });
        
        Application.app.jdbcTemplate.batchUpdate(
                "INSERT INTO bixi(id name, terminal_name, nb_bikes, nb_empty_docks, x, y) " +
                "VALUES (?,?,?,?,?,?,?)", args);
    }
    
}
