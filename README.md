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
* Add a mock API to be able to test the functionality anytime (There are no trains that do that conneciton late in the evening, also interesting for automatic testing)
* Add a confirmation that the train goes to the destination station. This requires an extra API call, but solves the problem of getting into the wrong train (which does not happen in the particular example, but could be if the destination was Howth or Sutton)
* Allow selection of origin and destination station as well as directions
* Auto selection of origin station based on GPS
* Auto-refresh every minute to check if the arrival time has changed.
