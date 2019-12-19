package entity;

public class Place {
    // Fill with location info, name, poi, type, image, etc.
    private String name;
    private double rating;
    private String imageUrl;
    private String placeId;
    private int userRatingsTotal;

    private Place(PlaceBuilder builder){
        this.name = builder.name;
        this.rating = builder.rating;
        this.imageUrl = builder.imageUrl;
        this.placeId = builder.placeId;
        this.userRatingsTotal = builder.userRatingsTotal;
    }

    public String getName() {
        return name;
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

    public static class PlaceBuilder {
        private String name;
        private double rating;
        private String imageUrl;
        private String placeId;
        private int userRatingsTotal;

        public void setName(String name){
            this.name = name;
        }

        public void setRating(double rating){
            this.rating = rating;
        }

        public void setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public void setUserRatingsTotal(int userRatingsTotal) {
            this.userRatingsTotal = userRatingsTotal;
        }

        public Place build() {
            return new Place(this);
        }
    }
}
