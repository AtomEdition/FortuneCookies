package com.atomEdition.FortuneCookies.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FruityDevil on 15.12.14.
 */
public class Prophecy {
    private Date date;
    private String prophecy;
    private Integer prophecyType;

    public Prophecy(Date date, String prophecy, Integer prophecyType) {
        this.date = date;
        this.prophecy = prophecy;
        this.prophecyType = prophecyType;
    }

    public Prophecy(String input) {
        String[] inputArray = input.split("\\#");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            this.date = simpleDateFormat.parse(inputArray[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.prophecy = inputArray[1];
        this.prophecyType = Integer.parseInt(inputArray[2]);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProphecy() {
        return prophecy;
    }

    public void setProphecy(String prophecy) {
        this.prophecy = prophecy;
    }

    public Integer getProphecyType() {
        return prophecyType;
    }

    public void setProphecyType(Integer prophecyType) {
        this.prophecyType = prophecyType;
    }

    public String getStringToSave(){
        String str = "";
        str += new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.date);
        str += "#" + this.prophecy + "#";
        str += this.prophecyType;
        return str;
    }

    public String getDateToShow(){
        String str = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.date);
//        str += this.date.getDate() + "." + this.date.getMonth() + "." + (this.date.getYear() + 1900) +
//                ", " + this.date.getHours() + ":" + this.date.getMinutes() + ":" + this.date.getSeconds() + "\n";
        return str;
    }
}
