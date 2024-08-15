package fastcampus.part5.chapter2.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel //Hit Annotation은 Hit가 viewModel을 관리하도록 지정한다.
class MainViewModel @Inject constructor() : ViewModel(){
    //@Inject Annotation을 통해 Hilt에게 이 클래스의 생성자를 사용하여 인스턴스를 생성할 수 있음을 알린다.
    //ViewModel을 통해 UI관련 데이터를 저장하고 관리하는 역할을 한다.
    fun openSearchForm() {
        println("open search form")
    }
}