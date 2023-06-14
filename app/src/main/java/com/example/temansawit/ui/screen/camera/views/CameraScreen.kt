import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.temansawit.ui.screen.camera.views.CameraTActivity
import com.example.temansawit.ui.screen.camera.views.DeteksiActivity
import com.example.temansawit.ui.theme.OrangePrimary

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Catatan", fontWeight = FontWeight.Bold) },
            text = { Text(text = "Arahkan kamera handphone ke bibit sawit (disarankan dari arah atas), di layer handphone akan memperlihatkan letak posisi sulur. \n" +
                    "Cek pada bagian yang terdeteksi. Jika terdapat sulur pada daun, Silahkan tekan tombol \"deteksi\" pada aplikasi dan aplikasi akan menampilkan hasil eteksinya.") },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                    }
                ) {
                    Text(text = "Lanjutkan")
                }
            },
        )
    }
}


@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val customButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = OrangePrimary,
        contentColor = Color.White
    )

    // Track the visibility of the dialog
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.padding(top = 24.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(50),
            onClick = {
                showDialog.value = true
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

    // Show the dialog based on the visibility state
    ConfirmationDialog(
        showDialog.value,
        onConfirm = {
            val intent = Intent(context, CameraTActivity::class.java)
            context.startActivity(intent)
        },
        onDismiss = {
            showDialog.value = false
        }
    )
}
