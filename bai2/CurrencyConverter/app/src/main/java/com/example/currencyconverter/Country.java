package com.example.currencyconverter;

public class Country{
    String continent;
    String capital;
    String languages;
    int geonameId;
    double south;
    String isoAlpha3;
    double north;
    String fipsCode;
    String population;
    double east;
    String isoNumeric;
    String areaInSqKm;
    String countryCode;
    double west;
    String countryName;
    String postalCodeFormat;
    String continentName;
    String currencyCode;

    public Country() {
    }

    public Country(String continent, String capital, String languages, int geonameId, double south, String isoAlpha3, double north, String fipsCode, String population, double east, String isoNumeric, String areaInSqKm, String countryCode, double west, String countryName, String postalCodeFormat, String continentName, String currencyCode) {
        this.continent = continent;
        this.capital = capital;
        this.languages = languages;
        this.geonameId = geonameId;
        this.south = south;
        this.isoAlpha3 = isoAlpha3;
        this.north = north;
        this.fipsCode = fipsCode;
        this.population = population;
        this.east = east;
        this.isoNumeric = isoNumeric;
        this.areaInSqKm = areaInSqKm;
        this.countryCode = countryCode;
        this.west = west;
        this.countryName = countryName;
        this.postalCodeFormat = postalCodeFormat;
        this.continentName = continentName;
        this.currencyCode = currencyCode;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public int getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(int geonameId) {
        this.geonameId = geonameId;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPostalCodeFormat() {
        return postalCodeFormat;
    }

    public void setPostalCodeFormat(String postalCodeFormat) {
        this.postalCodeFormat = postalCodeFormat;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}