import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.temansawit.ui.screen.camera.views.CameraTActivity
import com.example.temansawit.ui.screen.camera.views.DeteksiActivity
import com.example.temansawit.ui.theme.OrangePrimary

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val customButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = OrangePrimary,
        contentColor = Color.White
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.padding(top = 24.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(50),
            onClick = {
                val intent = Intent(context, CameraTActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(text = "DETEKSI GENDER BIBIT")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(50),
            colors = customButtonColors,
            onClick = {
                val intent = Intent(context, DeteksiActivity::class.java)
                context.startActivity(intent)
            }
        ) {
            Text(text = "DATEKSI KEMATANGAN BUAH")
        }
    }
}
