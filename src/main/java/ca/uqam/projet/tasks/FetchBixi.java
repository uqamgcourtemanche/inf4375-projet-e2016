package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.BixiRepository;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FetchBixi {
	
    private static final String URL = "https://montreal.bixi.com/data/bikeStations.xml";
    private static final Logger log = LoggerFactory.getLogger(FetchBixi.class);
    
    //@Scheduled(cron="0 0 0/12 * * *")
    @Scheduled(cron="*/2 * * * * *")
    public void execute(){
        RestTemplate rs = new RestTemplate();
        
        List<HttpMessageConverter<?>> cnv = new LinkedList<>();
        cnv.add(new Jaxb2RootElementHttpMessageConverter());
        
        rs.setMessageConverters(cnv);
        
        StationList sl = rs.getForObject(URL, StationList.class);
        
        List<ca.uqam.projet.resources.StationBixi> models = new LinkedList<>();
        sl.station.stream().forEach((item) -> {
            models.add( new ca.uqam.projet.resources.StationBixi(
                    item.id, item.name, item.terminalName,
                    item.nbBikes, item.nbEmptyDocks, 
                    item.lat, item.longitude
            ));
        });
        
        new BixiRepository().update(models);
    }

    @XmlRootElement(name = "stations")
    static class StationList{
        @XmlElement
        public List<StationBixi> station;
    }
    
    @XmlRootElement(name = "station")
    static class StationBixi{
        @XmlElement(name="id") public int id;
        public String name;
        public String terminalName;
        public String lastCommWithServer;
        public double lat;
        @XmlElement(name="long") public double longitude;
        public boolean installed;
        public boolean locked;
        public boolean temporary;
        @XmlElement(name="public") public boolean isPublic;
        public int nbBikes;
        public int nbEmptyDocks;
        public int lastUpdateTime;
    }
}