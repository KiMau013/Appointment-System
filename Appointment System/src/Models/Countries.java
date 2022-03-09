package Models;

/**
 * Model for Countries
 */
public class Countries {

    private Integer countryId;
    private String country;

    public Countries(){}

    public Countries(Integer countryId, String country){
        this.countryId = countryId;
        this.country = country;
    }

    public Countries(String country) {
    }

    //getters
    public Integer getCountryId(){
        return countryId;
    }
    public String getCountry(){
        return country;
    }

    //setters
    public void setCountryId(Integer countryId){
        this.countryId = countryId;
    }
    public void setCountry(String country){
        this.country = country;
    }

}