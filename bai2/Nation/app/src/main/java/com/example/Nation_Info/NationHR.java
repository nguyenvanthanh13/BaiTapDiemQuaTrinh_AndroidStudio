package com.example.Nation_Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NationHR {
    List<Nation> nationList;

    public NationHR() {
        nationList = new ArrayList<>();
    }

    public NationHR(List<Nation> nationList) {
        this.nationList = nationList;
    }

    public void add(Nation nation) {
        nationList.add(nation);
    }

    public void display() {
        for (Nation e : nationList) {
            System.out.println(e.toString());
        }
    }
    public String getCountryCode(int i) { return  nationList.get(i).getCountryCode(); }

    public String getCountryCode(String s) {
        for (Nation nation : nationList) {
            if ( nation.getCountryName().toLowerCase().indexOf(s.toLowerCase()) != -1 )
                return  nation.getCountryCode();
        }
        return null;
    }

    public String getPopulation(int i) { return nationList.get(i).getPopulation(); }

    public String getArea(int i) { return nationList.get(i).getAreaInSqKm(); }

    public int  getLength() {
        return nationList.size();
    }

    public List<Nation> getNationList() {
        return nationList;
    }

    public void setNationList(List<Nation> nationList) {
        this.nationList = nationList;
    }

    public List<String> getStringName() {
        List<String> list = new ArrayList<>();

        for (Nation nation : nationList) {
            String name = nation.getCountryName();
            list.add(name);
        }

        return  list;
    }

    public String getName(int i) {
        return nationList.get(i).getCountryName();
    }

    public void sortbyName(){
        Collections.sort(nationList, Nation::nameInc);
    }

    public List<String> findNationName(String strName) {
        List<String> arr = new ArrayList<>();
        for (Nation nation : nationList) {
            if ( nation.getCountryName().toLowerCase().indexOf(strName.toLowerCase()) != -1 ) {
                System.out.println("Tim thay " + nation.getCountryName() );
                arr.add(nation.getCountryName());
            }
        }

        sortbyName();

        return arr;
    }

    public Nation getNation(String name) {
        for (Nation nation : nationList) {
            if ( nation.getCountryName().toLowerCase().indexOf(name.toLowerCase()) != -1 ) {
               return nation;
            }
        }

        return null;
    }

}
