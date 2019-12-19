package rpc;

import api.GoogleMapAPIClient;
import entity.Place;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Recommend places to visit.
@WebServlet("/recommendPlaces")
public class RecommendPlaceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        // User Session Check
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            return;
        }
        */

        double lat = Double.parseDouble(request.getParameter("lat"));
        double lon = Double.parseDouble(request.getParameter("lon"));

        GoogleMapAPIClient client = new GoogleMapAPIClient();
        List<Place> nearByPlaces =  client.getNearbyPlaces(lat, lon, null, 10000);

        sortNearByPlaces(nearByPlaces, lat, lon);

        JSONArray array = new JSONArray();
        for (Place place : nearByPlaces){
            array.put(place.toJSONObject());
        }
        RpcHelper.writeJsonArray(response, array);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void sortNearByPlaces(List<Place> places, double lat, double lon) {
        for (Place place : places) {
            double distance = Math.sqrt((place.getLat() - lat) * (place.getLat() - lat) + (place.getLon() - lon) * (place.getLon() - lon));
            double score = place.getRating() * Math.log(place.getUserRatingsTotal()) / Math.log(1 + distance * 10000);
            place.setScore(score);
            place.setDistance(distance);
        }

        places.sort(Place.scoreComparator);
    }
}
