import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.temansawit.ui.screen.camera.views.CameraTActivity
import com.example.temansawit.ui.screen.camera.views.DeteksiActivity

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .verticalScroll(scrollState)
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
