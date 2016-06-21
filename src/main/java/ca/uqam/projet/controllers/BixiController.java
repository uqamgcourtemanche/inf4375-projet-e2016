package ca.uqam.projet.controllers;

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
            if(distFrom(foodTruckY, foodTruckX, liste.get(i).getY(), liste.get(i).getX()) > DISTANCE_MAXIMAL_DU_FOOD_TRUCK){
                System.out.println("foodtruckX : " + foodTruckX + " foodtruckY : " + foodTruckY +
                        " bixiX : " + liste.get(i).getX() + " bixiY : " + liste.get(i).getY());
                System.out.println("Distance : "+distFrom(foodTruckY, foodTruckX, liste.get(i).getY(), liste.get(i).getX()));
                liste.set(i, null);
            }
        }
        liste.removeAll(Collections.singleton(null));
        return liste;
    }
    
    // pris de 
    // http://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java 
    // le 2016-06-20
    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
}
