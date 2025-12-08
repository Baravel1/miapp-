package com.seba.miapp

import android.content.pm.PackageManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seba.miapp.ui.theme.MiAppTheme
import androidx.compose.ui.platform.LocalContext
import com.seba.miapp.R

class MainActivity : ComponentActivity() {

    private val retos = listOf(
        "Baila como un robot en la calle",
        "Habla solo en rimas por 1 minuto",
        "Haz una coreografía con tu mascota",
        "Imita a un presentador de noticias inventadas",
        "Canta una canción inventada sobre el desayuno",
        "Haz un desfile ridículo con ropa al revés",
        "Imita a una gallina por 1 minuto"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.RECORD_AUDIO
                ), 100
            )
        }
        super.onCreate(savedInstanceState)
        setContent {
            MiAppTheme {
                DesafioApp(retos)
            }
        }
    }
}

@Composable
fun DesafioApp(retos: List<String>) {
    val context = LocalContext.current
    var retoActual by remember { mutableStateOf(retos.random()) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White)

            Spacer(modifier = Modifier.height(24.dp))

            // Globo de diálogo
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    text = retoActual,
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF006064),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { retoActual = retos.random() }) {
                Text("Nuevo reto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val intent = Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE)
                context.startActivity(intent)
            }) {
                Text("Grabar video")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Mi reto ridículo: $retoActual #DesafioRidiculo")
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(shareIntent, "Compartir reto con..."))
            }) {
                Text("Compartir")
            }
        }
    }
}