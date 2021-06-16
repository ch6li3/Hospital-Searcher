package ac.uk.dundee.agile7.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Database entity to store provider data. - name, ID, address, coordinates
 */
@Entity
public class Provider {
    @Id
    @GeneratedValue
    private Long id;

    private int providerID;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String postcode;
    private double lat;
    private double lng;

    public Provider(){}

    public Provider(String name) {
        this.name = name;
    }



    public Provider(String name, int providerID, String streetAddress, String city, String state, String postcode, double lat, double lng) {

        this.name = name;
        this.providerID = providerID;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.lat = lat;
        this.lng = lng;
    }


    public String getName() {
        return name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Long getId() {
        return id;
    }

    public int getProviderID() {
        return providerID;
    }

    public void setProviderID(int providerID) {
        this.providerID = providerID;
    }
}
