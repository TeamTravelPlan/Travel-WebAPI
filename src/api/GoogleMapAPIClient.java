package api;

import entity.Place;
import entity.Place.PlaceBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GoogleMapAPIClient {
    private static final String HOST = "https://maps.googleapis.com";
    private static final String PATH = "/maps/api/place/nearbysearch/json";
    private static final String PHOTO_PATH = "/maps/api/place/photo";
    private static final String DEFAULT_TYPE = "tourist_attraction";
    private static final int DEFAULT_RADIUS = 100;
    private static final String API_KEY = "USE_YOUR_OWN_KEY";

    // TODO: implement three methods - findPlace, getNearbyPlaces, getPlaceDetails

    // TODO: findPlace
    // https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=New%20York&fields=photos,formatted_address,name,rating,opening_hours,geometry&inputtype=textquery&key=AIzaSyA-xhthFchWNRXSkamJ8i2SzoEpTl3Fd1M
    public void findPlace() {
        return;
    }

    // TODO: getNearbyPlaces
    // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&types=tourist_attraction&keyword=cruise&key=AIzaSyA-xhthFchWNRXSkamJ8i2SzoEpTl3Fd1M
    public List<Place> getNearbyPlaces(double lat, double lon, String type, int radius) {
        if (type == null) {
            type = DEFAULT_TYPE;
        }

        if (radius == 0) {
            radius = DEFAULT_RADIUS;
        }

        try {
            type = URLEncoder.encode(type, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String query = String.format("location=%s,%s&radius=%s&type=%s&keyword=%s&key=%s", lat, lon, radius, type, "name", API_KEY);

        String url = HOST + PATH + "?" + query;
        // System.out.println("myprint: " + url);

        StringBuilder responseBody = new StringBuilder();

        try {
            // Create a URLConnection instance that represents a connection to the remote
            // object referred to by the URL. The HttpUrlConnection class allows us to
            // perform basic HTTP requests without the use of any additional libraries.
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Get the status code from an HTTP response message. To execute the request we
            // can use the getResponseCode(), connect(), getInputStream() or
            // getOutputStream() methods.
            int responseCode = connection.getResponseCode();
            System.out.println("Sending requests to url: " + url);
            System.out.println("Response code: " + responseCode);

            if (responseCode != 200) {
                return new ArrayList<>();
            }

            // Create a BufferedReader to help read text from a character-input stream.
            // Provide for the efficient reading of characters, arrays, and lines.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(responseBody.toString());

        try {
            JSONObject obj = new JSONObject(responseBody.toString());
            if (!obj.isNull("results")){
                JSONArray results = obj.getJSONArray("results");
                return getPlaceList(results);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<Place> getPlaceList(JSONArray results)  throws JSONException{
        List<Place> placeList = new ArrayList<>();

        for (int i = 0; i < results.length(); i++){
            JSONObject result = results.getJSONObject(i);

            PlaceBuilder builder = new PlaceBuilder();

            if (!result.isNull("name")){
                builder.setName(result.getString("name"));
            }

            if (!result.isNull("geometry")){
                JSONObject geometry = result.getJSONObject("geometry");
                if (!geometry.isNull("location")) {
                    JSONObject location = geometry.getJSONObject("location");

                    builder.setLat(location.getDouble("lat"));
                    builder.setLon(location.getDouble("lng"));
                }
            }

            if (!result.isNull("rating")){
                builder.setRating(result.getDouble("rating"));
            }

            if (!result.isNull("place_id")){
                builder.setPlaceId(result.getString("place_id"));
            }

            if (!result.isNull("user_ratings_total")){
                builder.setUserRatingsTotal(result.getInt("user_ratings_total"));
            }

            builder.setImageUrl(getImageUrl(result));

            placeList.add(builder.build());
        }
        return placeList;
    }

    private String getImageUrl(JSONObject result) throws JSONException {
        if (result.isNull("photos")){
            return "";
        }

        JSONArray array = result.getJSONArray("photos");
        if (array.length() <= 0) {
            return "";
        }

        JSONObject image = array.getJSONObject(0);
        String photoReference = image.getString("photo_reference");
        String photoQuery = String.format("maxwidth=400&photoreference=%s&key=%s", photoReference, API_KEY);
        return HOST + PHOTO_PATH + "?" + photoQuery;

    }


    // TODO: getPlaceDetails
    // https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number&key=YOUR_API_KEY
    // https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&key=AIzaSyA-xhthFchWNRXSkamJ8i2SzoEpTl3Fd1M
    public void getPlaceDetails() {
        return;
    }

    public static void main(String[] args) {
        GoogleMapAPIClient client = new GoogleMapAPIClient();

        List<Place> places = client.getNearbyPlaces(-33.8670522, 151.1957362, null, DEFAULT_RADIUS);
        System.out.println("places length: " + places.size());
        System.out.println("places name: " + places.get(0).getName());
        System.out.println("places lat: " + places.get(0).getLat());
        System.out.println("places lon: " + places.get(0).getLon());
        System.out.println("places rating: " + places.get(0).getRating());
        System.out.println("places image url: " + places.get(0).getImageUrl());
        System.out.println("places id: " + places.get(0).getPlaceId());
        System.out.println("places rating total: " + places.get(0).getUserRatingsTotal());
    }
}
