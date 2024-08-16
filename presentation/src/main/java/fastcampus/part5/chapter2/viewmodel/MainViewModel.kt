package fastcampus.part5.chapter2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fastcampus.part5.domain.usecase.MainUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel //Hit Annotation은 Hit가 viewModel을 관리하도록 지정한다.
class MainViewModel @Inject constructor(useCase: MainUseCase) : ViewModel(){
    //@Inject Annotation을 통해 Hilt에게 이 클래스의 생성자를 사용하여 인스턴스를 생성할 수 있음을 알린다.
    //ViewModel을 통해 UI관련 데이터를 저장하고 관리하는 역할을 한다.
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount : StateFlow<Int> = _columnCount

    val productList = useCase.getProductList()
    fun openSearchForm() {
        println("open search form")
    }

    fun updateColumnCount(count: Int){
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }
    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}