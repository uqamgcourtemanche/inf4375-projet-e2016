package ca.uqam.projet.repositories;

import ca.uqam.projet.Application;
import java.util.List;
import ca.uqam.projet.resources.FoodTruck;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import org.springframework.stereotype.Repository;

@Repository
public class FoodTruckRepository {
    public void update(List<FoodTruck> foodtrucks )
    {
        for(FoodTruck ft : foodtrucks)
        {
            if( !foodTruckExists(ft) ) createFoodTruck(ft);
            
            for( FoodTruck.Location loc : ft.getLocations() )
            {
                if( !locationExists(ft, loc) ) createLocation(ft, loc);
            }
        }
    }
    
    public FoodTruck findById(int id)
    {   
        FoodTruck ft = null;
        List<FoodTruck> result = Application.app.jdbcTemplate.query("SELECT id, name from foodtrucks",
                 (rs, rowNum) ->  new FoodTruck(rs.getString("id"), rs.getString("name"), new LinkedList<>()));
    }   
    
    public List<FoodTruck> findAll()
    {
        List<FoodTruck> result = Application.app.jdbcTemplate.query("SELECT id, name from foodtrucks",
                 (rs, rowNum) ->  new FoodTruck(rs.getString("id"), rs.getString("name"), new LinkedList<>()));
        
        return result;
    }
    
    public List<FoodTruck> findByDate(Date start, Date end)
    {
        List<FoodTruck> result = Application.app.jdbcTemplate.query("SELECT id, name from foodtrucks",
                 (rs, rowNum) ->  new FoodTruck(
                         rs.getString("id"), 
                         rs.getString("name"), 
                         findLocations(rs.getString("id"), start, end)));
        
        return result;
    }
    
    private List<FoodTruck.Location> findLocations(String id, Date start, Date end)
    {
        List<FoodTruck.Location> result = Application.app.jdbcTemplate.query(
                "SELECT date, timeStart, timeEnd, description, coord[0] as x, coord[1] as y from truck_location where foodtruck_id = ? and date >= ? and date <= ?",
                new Object[] { id, start, end },
                (rs, rowNum) -> new FoodTruck.Location(
                        rs.getDate("date"), 
                        rs.getTime("timeStart").toString(), 
                        rs.getTime("timeEnd").toString(),  
                        rs.getString("description"), 
                        new FoodTruck.Coordinates(rs.getDouble("x"), rs.getDouble("y"))));
        
        return result;
    }
    
    private boolean foodTruckExists(FoodTruck ft)
    {
        List<String> result = Application.app.jdbcTemplate.query("SELECT id from foodtrucks where id = ? ", new Object[]{ ft.getId() },
                 (rs, rowNum) -> rs.getString("id"));
        
        return !result.isEmpty();
    }
    
    private void createFoodTruck(FoodTruck ft)
    {
        Application.app.jdbcTemplate.update("INSERT INTO foodtrucks(id, name) VALUES (?,?)", new Object[] { ft.getId(), ft.getName()});
    }
    
    private boolean locationExists(FoodTruck ft, FoodTruck.Location location)
    {
        List<String> result = Application.app.jdbcTemplate.query("SELECT foodtruck_id from truck_location where foodtruck_id = ? and date = ?", new Object[]{ ft.getId(), location.getDate() },
                 (rs, rowNum) -> rs.getString("foodtruck_id"));
        
        return !result.isEmpty();
    }
    
    private void createLocation(FoodTruck ft, FoodTruck.Location location)
    {
        Date dateStart, dateEnd;
        
        try{
            dateStart = new java.text.SimpleDateFormat("HH:mm").parse(location.getTimeStart());
            dateEnd = new java.text.SimpleDateFormat("HH:mm").parse(location.getTimeEnd());
        } catch( ParseException ex )
        {
            throw new RuntimeException(ex);
        }
        
        Application.app.jdbcTemplate.update(
                "INSERT INTO truck_location(date, foodtruck_id, timestart, timeend, description, coord) " 
                + "VALUES (?, ?, ?, ?, ?, "
                + "'(" 
                + Double.toString(location.getCoord().getX())
                + "," 
                + Double.toString(location.getCoord().getY())
                + ")'"
                + ")", 
                new Object[] { 
                    location.getDate(), 
                    ft.getId(), 
                    new Time(dateStart.getTime()), 
                    new Time(dateEnd.getTime()),
                    location.getLocationName()
                }
        );
               
    }

}
