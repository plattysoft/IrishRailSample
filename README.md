# IrishRailSample
This app checks the connections for traveling from Broombridge to Booterstown.

For this case, there needs to be a train transfer at Tara Street, the app has constants for:
* Initial Station
* Initial Direction (Northbound or Southbound)
* Transfer Station
* Transfer Direction
* Final Station

The app does the following steps:
* Find the next train from the initial station towards the inital direction
* Find the arrival time at the transfer station (based on traincode)
* Find the next train from Transfer station towards the transfer direction
* Find the arrival time at the final station (based on traincode)

The app uses retrofit for the handling of the API and Loaders for the handling of the API requests.

The UI has been designed to maximize simplicity.

##Possible improvements
* Allow selection of origin and destination station as well as directions
* Auto selection of origin station based on GPS
* Auto-refresh every minute to check if the arrival time has changed.

