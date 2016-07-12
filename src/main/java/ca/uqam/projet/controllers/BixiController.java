package ca.uqam.projet.controllers;

import ca.uqam.projet.Utility.Distance;
import ca.uqam.projet.Utility.Global;
import ca.uqam.projet.repositories.BixiRepository;
import ca.uqam.projet.resources.StationBixi;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BixiController {

    @RequestMapping("/bixi")
    public List<StationBixi> findAllNearFoodtruck(
            @RequestParam(value = "x", defaultValue = "") String x,
            @RequestParam(value = "y", defaultValue = "") String y) {
        if (x.equals("") || y.equals("")) {
            return BixiRepository.findAll();
        }

        double coordX, coordY;
        try {
            coordY = Double.parseDouble(y);
            coordX = Double.parseDouble(x);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return removeAllBixiToFar(BixiRepository.findAll(), coordX, coordY);
    }

    //la partis plus bas est à refaire c'est un peu getho
    private List<StationBixi> removeAllBixiToFar(List<StationBixi> liste, double foodTruckX, double foodTruckY) {

        for (int i = 0; i < liste.size(); i++) {
            if (Distance.distance(foodTruckX, foodTruckY, liste.get(i).getX(), liste.get(i).getY(), "K") > Global.DISTANCE_MAXIMAL_DU_FOODTRUCK) {
                liste.set(i, null);
            }
        }
        liste.removeAll(Collections.singleton(null));
        return liste;
    }
}
