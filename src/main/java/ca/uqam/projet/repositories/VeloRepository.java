package ca.uqam.projet.repositories;

import ca.uqam.projet.Application;
import java.util.List;
import ca.uqam.projet.resources.Velo;
import java.util.LinkedList;
import org.springframework.stereotype.Repository;

@Repository
public class VeloRepository {

    public static List<Velo> findAll() {
        List<Velo> result = Application.app.jdbcTemplate.query("SELECT id, lon, lat from velo",
                (rs, rowNum) -> new Velo(
                        rs.getString("id"),
                        rs.getDouble("lon"),
                        rs.getDouble("lat"))
        );

        return result;
    }

    public static void update(List<Velo> stationsVelo) {
        List<Velo> toCreate = new LinkedList<>();
        List<Velo> toUpdate = new LinkedList<>();

        stationsVelo.forEach((s) -> {
            if (!stationExists(s)) {
                toCreate.add(s);
            } else {
                toUpdate.add(s);
            }
        });

        createStations(toCreate);
        updateStations(toUpdate);
    }

    private static boolean stationExists(Velo s) {
        List<String> result = Application.app.jdbcTemplate.query("SELECT id from velo where id = ? ", new Object[]{s.getId()},
                (rs, rowNum) -> rs.getString("id"));

        return !result.isEmpty();
    }

    private static void updateStations(List<Velo> stations) {
        List<Object[]> args = new LinkedList<>();
        stations.forEach((s) -> {
            args.add(new Object[]{s.getLon(), s.getLat(), s.getId()});
        });

        Application.app.jdbcTemplate.batchUpdate(
                "UPDATE velo SET "
                + "lon = ?, lat = ? where id = ?", args);
    }

    private static void createStations(List<Velo> stations) {
        if (stations.isEmpty()) {
            return;
        }

        List<Object[]> args = new LinkedList<>();
        stations.forEach((s) -> {
            args.add(new Object[]{s.getId(), s.getLon(), s.getLat()});
        });

        Application.app.jdbcTemplate.batchUpdate(
                "INSERT INTO velo(id, lon, lat) "
                + "VALUES (?,?,?)", args);
    }

}
