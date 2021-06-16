package ac.uk.dundee.agile7;

import ac.uk.dundee.agile7.entity.PriceEntry;

/**
 * Data structure class used for returning data out of the REST endpoint
 */
public class PriceEntryOutput {
    public PriceEntryOutput(){}

    private String procedureName;
    private String providerName;

    private String streetAddress;
    private String city;
    private String state;
    private String postcode;
    private double lat;
    private double lng;

    private double distance;
    private double price;

    public PriceEntryOutput(PriceEntry pe, double distance){
        procedureName = pe.getProcedure().getName();
        providerName = pe.getProvider().getName();

        streetAddress = pe.getProvider().getStreetAddress();
        city = pe.getProvider().getCity();
        state = pe.getProvider().getState();
        postcode = pe.getProvider().getPostcode();
        lat = pe.getProvider().getLat();
        lng= pe.getProvider().getLng();

        price = pe.getPrice();
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }
}

