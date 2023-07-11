package com.kfouri.yapetest.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kfouri.yapetest.util.ConstantTest.MAP_SCREEN_TAG

@Composable
fun MapScreen(
    lat: Double,
    lon: Double,
    name: String
) {
    val markerPosition = LatLng(lat, lon)
    val markerState = MarkerState(markerPosition)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 5f)
    }

    Box(modifier = Modifier.fillMaxSize().testTag(MAP_SCREEN_TAG)) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(state = markerState, title = name)
        }
    }
}