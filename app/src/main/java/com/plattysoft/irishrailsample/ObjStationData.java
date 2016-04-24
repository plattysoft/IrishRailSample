package com.plattysoft.irishrailsample;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/* This is an example XML Response for this type of object:

<objStationData>
    <Servertime>2016-04-24T14:39:30.117</Servertime>
    <Traincode>D905</Traincode>
    <Stationfullname>Broombridge</Stationfullname>
    <Stationcode>BBRDG</Stationcode>
    <Querytime>14:39:30</Querytime>
    <Traindate>24 Apr 2016</Traindate>
    <Origin>Dublin Pearse</Origin>
    <Destination>Maynooth</Destination>
    <Origintime>14:43</Origintime>
    <Destinationtime>15:27</Destinationtime>
    <Status>No Information</Status>
    <Lastlocation/>
    <Duein>20</Duein>
    <Late>0</Late>
    <Exparrival>14:58</Exparrival>
    <Expdepart>14:59</Expdepart>
    <Scharrival>14:58</Scharrival>
    <Schdepart>14:59</Schdepart>
    <Direction>Northbound</Direction>
    <Traintype>ARROW</Traintype>
    <Locationtype>S</Locationtype>
</objStationData>
 */

@Root(name = "objStationData")
public class ObjStationData {
    @Element(name = "Servertime")
    public String Servertime;
    @Element(name = "Traincode")
    public String Traincode;
    @Element(name = "Stationfullname")
    public String Stationfullname;
    @Element(name = "Stationcode")
    public String Stationcode;
    @Element(name = "Querytime")
    public String Querytime;
    @Element(name = "Traindate")
    public String Traindate;
    @Element(name = "Origin")
    public String Origin;
    @Element(name = "Destination")
    public String Destination;
    @Element(name = "Origintime")
    public String Origintime;
    @Element(name = "Destinationtime")
    public String Destinationtime;
    @Element(name = "Status")
    public String Status;
    @Element(name = "Lastlocation", required = false)
    public String Lastlocation;
    @Element(name = "Duein")
    public Integer Duein;
    @Element(name = "Late")
    public Integer Late;
    @Element(name = "Exparrival")
    public String Exparrival;
    @Element(name = "Expdepart")
    public String Expdepart;
    @Element(name = "Scharrival")
    public String Scharrival;
    @Element(name = "Schdepart")
    public String Schdepart;
    @Element(name = "Direction")
    public String Direction;
    @Element(name = "Traintype")
    public String Traintype;
    @Element(name = "Locationtype")
    public String Locationtype;
}
