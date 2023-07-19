package ba.unsa.sportevents.ui.screens.mainpage.tabs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ba.unsa.etf.R
import ba.unsa.sportevents.ui.screens.activity.ActivityCard
import ba.unsa.sportevents.ui.viewmodels.MainPageViewModel
import coil.compose.rememberImagePainter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    navController: NavController,
    token : String,
    viewModel: MainPageViewModel


) {


    val user = viewModel.user.collectAsState()
    val activities = viewModel.activitiesByHost.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUser(token)
        user.value?.let { viewModel.getActivitiesByHostId(it.id) }
    }

    SideEffect {
        user.value?.let { viewModel.getActivitiesByHostId(it.id) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
    Image(
        painter = painterResource(R.drawable.profilebackground),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 35.dp)
                .align(alignment = Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(
                    data = "https://ui-avatars.com/api/?name=${user.value?.fullName}&background=random&color=fff",
                    builder = {
                        error(R.drawable.profile_picture)
                        fallback(R.drawable.profile_picture)
                    }
                ),
                contentDescription = "Profile photo",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )
            user.value?.let {
                Text(
                    text = it.fullName,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "@" + (user.value?.username ?: ""),
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }


    Box( modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Transparent)){

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.Transparent)
        ) {

            item {
                Box(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .height(200.dp)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topStart = 40.dp,
                                topEnd = 40.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {
                    Text(
                        text = "Your activities",
                        color = Color.Black,
                        modifier = Modifier
                            .padding(20.dp)
                            .background(color = Color.White),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        item {
            LazyRow {
                items(activities.value) { activity ->
                    Box(modifier = Modifier.width(getScreenWidthInDp())) {
                        ActivityCard(navController, activity, token)
                    }
                }
            }
        }


            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {
                    Text(
                        text = "Upcoming events",
                        color = Color.Black,
                        modifier = Modifier
                            .padding(20.dp)
                            .background(color = Color.White),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            user.value?.let {
                items(it.activities) { activity ->
                    if (activity != null) {
                        ActivityCard(navController, activity, token)
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(getScreenHeightInDp() - 200.dp)
                        .background(
                            color = Color.White,
                        )
                )
            }
        }

    }
}


@Composable
fun getScreenHeightInDp(): Dp {
    val configuration = LocalConfiguration.current
    val screenHeightPx = configuration.screenHeightDp.dp
    val density = LocalDensity.current.density
    return screenHeightPx
}
@Composable
fun getScreenWidthInDp(): Dp {
    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp.dp
    val density = LocalDensity.current.density
    return screenWidthPx
}




/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewCreateActivityScreen() {
    ProfileScreen()
}
*/

