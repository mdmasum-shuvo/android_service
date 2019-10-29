package com.masum.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class LocationService : Service(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {
    internal lateinit var mLocationClient: GoogleApiClient
    internal var mLocationRequest = LocationRequest()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mLocationClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        mLocationRequest.interval = FASTEST_LOCATION_INTERVAL
        mLocationRequest.fastestInterval = FASTEST_LOCATION_INTERVAL


        val priority = LocationRequest.PRIORITY_HIGH_ACCURACY //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.priority = priority
        mLocationClient.connect()
        //Make it stick to the notification panel so it is less prone to get cancelled by the Operating System.
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    /*
     * LOCATION CALLBACKS
     */
    override fun onConnected(dataBundle: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "== Error On onConnected() Permission not granted")
            //Permission not granted by user so cancel the further execution.

            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
            mLocationClient,
            mLocationRequest,
            this
        )

        Log.d(TAG, "Connected to Google API")
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    override fun onConnectionSuspended(i: Int) {
        Log.d(TAG, "Connection suspended")
    }

    //to get the location change
    override fun onLocationChanged(location: Location?) {
        Log.d(TAG, "Location changed")


        if (location != null) {
            Log.d(TAG, "== location != null")
            // Toast.makeText(this, String.valueOf(location.getLatitude()) + " \n" + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
            //Send result to activities

            Log.e("location", "lat:" + location.latitude + " \nlng:" + location.longitude)


            //sendMessageToUI(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        }
    }


    override fun onDestroy() {
        if (mLocationClient.isConnected)
            mLocationClient.disconnect()
        super.onDestroy()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    companion object {
        private val FASTEST_LOCATION_INTERVAL: Long = 5000

        private val TAG = LocationService::class.java.simpleName
    }
}