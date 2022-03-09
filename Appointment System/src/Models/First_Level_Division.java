package Models;

/**
 * Model for First_Level_Division
 */
public class First_Level_Division {

    private Integer divisionId, countryId;
    private String division;

    public First_Level_Division(){}

    public First_Level_Division(String division){
        this.division = division;
    }

    //getters
    public Integer getDivisionId(){
        return divisionId;
    }
    public String getDivision(){
        return division;
    }
    public Integer getCountryId(){
        return countryId;
    }

    //setters
    public void setDivisionId(Integer divisionId){
        this.divisionId = divisionId;
    }
    public void setDivision(String division){
        this.division = division;
    }
    public void setCountryId(Integer countryId){
        this.countryId = countryId;
    }

    public String toString(){
        return (division);
    }
}
