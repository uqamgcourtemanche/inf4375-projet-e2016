/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uqam.projet.tasks;

import ca.uqam.projet.resources.Velo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchVelo {

    
    @Scheduled(cron="*/2 * * * * *")
    public void execute() throws Exception{
        String url = "http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        int responceCode = connection.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responceCode);
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            String response = "";
            
            while ((inputLine = br.readLine()) != null) {
                response = response + (inputLine) + "\n";
            }
            convertCsvToStringList(response);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    private Map convertCsvToStringList(String CsvList){
        Map<String, ArrayList<String>> maps = new HashMap<String, ArrayList<String>>();
        int firstLinePosition = CsvList.indexOf("\n");
        
        String titleString = CsvList.substring(0, firstLinePosition);
        String valueString = CsvList.substring(titleString.length());
        String[] titleList = titleString.split(",");
        String[] valueList = valueString.replaceAll("\n", ",").split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        for(int i = 0 ; i < titleList.length ; i++){
            maps.put(titleList[i], new ArrayList<>());
        }
        for(int i = 0 ; i < valueList.length ; i++){
            maps.get(titleList[i%titleList.length]).add(valueList[i]);
        }
        return maps;
    }
}