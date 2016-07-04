package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.BixiRepository;
import ca.uqam.projet.repositories.FoodTruckRepository;
import ca.uqam.projet.resources.FoodTruck;
import ca.uqam.projet.resources.StationBixi;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BixiController {
    
    @Autowired
    FoodTruckRepository repository;
    
    @RequestMapping("/bixi")
    public List<StationBixi> findAll(
            @RequestParam(value="foodtruck", defaultValue="") String ftId )
    {
        FoodTruck ft = null;
        
        if( ftId.equals("") ) return null;
        
        int foodTruckId = Integer.parseInt(ftId);
        ft = new FoodTruckRepository().findById(ftId);
        
        return new BixiRepository().getBixiForFoodTruck(ft);
    }
    
}
