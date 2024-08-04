package org.example;

import java.time.LocalDateTime;

public class CDR {
    private String anum;
    private String bnum;
    private String serviceType;
    private double usage;
    private String startDateTime;

    public CDR(String anum, String bnum, String serviceType, float usage, String startDateTime) {
        this.anum = anum;
        this.bnum = bnum;
        this.serviceType = serviceType;
        this.usage = usage;
        this.startDateTime = startDateTime;
    }

    // Getters and Setters
    public String getAnum() { return anum; }
    public void setAnum(String anum) { this.anum = anum; }

    public String getBnum() { return bnum; }
    public void setBnum(String bnum) { this.bnum = bnum; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public double getUsage() { return usage; }
    public void setUsage(float usage) { this.usage = usage; }

    public String getStartDateTime() { return startDateTime; }
    public void setStartDateTime(String startDateTime) { this.startDateTime = startDateTime; }
}
