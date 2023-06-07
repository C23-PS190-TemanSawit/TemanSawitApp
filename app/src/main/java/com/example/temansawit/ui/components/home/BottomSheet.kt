import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.temansawit.ui.components.transaction.CRUDTransaction
import com.example.temansawit.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    modalSheetState: ModalBottomSheetState,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val modifier = if (isSheetFullScreen)
        Modifier
            .fillMaxSize()
    else
        Modifier.fillMaxWidth()

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
        sheetContent = {
            Column(
                modifier = modifier
                    .padding( bottom = 24.dp),
            ) {
                Text(
                    text = "TAMBAH TRANSAKSI BARU",
                    modifier.padding(top = 40.dp, bottom = 24.dp),
                    style = Typography.subtitle2,
                    textAlign = TextAlign.Center
                )
                CRUDTransaction(modalSheetState)
            }
        }
    ) {
        content()
    }
}