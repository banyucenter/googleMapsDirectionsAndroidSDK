package Utilities;

import java.util.List;

import Models.Route;

/**
 * Created by gerard on 2016-05-22.
 */
public interface MapsActivityInterface {

    // Clear MapsActivity interface from old routes
    void clearPrevious();

    // Change MapsActivity interface to add new routes
    void addNewRoutes(List<Route> route);
}
