package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.FoodTruckRepository;
import ca.uqam.projet.resources.FoodTruck;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FetchFoodTruck {
	
    private static final String URL = "http://camionderue.com/donneesouvertes/geojson";
    private static final Logger log = LoggerFactory.getLogger(FetchFoodTruck.class);
    
    //@Scheduled(cron="0 0 0/12 * * *")
    @Scheduled(cron="*/2 * * * * *")
    public void execute(){
        FeatureCollection c = new RestTemplate().getForObject(URL, FeatureCollection.class);
       
        List<FoodTruck> fts = new LinkedList<>();
        for( Feature ft : c.features ) fts.add(ExtractFoodtruck(ft));

        new FoodTruckRepository().update(fts);
    }
    
    private FoodTruck ExtractFoodtruck(Feature feature){
        List<FoodTruck.Location> locations = new LinkedList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt;
        
        try{
            dt = df.parse(feature.properties.date);
        } catch( ParseException ex )
        {
            throw new RuntimeException(ex);
        }
        
        locations.add(new FoodTruck.Location( 
            dt,
            feature.properties.startTime,
            feature.properties.endTime,
            feature.properties.location,
            new FoodTruck.Coordinates(
                feature.geometry.coordinates[0], 
                feature.geometry.coordinates[1]
            )
        ));
        
        return new FoodTruck( feature.properties.id, feature.properties.truck, locations);
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class FeatureCollection{
        @JsonProperty("type") public String type;
        @JsonProperty("features") Feature features[];
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Feature{
        @JsonProperty("geometry") Geometry geometry;
        @JsonProperty("properties") Properties properties;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Geometry{
        @JsonProperty("type") String type;
        @JsonProperty("coordinates") double coordinates[];
    }    
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Properties{
        @JsonProperty("name") String name;
        @JsonProperty("description") String description;
        @JsonProperty("Date") String date;
        @JsonProperty("Heure_debut") String startTime;
        @JsonProperty("Heure_fin") String endTime;
        @JsonProperty("Lieu") String location;
        @JsonProperty("Camion") String truck;
        @JsonProperty("Truckid") String id;
    }
}