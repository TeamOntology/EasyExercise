package sg.edu.ntu.scse.cz2006.ontology.easyexercise.recommendation;

import android.content.Context;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.SportAndFacilityDBHelper;

/**
 * A utility class that generates a list of {@code Facility} based on the conditions given.
 *
 * @author Ma Xinyi
 */
public class FacilityRecommendation {
    private FacilityRecommendation() {
    }

    /**
     * Generates facilities containing the sports selected, sorted by the distance to the location given.
     *
     * @param context     the context in which this method involved
     * @param sports      the sports selected
     * @param coordinates the geographical coordinates of a location
     * @param limit       the number of facilities returned in the list
     * @return a list of sorted facilities containing the sports selected
     */
    public static List<Facility> getFacilitiesBySports(Context context, Collection<Sport> sports, Coordinates coordinates, int limit) {
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        List<Facility> results = manager.getFacilities().stream()
                .filter(facility ->
                        facility.getSports().stream()
                                .anyMatch(sport ->
                                        sports.stream()
                                                .anyMatch(x -> x.getId() == sport.getId())
                                )
                ).sorted(Comparator.comparingDouble(x -> x.getDistance(coordinates)))
                .limit(limit)
                .collect(Collectors.toList());
        manager.closeDatabase();
        return results;
    }

    /**
     * Generates facilities within a certain distance near the location given, sorted by the distance to the location given.
     *
     * @param context     the context in which this method involved
     * @param coordinates the geographical coordinates of a location
     * @param distance    the distance within which the facilities are
     * @param limit       the number of facilities returned in the list
     * @return a list of sorted facilities within the distance near the location
     */
    public static List<Facility> getFacilitiesNearby(Context context, Coordinates coordinates, double distance, int limit) {
        SportAndFacilityDBHelper manager = new SportAndFacilityDBHelper(context);
        manager.openDatabase();
        List<Facility> results = manager.getFacilities().stream()
                .filter(facility ->
                        coordinates.getDistance(facility.getCoordinates()) <= distance)
                .sorted(Comparator.comparingDouble(x -> x.getDistance(coordinates)))
                .limit(limit)
                .collect(Collectors.toList());
        manager.closeDatabase();
        return results;
    }
}
