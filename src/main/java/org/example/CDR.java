package org.example;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "cdr")
public class CDR {
    private String anum;
    private String bnum;
    private String serviceType;
    private double usage;
    private String startDateTime;

    // Default no-arg constructor required for JAXB
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
}
