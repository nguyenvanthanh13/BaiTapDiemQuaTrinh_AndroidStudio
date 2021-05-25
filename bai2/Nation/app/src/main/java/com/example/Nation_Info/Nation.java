package com.example.Nation_Info;

public class Nation {
    private String countryName;
    private String countryCode;
    private String population;
    private String areaInSqKm;

    public Nation() {}

    public Nation(String countryName, String currencyCode, String population, String areaInSqKm) {
        this.countryName = countryName;
        this.countryCode = currencyCode;
        this.population = population;
        this.areaInSqKm = areaInSqKm;

    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public static int nameInc(Nation a, Nation b){
        return a.getCountryName().compareTo(b.getCountryName());
    }

    public static int nameDec(Nation a, Nation b){
        return a.getCountryName().compareTo(b.getCountryName());
    }

    @Override
    public String toString() {
        return  countryName ;
    }
}