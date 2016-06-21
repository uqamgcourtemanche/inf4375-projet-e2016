package ca.uqam.projet.controllers;

import ca.uqam.projet.Utility.Distance;
import ca.uqam.projet.repositories.BixiRepository;
import ca.uqam.projet.resources.StationBixi;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BixiController {
    
    @Autowired
    BixiRepository repository;
    
    @RequestMapping("/bixi")
    public List<StationBixi> findAllNearFoodtruck(
            @RequestParam(value="x", defaultValue="") String y,
            @RequestParam(value="y", defaultValue="") String x ) 
    {
        if( x.equals("") || y.equals("") )
        {
            return repository.findAll();
        }
        //il faut que je finise ici
        double coordX, coordY;
        try{
            coordY = Double.parseDouble(y);
            coordX = Double.parseDouble(x);
        } catch( Exception ex )
        {
            throw new RuntimeException(ex);
        }
        
        return removeAllBixiToFar(repository.findAll(), coordX, coordY);
    }
    
    //la partis plus bas est à refaire c'est un peu getho
    private List<StationBixi> removeAllBixiToFar(List<StationBixi> liste, double foodTruckX, double foodTruckY){
        final int DISTANCE_MAXIMAL_DU_FOOD_TRUCK = 200; 
        
        for(int i = 0 ; i < liste.size() ; i++){
            if(Distance.distance(foodTruckX, foodTruckY, liste.get(i).getX(), liste.get(i).getY(), "K")*1000 > DISTANCE_MAXIMAL_DU_FOOD_TRUCK){
                liste.set(i, null);
            }
        }
        liste.removeAll(Collections.singleton(null));
        return liste;
    }
}
