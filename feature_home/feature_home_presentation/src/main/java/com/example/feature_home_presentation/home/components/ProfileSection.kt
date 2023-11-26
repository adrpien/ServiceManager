package com.example.feature_home_presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.core.util.Helper
import com.example.servicemanager.feature_home_domain.model.Profile

@Composable
fun ProfileSection(
    profile: Profile
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        val bitmap = Helper.byteArrayToBitmap(profile.profilePicture)
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Profile picture",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
            )
        Column {

            Text(
                text = profile.user.userName,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Point this month: ${profile.pointsThisMonth}"
            )
        }
    }
}