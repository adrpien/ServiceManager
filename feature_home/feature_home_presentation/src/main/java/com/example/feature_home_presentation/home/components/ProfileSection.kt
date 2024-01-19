package com.example.feature_home_presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.util.Helper
import com.example.feature_home_presentation.R
import com.example.servicemanager.feature_home_domain.model.Profile

@Composable
fun ProfileSection(
    profile: Profile
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        Row {
            val bitmap = Helper.byteArrayToBitmap(profile.profilePicture)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(10.dp)
                    .border(
                        border = BorderStroke(1.dp, Color.Black),
                        shape = MaterialTheme.shapes.medium
                    )

            )
            Column {

                Text(
                    text = profile.user.userName,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.point_this_month) + ": ${profile.pointsThisMonth}",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}