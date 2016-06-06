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
        List<Integer> result = Application.app.jdbcTemplate.query("SELECT id from bixi where id = ? ", new Object[]{ new Integer(s.getId()) },
                 (rs, rowNum) -> rs.getInt("id")  );
        
        return !result.isEmpty();
    }
    
    private void updateStations(List<StationBixi> stations)
    {
        /*List<Object[]> args = new LinkedList<>();
        stations.forEach((s) -> {
            args.add(new Object[] { s.getId(), s.getName(), s.getTerminalName(), s.getNbBikes(), s.getNbEmptyDocks(), new Double[]{s.getX(), s.getY()} } );
        });
        
        Application.app.jdbcTemplate.batchUpdate(
                "INSERT INTO bixi(id, name, terminalName, nbBikes, nbEmptyDocks, coord) " +
                "VALUES (?,?,?,?,?, ?)", args);*/
    }
    
    private void createStations(List<StationBixi> stations)
    {
        if( stations.isEmpty() ) return;
        
        List<Object[]> args = new LinkedList<>();
        stations.forEach((s) -> {
            args.add(new Object[] { s.getId(), s.getName(), s.getTerminalName(), s.getNbBikes(), s.getNbEmptyDocks(), new Double[]{s.getX(), s.getY()} } );
        });
        
        Application.app.jdbcTemplate.batchUpdate(
                "INSERT INTO bixi(id, name, terminalName, nbBikes, nbEmptyDocks, coord) " +
                "VALUES (?,?,?,?,?, ?)", args);
    }
    
}
