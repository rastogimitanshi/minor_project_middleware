package com.example.sl.mypro;

public class ContentFormat {

    public String time;
    public String extract_date;
    public String extract_address;

    public ContentFormat(String time, String extract_date, String extract_address){
        this.time= time;
        this.extract_date= extract_date;
        this.extract_address= extract_address;

    }
    public String getTime(){
        return time;
    }
    public String getExtract_date(){return extract_date;};
    public String getExtract_address(){return extract_address;};

}
