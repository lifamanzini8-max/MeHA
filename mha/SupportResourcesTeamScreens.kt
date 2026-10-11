package com.example.mha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState


data class SupportPerson(
    val id: String,
    val name: String,
    val title: String,
    val description: String,
    val isOnline: Boolean = true
)

val supportPeople = listOf(
    SupportPerson(
        id = "sarah",
        name = "Dr. Sarah Mahlangu",
        title = "Professional Counselor",
        description = "Licensed mental health professional here at MHA. I'm really glad you've reached out today.",
        isOnline = true
    ),
    SupportPerson(
        id = "peer",
        name = "Peer Supporter",
        title = "Youth Mentor",
        description = "Fellow student trained in active listening. I understand what you're going through.",
        isOnline = true
    ),
    SupportPerson(
        id = "trauma",
        name = "Trauma & Healing Counselor",
        title = "Child & Youth Trauma Specialist",
        description = "Helping you process difficult experiences and heal in a safe space.",
        isOnline = true
    )
)

@Composable
fun ChatSupportApp() {
    var selectedPerson by remember { mutableStateOf<SupportPerson?>(null) }
    if (selectedPerson == null) {
        SupportPeopleScreen(onPersonClick = { selectedPerson = it })
    } else {
        ChatScreen(person = selectedPerson!!, onBack = { selectedPerson = null })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportPeopleScreen(onPersonClick: (SupportPerson) -> Unit) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF1A1A1A))
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF0D4F44), Color(0xFF1A7A6B))
                            )
                        )
                        .padding(24.dp, 40.dp)
                ) {
                    Text(
                        text = "Chat Support",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Reach out anytime — real peer support, always confidential.",
                        fontSize = 15.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFFB84000), Color(0xFFE2703A))
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = "⚠️ In immediate danger? Call 10111 (SA Police)",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "0800 567 567 (SADAG 24hr) | 0861 322 322 (Lifeline SA)",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Professional",
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 15.sp,
                    modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp)
                )
            }

            items(supportPeople) { person ->
                SupportPersonCard(person = person, onClick = { onPersonClick(person) })
            }
        }
    }
}

@Composable
fun SupportPersonCard(person: SupportPerson, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF238575)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = person.name.first().toString(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = person.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (person.isOnline) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                    }
                }
                Text(
                    text = person.title,
                    fontSize = 13.sp,
                    color = Color(0xFF81C784),
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = person.description,
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(person: SupportPerson, onBack: () -> Unit) {
    var userText by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            "Hello! I'm ${person.name}. How can I support you today?" to false
        )
    }

    fun send() {
        if (userText.isNotBlank()) {
            messages.add(userText to true)
            val reply = when (person.id) {
                "sarah" -> "Thank you for sharing. I'm here to listen and support you. You're not alone in this."
                "peer" -> "I hear you 💛 I've been there too. We'll get through this together."
                "trauma" -> "I'm so proud of you for reaching out.Healing takes time,but you are safe here with me."
                else -> "I'm here for you."
            }
            messages.add(reply to false)
            userText = ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(person.name, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", color = Color.White, fontSize = 20.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D4F44),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF1A1A1A))
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { (text, isUser) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (isUser) Color(0xFF238575) else Color(0xFF2A2A2A)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = text,
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(14.dp, 10.dp),
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userText,
                    onValueChange = { userText = it },
                    placeholder = { Text("Type your message...", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2A2A2A),
                        unfocusedContainerColor = Color(0xFF2A2A2A),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White

                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = { send() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF238575))
                ) {
                    Text("Send", color = Color.White)
                }
            }
        }
    }
}

data class Article(
    val id: Int,
    val title: String,
    val category: String,
    val icon: String,
    val summary: String,
    val contentSections: List<Pair<String, List<String>>>,
    val intro: String,
    val tags: List<String> = listOf(category, "Mental Health", "Support"),
)

object ArticleRepository {
    val categories = listOf("all", "resilience", "wellbeing", "anxiety", "depression", "stress", "selfcare")

    val articles = listOf(
        Article(
            id = 1,
            title = "Building Resilience in Difficult Times",
            category = "resilience",
            icon = "🌱",
            summary = "Resilience is the ability to bounce back from adversity. Learn practical strategies to strengthen your mental resilience...",
            intro = "Resilience isn't about avoiding stress, but learning to adapt. It's a skill that can be developed over time with practice and patience.",
            contentSections = listOf(
                "Key Strategies for Building Resilience" to listOf(
                    "Maintain Connections: Build strong, positive relationships with friends and family.",
                    "Practice Self-Care: Prioritize sleep, nutrition, and physical activity.",
                    "Set Realistic Goals: Break large tasks into manageable steps.",
                    "Develop a Positive Self-View: Trust your ability to solve problems.",
                    "Keep Things in Perspective: Avoid blowing events out of proportion.",
                    "Embrace Change: Accept that change is a part of living.",
                )
            )
        ),
        Article(
            id = 2,
            title = "Understanding Anxiety: A Complete Guide",
            category = "anxiety",
            icon = "🌊",
            summary = "Anxiety is more than just feeling stressed. Discover the types, symptoms, and coping mechanisms...",
            intro = "Anxiety disorders are the most common mental health concern, affecting millions worldwide. Understanding anxiety is the first step toward managing it effectively.",
            contentSections = listOf(
                "Common Types of Anxiety" to listOf(
                    "Generalized Anxiety Disorder (GAD): Persistent worry about everyday things.",
                    "Social Anxiety: Fear of social situations and being judged by others.",
                    "Panic Disorder: Recurring panic attacks with physical symptoms.",
                    "Specific Phobias: Intense fear of specific objects or situations."
                ),
                "Coping Strategies" to listOf(
                    "Deep breathing and relaxation techniques",
                    "Regular physical exercise",
                    "Limiting caffeine and alcohol",
                    "Cognitive Behavioral Therapy (CBT)",
                    "Mindfulness and meditation practices"
                )
            )
        ),
        Article(
            id = 3,
            title = "Self-Care Rituals for Daily Wellness",
            category = "selfcare",
            icon = "🧘",
            summary = "Self-care isn't selfish—it's essential. Create a sustainable self-care routine that works for you...",
            intro = "Effective self-care includes physical activity, adequate sleep, healthy eating, and activities that bring joy. Start small: even 10 minutes of daily self-care can make a significant difference.",
            contentSections = listOf(
                "Building Your Self-Care Routine" to listOf(
                    "Physical Self-Care: Exercise, sleep, nutrition, medical check-ups.",
                    "Emotional Self-Care: Journaling, therapy, expressing feelings.",
                    "Social Self-Care: Time with loved ones, boundary setting.",
                    "Mental Self-Care: Reading, learning, creative activities.",
                    "Spiritual Self-Care: Meditation, nature, reflection."
                )
            )
        ),
        Article(
            id = 4,
            title = "Managing Depression: Steps Forward",
            category = "depression",
            icon = "☀️",
            summary = "Depression affects millions worldwide. Learn about symptoms, treatment options, and recovery stories...",
            intro = "Depression is a serious but treatable condition. It's not a sign of weakness, and seeking help is a sign of strength.",
            contentSections = listOf(
                "Recognizing Depression" to listOf(
                    "Persistent sad, anxious, or 'empty' mood",
                    "Loss of interest in activities once enjoyed",
                    "Changes in appetite or sleep patterns",
                    "Difficulty concentrating or making decisions"
                ),
                "Treatment Approaches" to listOf(
                    "Psychotherapy: CBT, interpersonal therapy, psychodynamic therapy.",
                    "Medication: Antidepressants prescribed by a psychiatrist.",
                    "Lifestyle Changes: Exercise, sleep hygiene, social connection."
                )
            )
        ),
        Article(
            id = 5,
            title = "Stress Management Techniques",
            category = "stress",
            icon = "🎯",
            summary = "Chronic stress impacts both mental and physical health. Master these evidence-based stress reduction techniques...",
            intro = "Stress is a natural response, but chronic stress can harm your health. Learning to manage stress effectively is crucial for overall wellbeing.",
            contentSections = listOf(
                "Immediate Stress Relief Techniques" to listOf(
                    "4-7-8 Breathing: Inhale for 4, hold for 7, exhale for 8.",
                    "Progressive Muscle Relaxation: Tense and relax each muscle group.",
                    "Grounding Techniques: 5-4-3-2-1 sensory exercise."
                )
            )
        ),
        Article(
            id = 6,
            title = "The Science of Wellbeing",
            category = "wellbeing",
            icon = "🔬",
            summary = "Positive psychology research reveals what truly makes us happy. Explore the PERMA model of wellbeing...",
            intro = "The PERMA model, developed by Dr. Martin Seligman, identifies five core elements of psychological wellbeing.",
            contentSections = listOf(
                "The PERMA Model" to listOf(
                    "P - Positive Emotion: Feeling joy, gratitude, serenity, and hope.",
                    "E - Engagement: Being fully absorbed in activities (flow state).",
                    "R - Relationships: Having meaningful connections with others.",
                    "M - Meaning: Belonging to and serving something bigger than yourself.",
                    "A - Accomplishment: Pursuing success, achievement, and mastery."
                )
            )
        )
    )
}

@Composable
fun ResourcesScreen() {
    var selectedCategory by remember { mutableStateOf("all") }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    val filteredArticles = remember(selectedCategory) {
        if (selectedCategory.equals("all", ignoreCase = true)) {
            ArticleRepository.articles
        } else {
            ArticleRepository.articles.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeroHeader()

        FilterBar(
            categories = ArticleRepository.categories,
            selectedCategory = selectedCategory,
        ) { selectedCategory = it }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 280.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredArticles, key = { it.id }) { article ->
                ArticleCard(article = article) { selectedArticle = article }
            }
        }
    }

    selectedArticle?.let { article ->
        ArticleModalDialog(
            article = article,
        ) { selectedArticle = null }
    }
}

@Composable
fun HeroHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF34866F), Color(0xFF235244))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Decorative circles to match the background pattern
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-80).dp)
                .size(280.dp)
                .background(Color.White.copy(alpha = 0.05f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 100.dp, y = (-20).dp)
                .size(320.dp)
                .background(Color.White.copy(alpha = 0.05f), CircleShape)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "📚",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = "RESOURCES",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Mental Health Resources",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Expert-curated guides, articles and self-help tools for your journey.",
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            val isSelected = category.equals(selectedCategory, ignoreCase = true)
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = category.replaceFirstChar { it.uppercase() },
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF6366F1),
                    selectedLabelColor = Color.White,
                    containerColor = Color.White,
                    labelColor = Color(0xFF333333)
                ),
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF6366F1), Color(0xFFA855F7))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = article.icon, fontSize = 40.sp)
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.summary,
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280),
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = Color(0xFFB5DBC4),
                    shape = CircleShape
                ) {
                    Text(
                        text = article.category.replaceFirstChar { it.uppercase() },
                        color = Color(0xFF2D5A41),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticleModalDialog(article: Article, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismiss) {
                        Text(text = "✕", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = article.icon,
                        fontSize = 50.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = article.intro,
                        fontSize = 14.sp,
                        color = Color(0xFF4B5563),
                        lineHeight = 20.sp
                    )

                    article.contentSections.forEach { (sectionTitle, points) ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = sectionTitle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        points.forEach { point ->
                            Row(modifier = Modifier.padding(vertical = 3.dp)) {
                                Text(text = "• ", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                Text(text = point, fontSize = 13.sp, color = Color(0xFF374151))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        article.tags.forEach { tag ->
                            Surface(
                                color = Color(0xFFF3F4F6),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = tag.replaceFirstChar { it.uppercase() },
                                    fontSize = 11.sp,
                                    color = Color(0xFF6B7280),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

val Primary = Color(0xFF6C63FF)
val Dark = Color(0xFF2D2D3A)
val Light = Color(0xFFF1F0FB)
val Gradient1 = Brush.linearGradient(listOf(Color(0xFF6C63FF), Color(0xFF9A8CFF)))
val Gradient2 = Brush.linearGradient(listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0)))
val Gradient3 = Brush.linearGradient(listOf(Color(0xFF00B4DB), Color(0xFF0083B0)))
val Gradient4 = Brush.linearGradient(listOf(Color(0xFFF093FB), Color(0xFFF5576C)))
val Gradient5 = Brush.linearGradient(listOf(Color(0xFF4FACFE), Color(0xFF00F2FE)))
val Gradient6 = Brush.linearGradient(listOf(Color(0xFF43E97B), Color(0xFF38F9D7)))


data class TeamMember(
    val emoji: String,
    val name: String,
    val role: String,
    val bio: String,
    val credentials: List<String>,
    val gradient: Brush
)

val teamMembers = listOf(
    TeamMember(
        emoji = "👨‍⚕️",
        name = "Dr. Sarah Mahlangu",
        role = "Clinical Director",
        bio = "Board-certified psychiatrist with 15+ years of experience in community mental health. Leads our clinical programs and ensures evidence-based care.",
        credentials = listOf("MD, Psychiatry", "Harvard Medical", "15+ Years"),
        gradient = Gradient1
    ),
    TeamMember(
        emoji = "👩‍💼",
        name = "Dr. Michael Chen",
        role = "Lead Psychologist",
        bio = "Specializes in cognitive behavioral therapy and trauma-informed care. Passionate about making therapy accessible through technology.",
        credentials = listOf("PhD, Psychology", "Stanford", "CBT Specialist"),
        gradient = Gradient2
    ),
    TeamMember(
        emoji = "👩‍💻",
        name = "Emily Rodriguez",
        role = "Platform Developer",
        bio = "Full-stack developer focused on creating secure, user-friendly mental health platforms. Advocate for digital accessibility in healthcare.",
        credentials = listOf("MS, Computer Science", "MIT", "Security+"),
        gradient = Gradient3
    ),
    TeamMember(
        emoji = "🧠",
        name = "Dr. Aisha Patel",
        role = "Research Lead",
        bio = "Neuroscientist researching the intersection of technology and mental health. Leads our assessment validation and outcome studies.",
        credentials = listOf("PhD, Neuroscience", "Johns Hopkins", "Researcher"),
        gradient = Gradient4
    ),
    TeamMember(
        emoji = "🤝",
        name = "James Wilson",
        role = "Peer Support Coordinator",
        bio = "Lived experience advocate who trains and coordinates our peer support network. Believes in the power of shared stories and mutual aid.",
        credentials = listOf("Certified Peer", "Advocate", "10+ Years"),
        gradient = Gradient5
    ),
    TeamMember(
        emoji = "📊",
        name = "Lisa Thompson",
        role = "Data & Privacy Officer",
        bio = "Ensures HIPAA compliance and data security across all platforms. Expert in healthcare privacy law and ethical data practices.",
        credentials = listOf("JD, Privacy Law", "CIPP/US", "HIPAA Expert"),
        gradient = Gradient6
    )
)


@Composable
fun TeamGridScreen() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 340.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Meet Our Team",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Our dedicated team of mental health professionals, developers, and advocates work together to provide accessible, compassionate support for everyone.",
                    fontSize = 15.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }
        items(teamMembers) { member ->
            TeamCard(member = member)
        }
    }
}


@Composable
fun TeamCard(member: TeamMember) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(member.gradient),
                contentAlignment = Alignment.Center
            ) {
                Text(text = member.emoji, fontSize = 64.sp)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = member.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Dark
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = member.role,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Primary
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = member.bio,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(Modifier.height(16.dp))

                // Credential tags
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // wrap manually if too many — keep simple
                    member.credentials.take(3).forEach { tag ->
                        CredentialTag(tag)
                        Spacer(Modifier.width(6.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Social icons
                Row(horizontalArrangement = Arrangement.Center) {
                    SocialButton(emoji = "💼")
                    Spacer(Modifier.width(12.dp))
                    SocialButton(emoji = "📧")
                    Spacer(Modifier.width(12.dp))
                    SocialButton(emoji = "💻")
                }
            }
        }
    }
}

@Composable
fun CredentialTag(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Light
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            color = Dark,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun SocialButton(emoji: String, onClick: () -> Unit = {}) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = Light,
        modifier = Modifier.size(38.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = emoji, fontSize = 16.sp)
        }
    }
}

private enum class MhaSection(val label: String) {
    CHAT("Chat Support"), RESOURCES("Resources"), TEAM("Our Team")
}
