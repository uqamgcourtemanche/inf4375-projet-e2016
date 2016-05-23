package ca.uqam.projet.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FetchFoodTruck {
	
    private static final String URL = "http://camionderue.com/donneesouvertes/geojson";
    private static final Logger log = LoggerFactory.getLogger(FetchRandomQuoteTask.class);
    
    //@Scheduled(cron="0 0 0/12 * * *")
    @Scheduled(cron="*/2 * * * * *")
    public void execute(){
        FeatureCollection c = new RestTemplate().getForObject(URL, FeatureCollection.class);
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