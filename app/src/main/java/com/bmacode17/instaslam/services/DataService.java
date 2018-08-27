package com.bmacode17.instaslam.services;

import com.bmacode17.instaslam.models.InstaGalary;

import java.util.ArrayList;

/**
 * Created by User on 15-Apr-18.
 */

public class DataService {
    private static final DataService ourInstance = new DataService();

    public static DataService getInstance() {
        return ourInstance;
    }

    private DataService() {
    }

    public ArrayList<InstaGalary> getGalaryImages(){
        ArrayList<InstaGalary> list = new ArrayList<>();
        list.add(new InstaGalary("Image no. 1","img1"));
        list.add(new InstaGalary("Image no. 2","img2"));
        list.add(new InstaGalary("Image no. 3","img3"));
        list.add(new InstaGalary("Image no. 4","img4"));
        list.add(new InstaGalary("Image no. 5","img5"));
        list.add(new InstaGalary("Image no. 6","img6"));
        return list;
    }
}

