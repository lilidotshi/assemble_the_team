package com.eightbyeight.assemble.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.IntentSender;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.eightbyeight.assemble.R;
import com.eightbyeight.assemble.adapters.AssembleContacts;
import com.eightbyeight.assemble.filehandlers.FileHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

/**
 * This is the default tab, which allows the user to send an ASSEMBLE! call to
 * contacts with a touch of a button.
 * 
 * @author shil
 * 
 */
public class AssembleFragment extends Fragment implements OnClickListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {
    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    LocationClient mLocationClient;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.assemble_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Try to establish Location Services
        if (servicesConnected()) {
            mLocationClient = new LocationClient(getActivity(), this, this);

            // TODO: Create a loader to let the user know that we're still
            // determining location
            new Thread(new Runnable() {
                public void run() {
                    mLocationClient.connect();
                }
            }).start();

        }
    }

    @Override
    public void onClick(View arg0) {
        /* Test code */
//        mLocationClient.setMockMode(true);
//        final String PROVIDER = "flp";
//        final double LAT = 37.377166;
//        final double LNG = -122.086966;
//        final float ACCURACY = 3.0f;
//        Location newLocation = new Location(PROVIDER);
//        newLocation.setLatitude(LAT);
//        newLocation.setLongitude(LNG);
//        newLocation.setAccuracy(ACCURACY);
//        newLocation.setTime(System.currentTimeMillis());
//        newLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtime());
//        mLocationClient.setMockLocation(newLocation);
        /* End test code */
        Log.d("ASSEMBLE!", "" + mLocationClient.isConnected());

        Log.d("ASSEMBLE!", "" + mLocationClient.getLastLocation());
        /* Test code below! Should probably 1. thread, 2. create a new object, and 3 error validate it. But positive case works!*/
        if (mLocationClient.isConnected() && mLocationClient.getLastLocation() != null){
    		SmsManager manager = SmsManager.getDefault();
        	Log.d("ASSEMBLE!","The message will look like: ASSEMBLE! https://maps.google.com/?q=" + mLocationClient.getLastLocation().getLatitude() + "," + mLocationClient.getLastLocation().getLongitude());
        	for(AssembleContacts contact : FileHandler.getInstance().getContacts()){
        		Log.d("ASSEMBLE!", contact.getPhoneNumbers().get(0));
        		manager.sendTextMessage(contact.getPhoneNumbers().get(0), null, "ASSEMBLE! https://maps.google.com/?q=" + mLocationClient.getLastLocation().getLatitude() + "," + mLocationClient.getLastLocation().getLongitude(), null, null);
        	}
        }
        		
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            Log.d("ASSEMBLE!", "Could not connect to location services");
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    resultCode, getActivity(),
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(getFragmentManager(), "Location Updates");
            }
        }
        return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        /*
         * Google Play services can resolve some errors it detects. If the error
         * has a resolution, try sending an Intent to start a Google Play
         * services activity that can resolve error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the user with
             * the error.
             */
            showErrorDialog(connectionResult.getErrorCode());
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("ASSEMBLE!", "Services have been connected");
        Button assembleButton = (Button) getActivity().findViewById(
                R.id.assembleButton);
        assembleButton.setEnabled(true);
        assembleButton.setOnClickListener(this);
    }

    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(getActivity(), "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    void showErrorDialog(int code) {
        GooglePlayServicesUtil.getErrorDialog(code, getActivity(),
                REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
    }
}
