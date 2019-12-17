package api;

public class GoogleMapAPIClient {
    private static final String HOST = "...";
    private static final String PATH = "*.json";
    private static final String API_KEY = "USE_YOUR_OWN_KEY";

    // TODO: implement three methods - findPlace, getNearbyPlaces, getPlaceDetails

    // TODO: findPlace
    // https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=New%20York&fields=photos,formatted_address,name,rating,opening_hours,geometry&inputtype=textquery&key=AIzaSyA-xhthFchWNRXSkamJ8i2SzoEpTl3Fd1M
    public void findPlace() {
        return;
    }

    // TODO: getNearbyPlaces
    // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&types=tourist_attraction&keyword=cruise&key=AIzaSyA-xhthFchWNRXSkamJ8i2SzoEpTl3Fd1M
    public void getNearbyPlaces() {
        return;
    }

    // TODO: getPlaceDetails
    // https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name,rating,formatted_phone_number&key=YOUR_API_KEY
    // https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&key=AIzaSyA-xhthFchWNRXSkamJ8i2SzoEpTl3Fd1M
    public void getPlaceDetails() {
        return;
    }
}
