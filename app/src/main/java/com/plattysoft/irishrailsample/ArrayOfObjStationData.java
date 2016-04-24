package com.plattysoft.irishrailsample;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Raul Portales on 23/04/16.
 */

@Root(name = "ArrayOfObjStationData")
public class ArrayOfObjStationData {

    @ElementList(inline = true, required = false)
    public List<ObjStationData> stationData;

}
