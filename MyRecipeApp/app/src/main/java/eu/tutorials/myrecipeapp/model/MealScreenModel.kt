package eu.tutorials.myrecipeapp.model


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.myrecipeapp.data.Meal
import eu.tutorials.myrecipeapp.recipeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MealListViewModel() : ViewModel() {

    private val _mealListState = mutableStateOf(MealListByCategoryState())
    val mealListStateObj: State<MealListByCategoryState> = _mealListState
//    init {
//        fetchMealListByCategory()
//    }
    fun previewDataApiCalling(data:String){
        fetchMealListByCategory(data)
    }
    private fun fetchMealListByCategory(categoryName : String) {
//        Log.d("Data","Category Name :: ${categoryName.trim()}")
        viewModelScope.launch {
            try {
                val response = recipeService.getMealListByCategory(categoryName?:"Beef")
                _mealListState.value = _mealListState.value.copy(
                    list = response.meals,
                    loading = false,
                    error = null
                )

            }catch (e: Exception){
                _mealListState.value = _mealListState.value.copy(
                    loading = false,
                    error = "Error fetching Categories ${e.message}"
                )
            }
        }
    }

    data class MealListByCategoryState(
        val loading: Boolean = true,
        val list: List<Meal> = emptyList(),
        val error: String? = null
    )

}