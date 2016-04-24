package com.plattysoft.irishrailsample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Raul Portales on 23/04/16.
 */
public interface IrishRailService {

    //http://api.irishrail.ie/realtime/realtime.asmx/getStationDataByNameXML?StationDesc=Broombridge&NumMins=20
    @GET("realtime.asmx/getStationDataByNameXML")
    Call<ArrayOfObjStationData> getStationData(@Query("StationDesc") String stationDesc,
                                               @Query("NumMins") int numMins);
}
