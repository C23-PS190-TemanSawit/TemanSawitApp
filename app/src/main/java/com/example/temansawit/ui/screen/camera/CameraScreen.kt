import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.temansawit.R
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.screen.camera.CameraTActivity
import com.example.temansawit.ui.screen.camera.DeteksiActivity

@Composable
fun CameraScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
            )
        },
        content = { it
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.padding(top = 56.dp))

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        val intent = Intent(context, CameraTActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text(text = "DETEKSI GENDER BIBIT")
                }

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        val intent = Intent(context, DeteksiActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text(text = "DATEKSI KEMATANGAN BUAH")
                }
            }
        }
    )
}
