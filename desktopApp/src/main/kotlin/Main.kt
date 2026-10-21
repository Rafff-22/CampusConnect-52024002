import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.kharisma.campusconnect.shared.CourseRepositoryImpl
import com.kharisma.campusconnect.shared.MataKuliah
import com.kharisma.campusconnect.shared.getPlatformName

@Composable
fun DesktopScreen() {
    val repository = CourseRepositoryImpl()
    val courses = repository.getAllCourses()
    val platformName = getPlatformName()

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CampusConnect Desktop",
                    fontSize = 24.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                Text(
                    text = "Berjalan di: $platformName",
                    fontSize = 16.sp
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(courses) { mk ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            androidx.compose.foundation.layout.Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(text = "${mk.nama} — ${mk.sks} SKS", style = MaterialTheme.typography.titleMedium)
                                Text(text = "Dosen: ${mk.dosen}")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    val windowState = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "CampusConnect Desktop"
    ) {
        DesktopScreen()
    }
}