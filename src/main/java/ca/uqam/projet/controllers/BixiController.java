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
public class BixiController {
    
    static final double DISTANCE_MAXIMAL_DU_FOOD_TRUCK = 200.0; 
    
    @Autowired
    BixiRepository repository;
    
    @RequestMapping("/bixi")
    public List<StationBixi> findAllNearFoodtruck(
            @RequestParam(value="x", defaultValue="") String x,
            @RequestParam(value="y", defaultValue="") String y ) 
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
        
        System.out.println("Request for bixi");
        
        List<StationBixi> result =  repository.findAllWithinDist(coordX, coordY, BixiController.DISTANCE_MAXIMAL_DU_FOOD_TRUCK);
        System.out.println("Nb resultat " + Integer.toString(result.size()));
        return result;
    }
}
