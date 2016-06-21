package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.BixiRepository;
import ca.uqam.projet.repositories.FoodTruckRepository;
import ca.uqam.projet.resources.FoodTruck;
import ca.uqam.projet.resources.StationBixi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public List<StationBixi> findAll(
            @RequestParam(value="x", defaultValue="") String x,
            @RequestParam(value="y", defaultValue="") String y ) 
    {
        System.out.println(x);
        System.out.println(y);
        if( x.equals("") || y.equals("") )
        {
            return repository.findAll();
        }
        
        double coordX, coordY;
        
        try{
            coordX = Double.parseDouble(x);
            coordY = Double.parseDouble(y);
        } catch( Exception ex )
        {
            throw new RuntimeException(ex);
        }
        
        return repository.findAll();
    }
    
}
