package ca.uqam.projet.repositories;

import ca.uqam.projet.Application;
import java.util.List;
import ca.uqam.projet.resources.FoodTruck;
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
                 (rs, rowNum) -> rs.getString("id"));
        
        return !result.isEmpty();
    }
    
    private void createLocation(FoodTruck ft, FoodTruck.Location location)
    {
        //todo
    }

}
