package org.example.database.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.example.display.Color;


@XmlRootElement(name = "cdr")
public class CDR {
    private String anum;
    private String bnum;
    private String serviceType;
    private double usage;
    private String startDateTime;

    //default no-arg constructor required for JAXB
    public CDR() {
    }

    public CDR(String anum, String bnum, String serviceType, double usage, String startDateTime) {
        this.anum = anum;
        this.bnum = bnum;
        this.serviceType = serviceType;
        this.usage = usage;
        this.startDateTime = startDateTime;
    }

    @XmlElement(name = "anum")
    public String getAnum() {
        return anum;
    }

    public void setAnum(String anum) {
        this.anum = anum;
    }

    @XmlElement(name = "bnum")
    public String getBnum() {
        return bnum;
    }

    public void setBnum(String bnum) {
        this.bnum = bnum;
    }

    @XmlElement(name = "serviceType")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @XmlElement(name = "usage")
    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    @XmlElement(name = "startDateTime")
    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }


    public String toString(int index) {
        return(Color.colorText("\nCDR number (" + index + ")\n", Color.underline) +
                Color.colorText("   Anum: ", Color.baby_blue) + anum + '\n' +
                Color.colorText("   Bnum: ", Color.lavender) + bnum + '\n' +
                Color.colorText("   Service: " , Color.baby_pink)+ serviceType + '\n' +
                Color.colorText("   Date: ", Color.grey) + startDateTime + '\n' +
                Color.colorText("   Usage: ", Color.orange) + usage +
                "\n-------");
    }
}
