package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.domain.data.categories.ChildCategory
import kz.bcm.b2b.domain.usecase.GetCategoriesUseCase

class CatalogListViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    private val _categories = MutableStateFlow<List<ChildCategory>>(listOf())
    val categories get() = _categories.asStateFlow()

   fun findCategories() {
       viewModelScope.launch {
           val res = getCategoriesUseCase.execute()

            _categories.emit(res)
       }
   }
}