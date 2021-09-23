package com.surrus.peopleinspace.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.surrus.peopleinspace.di.AppModule
import com.surrus.peopleinspace.util.moduleViewModel

@Composable
fun PersonDetailsScreen(personName: String, popBack: () -> Unit) {
    val peopleInSpaceViewModel = moduleViewModel(AppModule::createVM)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(personName) },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val person = peopleInSpaceViewModel.getPerson(personName)
            person?.let {
                Text(person.name, style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.size(12.dp))

                val imageUrl = person.personImageUrl ?: ""
                if (imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        modifier = Modifier.size(240.dp), contentDescription = person.name
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))

                val bio = person.personBio ?: ""
                Text(bio, style = MaterialTheme.typography.body1)
            }
        }
    }
}
