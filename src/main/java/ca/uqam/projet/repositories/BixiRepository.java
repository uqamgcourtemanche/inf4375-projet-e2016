package ca.uqam.projet.repositories;

import ca.uqam.projet.Application;
import java.util.List;
import ca.uqam.projet.resources.StationBixi;
import java.util.LinkedList;
import org.springframework.stereotype.Repository;

@Repository
public class BixiRepository {

    public static List<StationBixi> findAllWithinDist(double x, double y, double dist) {
        List<StationBixi> result = null;
        String queryDist = Double.toString(dist);
        String queryPoint = "POINT(" + Double.toString(x) + " " + Double.toString(y) + ")";
        String query = "select * from bixi where "
                + "ST_DWithin( "
                + "ST_SetSRID(ST_MakePoint(bixi.x, bixi.y), 4326), "
                + "ST_GeogFromText('" + queryPoint + "'), " + queryDist + ");";

        System.out.println(query);

        result = Application.app.jdbcTemplate.query(query,
                (rs, rowNum) -> new StationBixi(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("terminal_name"),
                        rs.getInt("nb_bikes"),
                        rs.getInt("nb_empty_docks"),
                        rs.getDouble("x"),
                        rs.getDouble("y"))
        );

        return result;
    }

    public static List<StationBixi> findAll() {
        List<StationBixi> result = Application.app.jdbcTemplate.query("SELECT id, name, terminal_name, nb_bikes, nb_empty_docks, x, y from bixi",
                (rs, rowNum) -> new StationBixi(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("terminal_name"),
                        rs.getInt("nb_bikes"),
                        rs.getInt("nb_empty_docks"),
                        rs.getDouble("x"),
                        rs.getDouble("y"))
        );

        return result;
    }

    public static void update(List<StationBixi> stations) {
        List<StationBixi> toCreate = new LinkedList<>();
        List<StationBixi> toUpdate = new LinkedList<>();

        stations.forEach((s) -> {
            if (!stationExists(s)) {
                toCreate.add(s);
            } else {
                toUpdate.add(s);
            }
        });

        createStations(toCreate);
        updateStations(toUpdate);
    }

    private static boolean stationExists(StationBixi s) {
        List<Integer> result = Application.app.jdbcTemplate.query("SELECT id from bixi where id = ? ", new Object[]{s.getId()},
                (rs, rowNum) -> rs.getInt("id"));

        return !result.isEmpty();
    }

    private static void updateStations(List<StationBixi> stations) {
        List<Object[]> args = new LinkedList<>();
        stations.forEach((s) -> {
            args.add(new Object[]{s.getName(), s.getTerminalName(), s.getNbBikes(), s.getNbEmptyDocks(), s.getX(), s.getY(), s.getId()});
        });

        Application.app.jdbcTemplate.batchUpdate(
                "UPDATE bixi SET "
                + "name = ?, terminal_name = ?, nb_bikes = ?, nb_empty_docks = ?, x = ?, y = ? where id = ?", args);
    }

    private static void createStations(List<StationBixi> stations) {
        if (stations.isEmpty()) {
            return;
        }

        List<Object[]> args = new LinkedList<>();
        stations.forEach((s) -> {
            args.add(new Object[]{s.getId(), s.getName(), s.getTerminalName(), s.getNbBikes(), s.getNbEmptyDocks(), s.getX(), s.getY()});
        });

        Application.app.jdbcTemplate.batchUpdate(
                "INSERT INTO bixi(id, name, terminal_name, nb_bikes, nb_empty_docks, x, y) "
                + "VALUES (?,?,?,?,?,?,?)", args);
    }

}
