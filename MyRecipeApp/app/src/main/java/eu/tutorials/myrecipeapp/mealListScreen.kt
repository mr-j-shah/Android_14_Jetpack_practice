package eu.tutorials.myrecipeapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eu.tutorials.myrecipeapp.data.Meal
import eu.tutorials.myrecipeapp.model.MealListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealListScreen(modifier: Modifier = Modifier, categoryName:String, navController: NavController) {
    val recipeViewModel: MealListViewModel = viewModel()
    recipeViewModel.previewDataApiCalling(categoryName)
    val viewstate by recipeViewModel.mealListStateObj
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(categoryName.toString())
                }
            )
        },


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    viewstate.loading -> {
                        CircularProgressIndicator(modifier.align(Alignment.Center))
                    }

                    viewstate.error != null -> {
                        Text("ERROR OCCURRED ${viewstate.error}")
                    }

                    else -> {
                        MealListContentScreen(mealas = viewstate.list)
                    }
                }
            }
        }
    }
}

@Composable
fun MealListContentScreen(mealas: List<Meal>) {
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(mealas) { mealitem ->
            MealItem(
                meal = mealitem
            )
        }
    }
}

// How each Items looks like
@Composable
fun MealItem(meal: Meal) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = rememberAsyncImagePainter(meal.strMealThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )


        Text(
            text = meal.strMeal,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

