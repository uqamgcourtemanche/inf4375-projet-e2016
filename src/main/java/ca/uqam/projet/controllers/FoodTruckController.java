package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.FoodTruckRepository;
import ca.uqam.projet.resources.FoodTruck;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FoodTruckController {

    @RequestMapping("/horaires-camions")
    public List<FoodTruck> findAll(
            @RequestParam(value = "du", defaultValue = "") String start,
            @RequestParam(value = "au", defaultValue = "") String end) {
        if (start.equals("") || end.equals("")) {
            return FoodTruckRepository.findAll();
        }

        Date startDate, endDate;

        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            startDate = parser.parse(start);
            endDate = parser.parse(end);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return FoodTruckRepository.findByDate(startDate, endDate);
    }

}
