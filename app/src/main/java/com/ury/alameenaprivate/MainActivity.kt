
package com.ury.alameenaprivate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Pink = Color(0xFFE91E63)
private val Background = Color(0xFFFFF5F8)
private val Dark = Color(0xFF39212C)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Pink,
                    background = Background,
                    surface = Color.White,
                    onBackground = Dark,
                    onSurface = Dark
                )
            ) {
                AlameenaApp()
            }
        }
    }
}

@Composable
fun AlameenaApp() {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var firstName by rememberSaveable { mutableStateOf("أنا") }
    var secondName by rememberSaveable { mutableStateOf("أنت") }
    var message by rememberSaveable { mutableStateOf("") }
    var questionAnswer by rememberSaveable { mutableStateOf("") }

    val memories = remember { mutableStateListOf<String>() }

    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val tabs = listOf("الرئيسية", "ذكرياتنا", "إعداداتنا")
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Text(
                                listOf("♥", "♡", "⚙")[index],
                                fontSize = 20.sp
                            )
                        },
                        label = { Text(title) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    firstName = firstName,
                    secondName = secondName,
                    answer = questionAnswer,
                    onAnswerChange = { questionAnswer = it }
                )

                1 -> MemoriesScreen(
                    memories = memories,
                    onAdd = { memories.add(it) }
                )

                2 -> SettingsScreen(
                    firstName = firstName,
                    secondName = secondName,
                    onFirstNameChange = { firstName = it },
                    onSecondNameChange = { secondName = it }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    firstName: String,
    secondName: String,
    answer: String,
    onAnswerChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("♥", color = Pink, fontSize = 48.sp)

        Text(
            "مساحتنا الخاصة",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Dark
        )

        Text(
            "$firstName  ♡  $secondName",
            color = Pink,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(Modifier.padding(22.dp)) {
                Text(
                    "رسالة اليوم",
                    color = Pink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "بعض الأشخاص يجعلون الأيام العادية أجمل، " +
                        "ويصبح وجودهم أجمل تفاصيل الحياة. ♥",
                    color = Dark,
                    fontSize = 17.sp,
                    lineHeight = 28.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE4ED)
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    "سؤالنا اليوم 💗",
                    color = Pink,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    "ما أجمل ذكرى تتمنى أن نعيش مثلها مرة أخرى؟",
                    color = Dark,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = answer,
                    onValueChange = onAnswerChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("اكتب إجابتك هنا...")
                    },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "كل لحظة جميلة تستحق أن نتذكرها ♥",
            color = Pink,
            fontSize = 15.sp
        )
    }
}

@Composable
fun MemoriesScreen(
    memories: List<String>,
    onAdd: (String) -> Unit
) {
    var newMemory by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp)
    ) {
        Text(
            "ذكرياتنا ♡",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Dark
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = newMemory,
            onValueChange = { newMemory = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("اكتب ذكرى جميلة...") },
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                if (newMemory.isNotBlank()) {
                    onAdd(newMemory.trim())
                    newMemory = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("إضافة ذكرى ♥")
        }

        Spacer(Modifier.height(18.dp))

        if (memories.isEmpty()) {
            Text(
                "ذكرياتكم الجميلة ستبدأ من هنا 💗",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Pink
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(memories) { memory ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            "♥  $memory",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 16.sp,
                            color = Dark
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    firstName: String,
    secondName: String,
    onFirstNameChange: (String) -> Unit,
    onSecondNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp)
    ) {
        Text(
            "إعداداتنا",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Dark
        )

        Spacer(Modifier.height(22.dp))

        Text("الاسم الأول", color = Dark)

        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text("الاسم الثاني", color = Dark)

        OutlinedTextField(
            value = secondName,
            onValueChange = onSecondNameChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            "Alameena Private ♥",
            color = Pink,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "مساحة خاصة للرسائل والذكريات الجميلة.",
            color = Dark
        )
    }
}
