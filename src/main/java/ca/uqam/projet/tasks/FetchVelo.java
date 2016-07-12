package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.VeloRepository;
import ca.uqam.projet.resources.Velo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchVelo {

    /*
    * C'est un peu getho cette classe
     */
    @Scheduled(cron = "*/2 * * * * *")
    public void execute() throws Exception {
        String url = "http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        //on pourais faire dequoi avec cette variable ou l'enlever
        int responceCode = connection.getResponseCode();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            String value = "";
            String header;

            header = br.readLine();

            while ((inputLine = br.readLine()) != null) {
                value = value + (inputLine) + "\n";
            }
            ArrayList<Velo> listeVelo = convertListToListVelo(convertCsvToStringList(header, value));
            VeloRepository.update(listeVelo);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    ///
    // Take two list, one that is the header of the csv file 
    // and the other is the content of the csv file
    //
    // Return a map of all key-value
    //
    // difficile a comprendre, le code est un peu getho
    ////
    private Map convertCsvToStringList(String header, String CsvList) {
        Map<String, ArrayList<String>> maps = new HashMap<>();

        String[] titleList = header.split(",");
        //regex qui prend les , qui ne sont pas entre ""
        String[] valueList = CsvList.replaceAll("\\r?\\n", ",").split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);

        //remplis l'array pour les titres
        for (String titleList1 : titleList) {
            maps.put(titleList1, new ArrayList<>());
        }
        //remplis l'array pour les valeurs
        for (int i = 0; i < valueList.length; i++) {
            maps.get(titleList[i % titleList.length]).add(valueList[i]);
        }
        return maps;
    }

    ///
    // convert a map into a list of velo
    // difficile a comprendre, le code est un peu getho
    ///
    private ArrayList<Velo> convertListToListVelo(Map map) {
        ArrayList<Velo> listVelo = new ArrayList<>();
        for (int i = 1; i < ((ArrayList) map.get("INV_ID")).size(); i++) {
            if (!((ArrayList) map.get("INV_ID")).get(i).equals("")) {
                Velo velo = new Velo();
                velo.setId(((ArrayList) map.get("INV_ID")).get(i).toString());
                velo.setY(Double.parseDouble(((ArrayList) map.get("LONG")).get(i).toString()));
                velo.setX(Double.parseDouble(((ArrayList) map.get("LAT")).get(i).toString()));
                listVelo.add(velo);
            }
        }
        return listVelo;
    }
}
