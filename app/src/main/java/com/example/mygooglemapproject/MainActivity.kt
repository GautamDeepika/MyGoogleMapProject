package com.example.mygooglemapproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.mygooglemapproject.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityMainBinding
    private var googleMap: GoogleMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val mapFragment: SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.googleMap) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap

        val latlng: LatLng = LatLng(28.7041, 77.1025)
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        val marker = googleMap?.addMarker(
            MarkerOptions()
                .position(latlng)
                .title("Marker in Delhi")
                .icon(getBitmapFromVector(applicationContext, R.drawable.baseline_location_on_24))
        )

        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        // Move the camera to the specified location and set the zoom level
        // Move the camera to the specified location and set the zoom level
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 15.0f)
        googleMap!!.moveCamera(cameraUpdate)

        mBinding.ivRecenter.setOnClickListener {
            recenterMap()
        }

    }

    private fun recenterMap() {
        // Recenter the map to the default location and set the default zoom level
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(28.7041, 77.1025), 12.0f)
            googleMap?.moveCamera(cameraUpdate)

    }

    companion object {
        private fun getBitmapFromVector(context: Context, drawableId: Int): BitmapDescriptor? {
            var drawable: Drawable? = ContextCompat.getDrawable(context, drawableId)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = (drawable?.let { DrawableCompat.wrap(it) })?.mutate()

            }

            val bitmap: Bitmap? = drawable?.let {
                Bitmap.createBitmap(
                    it.intrinsicWidth,
                    drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
                )
            }
            val canvas: Canvas? = bitmap?.let { Canvas(it) }

            canvas?.width?.let { canvas.height.let { it1 -> drawable?.setBounds(0, 0, it, it1) } }
            if (drawable != null) {
                if (canvas != null) {
                    drawable.draw(canvas)
                }
            }
            return bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }
        }
    }

}