package com.plattysoft.irishrailsample;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    private IrishRailService mApiService;

    private static final String sInitialStation =       "Broombridge";
    private static final String sInitialDirection =     "Southbound";
    private static final String sTransferStation =      "Tara Street";
    private static final String sTransferDirection =    "Southbound";
    private static final String sFinalStation =         "Booterstown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* The request is to make something useful for traveling from Broombridge to Booterstown
        every morning and every evening using irishrail API:
            * An MVP is to show the trains departing from Broombridge towards Tara in realtime
            * Then Show the time to get down at Tara and the time for the next train to Botterstown
            * The exercice states that the trip needs to be done every morning and every afternoon, but
            does not indicate that the trip is reversed, so I'll assume that both trips are done
            in the same direction (this should be something to validate for a real product).
        */
        // Notes:
        //  * For this case, there needs to be a train transfer at Tara Street
        // Possible improvements:
        //  * Allow selection of origin and destination station as well as directions
        //  * Auto selection of origin station based on GPS
        //  * Auto-refresh every minute to check if the time is still ok.

        // Create API Service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.irishrail.ie/realtime/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        mApiService = retrofit.create(IrishRailService.class);

        // Populate stations
        TextView initialStation = (TextView) findViewById(R.id.initialStationName);
        initialStation.setText(getString(R.string.fromStation).replace("[station]", sInitialStation));

        TextView transferStation = (TextView) findViewById(R.id.transferStationName);
        transferStation.setText(getString(R.string.transferStation).replace("[station]", sTransferStation));

        TextView finalStation = (TextView) findViewById(R.id.finalStationName);
        finalStation.setText(getString(R.string.arrivalStation).replace("[station]", sFinalStation));

        // Setup a loader using the same id as the listView for the
        findInitialTrain();

    }

    private String populateTrainInfoDeparture(ObjStationData stationData) {
        return getString(R.string.trainInfoDeparture)
                .replace("[expectedtime]", stationData.Exparrival)
                .replace("[destination]", stationData.Destination)
                .replace("[direction]", stationData.Direction);
    }

    private String populateTrainInfoArrival(ObjStationData stationData) {
        return getString(R.string.trainInfoArrival)
                .replace("[expectedtime]", stationData.Exparrival)
                .replace("[station]", stationData.Stationfullname);
    }

    private void findInitialTrain() {
        getLoaderManager().initLoader(R.id.initialTrainLoader, null, new LoaderManager.LoaderCallbacks<ArrayOfObjStationData>() {
            @Override
            public Loader<ArrayOfObjStationData> onCreateLoader(int i, Bundle bundle) {
                return new AsyncTaskLoader<ArrayOfObjStationData>(getApplicationContext()) {
                    @Override
                    public ArrayOfObjStationData loadInBackground() {
                        Call<ArrayOfObjStationData> call = mApiService.getStationData(sInitialStation, 90);
                        try {
                            return call.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<ArrayOfObjStationData> loader, ArrayOfObjStationData arrayOfObjStationData) {
                TextView initialTrain = (TextView) findViewById(R.id.initialTrainDeparture);
                if (arrayOfObjStationData.stationData == null) {
                    // No trains found, display that and exit
                    initialTrain.setText(R.string.noTrains);
                    return;
                }
                // Get the first train that goes towards "Dublin Pearse"
                for (ObjStationData stationData : arrayOfObjStationData.stationData) {
                    if (stationData.Direction.equals(sInitialDirection)) {
                        // Set the view for the initial train to get
                        initialTrain.setText(populateTrainInfoDeparture(stationData));
                        // Start a new loader for the transfer
                        findTransfer(stationData.Traincode);
                        return;
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<ArrayOfObjStationData> loader) {

            }
        }).forceLoad();
    }

    private void findTransfer(final String traincode) {
        getLoaderManager().initLoader(R.id.transferTrainLoader, null, new LoaderManager.LoaderCallbacks<ArrayOfObjStationData>() {
            @Override
            public Loader<ArrayOfObjStationData> onCreateLoader(int i, Bundle bundle) {
                return new AsyncTaskLoader<ArrayOfObjStationData>(getApplicationContext()) {
                    @Override
                    public ArrayOfObjStationData loadInBackground() {
                        Call<ArrayOfObjStationData> call = mApiService.getStationData(sTransferStation, 90);
                        try {
                            return call.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<ArrayOfObjStationData> loader, ArrayOfObjStationData arrayOfObjStationData) {
                TextView transferTrainDeparture = (TextView) findViewById(R.id.transfetTrainDeparture);
                if (arrayOfObjStationData.stationData == null) {
                    // No trains found, display that and exit
                    transferTrainDeparture.setText(R.string.noTrains);
                    return;
                }
                boolean initialTrainFound = false;
                // Get the first train that goes towards "Dublin Pearse"
                for (ObjStationData stationData : arrayOfObjStationData.stationData) {
                    if (initialTrainFound && stationData.Direction.equals(sTransferDirection)) {
                        // Populate the train to get
                        transferTrainDeparture.setText(populateTrainInfoDeparture(stationData));
                        findArrivalTime(stationData.Traincode);
                        return;
                    }
                    if (stationData.Traincode.equals(traincode)) {
                        initialTrainFound = true;
                        // Populate arrival at transfer station
                        TextView initialTrainArrival = (TextView) findViewById(R.id.initialTrainArrival);
                        initialTrainArrival.setText(populateTrainInfoArrival(stationData));
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<ArrayOfObjStationData> loader) {

            }
        }).forceLoad();
    }

    private void findArrivalTime(final String traincode) {
        getLoaderManager().initLoader(R.id.arrivalTrainLoader, null, new LoaderManager.LoaderCallbacks<ArrayOfObjStationData>() {
            @Override
            public Loader<ArrayOfObjStationData> onCreateLoader(int i, Bundle bundle) {
                return new AsyncTaskLoader<ArrayOfObjStationData>(getApplicationContext()) {
                    @Override
                    public ArrayOfObjStationData loadInBackground() {
                        Call<ArrayOfObjStationData> call = mApiService.getStationData(sFinalStation, 90);
                        try {
                            return call.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<ArrayOfObjStationData> loader, ArrayOfObjStationData arrayOfObjStationData) {
                // Get the first train that goes towards "Dublin Pearse"
                for (ObjStationData stationData : arrayOfObjStationData.stationData) {
                    if (stationData.Traincode.equals(traincode)) {
                        // Populate arrival at final station
                        TextView transferTrainArrivel = (TextView) findViewById(R.id.transferTrainArrival);
                        transferTrainArrivel.setText(populateTrainInfoArrival(stationData));
                        return;
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<ArrayOfObjStationData> loader) {

            }
        }).forceLoad();
    }
}
