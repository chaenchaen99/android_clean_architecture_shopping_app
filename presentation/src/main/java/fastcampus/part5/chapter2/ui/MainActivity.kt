package fastcampus.part5.chapter2.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import fastcampus.part5.chapter2.ui.theme.MyApplicationTheme
import fastcampus.part5.chapter2.viewmodel.MainViewModel
import fastcampus.part5.chapter2.viewmodel.TempViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val viewModel : MainViewModel by viewModels()
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
        viewModel.updateColumnCount(getColumnCount())
    }

    private fun getColumnCount(): Int {
        return getDisplayWidthDp().toInt() /160
    }

    private fun getDisplayWidthDp() : Float{
        return resources.displayMetrics.run {widthPixels/density}
    }
    companion object{
        private const val DEFAULT_COLUMN_SIZE = 160
    }
}
