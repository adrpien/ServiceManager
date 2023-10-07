package com.example.servicemanager.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.theme.TiemedLightBlue
import com.example.core.theme.TiemedVeryLightBeige
import com.example.core.theme.TiemedVeryLightBlue

@Composable
fun BottomNavigationBar(
    itemList: List<BottomNavigationItem>,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavigationItem) -> Unit
) {

    NavigationBar(
        modifier = modifier,
        containerColor = TiemedLightBlue,
        tonalElevation = 4.dp
        ) {
        itemList.forEach {  item ->
            val backStackEntry = navHostController.currentBackStackEntryAsState()
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = TiemedVeryLightBeige,
                unselectedContentColor = TiemedVeryLightBlue,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if(item.badgeCount > 0 ) {
                            BadgedBox(
                                badge = {
                                       Text(text = item.badgeCount.toString())
                                },
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            )
        }
    }
}