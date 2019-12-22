package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

public class Place {
    // Fill with location info, name, poi, type, image, etc.
    private String name;
    private double lat;
    private double lon;
    private double rating;
    private String imageUrl;
    private String placeId;
    private int userRatingsTotal;
    private double score;
    private double distance;
    private String address;
    private String website;
    private JSONArray openPeriods;

    private Place(PlaceBuilder builder) {
        this.name = builder.name;
        this.address = builder.address;
        this.lat = builder.lat;
        this.lon = builder.lon;
        this.rating = builder.rating;
        this.imageUrl = builder.imageUrl;
        this.placeId = builder.placeId;
        this.userRatingsTotal = builder.userRatingsTotal;
        this.score = builder.score;
        this.distance = builder.distance;
        this.website = builder.website;
        this.openPeriods = builder.openPeriods;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlaceId() {
        return placeId;
    }

    public int getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public double getScore() {
        return score;
    }

    public JSONArray getOpenPeriods() {return openPeriods; }

    public double getDistance() {
        return distance;
    }

    public String getWebsite() {return website; }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("address", address);
            obj.put("lat", lat);
            obj.put("lon", lon);
            obj.put("rating", rating);
            obj.put("imageUrl", imageUrl);
            obj.put("placeId", placeId);
            obj.put("userRatingsTotal", userRatingsTotal);
            obj.put("score", score);
            obj.put("distance", distance);
            obj.put("website", website);
            obj.put("openPeriods", openPeriods);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static class PlaceBuilder {
        private String name;
        private String address;
        private double lat;
        private double lon;
        private double rating;
        private String imageUrl;
        private String placeId;
        private int userRatingsTotal;
        private double score;
        private double distance;
        private String website;
        private JSONArray openPeriods;

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public void setUserRatingsTotal(int userRatingsTotal) {
            this.userRatingsTotal = userRatingsTotal;
        }

        public void setWebsite(String website) {this.website = website; }

        public void setOpenPeriods(JSONArray openPeriods) {this.openPeriods = openPeriods;}

        public Place build() {
            return new Place(this);
        }
    }

    public static Comparator<Place> scoreComparator = (place1, place2) -> {
        if (place1.getUserRatingsTotal() >= 1000 && place2.getUserRatingsTotal() < 1000){
            return -1;
        }

        if (place1.getUserRatingsTotal() < 1000 && place2.getUserRatingsTotal() >= 1000){
            return 1;
        }

        if (place1.getScore() == place2.getScore()) {
            return 0;
        }

        return place1.getScore() > place2.getScore() ? -1 : 1;
    };

}
