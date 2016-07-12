package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.BixiRepository;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FetchBixi {

    private static final String URL = "https://montreal.bixi.com/data/bikeStations.xml";

    //@Scheduled(cron="0 0 0/12 * * *")
    @Scheduled(cron = "*/2 * * * * *")
    public void execute() {
        RestTemplate rs = new RestTemplate();

        List<HttpMessageConverter<?>> cnv = new LinkedList<>();
        cnv.add(new Jaxb2RootElementHttpMessageConverter());

        rs.setMessageConverters(cnv);

        StationList sl = rs.getForObject(URL, StationList.class);

        List<ca.uqam.projet.resources.StationBixi> models = new LinkedList<>();
        sl.station.stream().forEach((item) -> {
            models.add(new ca.uqam.projet.resources.StationBixi(
                    item.id, item.name, item.terminalName,
                    item.nbBikes, item.nbEmptyDocks,
                    item.longitude, item.lat
            ));
        });

        BixiRepository.update(models);
    }

    @XmlRootElement(name = "stations")
    static class StationList {

        @XmlElement
        public List<StationBixi> station;
    }

    @XmlRootElement(name = "station")
    static class StationBixi {

        @XmlElement(name = "id")
        public int id;
        public String name;
        public String terminalName;
        public String lastCommWithServer;
        public double lat;
        @XmlElement(name = "long")
        public double longitude;
        public boolean installed;
        public boolean locked;
        public boolean temporary;
        @XmlElement(name = "public")
        public boolean isPublic;
        public int nbBikes;
        public int nbEmptyDocks;
        public int lastUpdateTime;
    }

    //<iframe width="700" height="400" src="http://donnees.ville.montreal.qc.ca/dataset/arceaux-velos/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/view/dc59d539-6276-4f02-b2b5-6ffbb26ca007" frameBorder="0"></iframe>
}
