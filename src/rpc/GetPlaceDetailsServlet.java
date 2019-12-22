package rpc;

import api.GoogleMapAPIClient;
import entity.Place;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Get details about the given place.
@WebServlet("/getPlaceDetails")
public class GetPlaceDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        // User Session Check
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(403);
            return;
        }
        */

        String placeId = "ChIJVVUVkTeuEmsREx6jD41IUGw";

        GoogleMapAPIClient client = new GoogleMapAPIClient();
        Place placeDetails =  client.getPlaceDetails(placeId);

        JSONArray array = new JSONArray();

        array.put(placeDetails.toJSONObject());

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
