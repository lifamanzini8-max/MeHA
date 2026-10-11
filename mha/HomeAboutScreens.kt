package com.example.mha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Shared colors
val HeaderTan = Color.White
val HeroPurpleTop = Color(0xFF3B2FA3)
val HeroPurpleBottom = Color(0xFF6B5FD1)
val CreamBg = Color(0xFFEDE6DC)
val DarkNavy = Color(0xFF1A1A2E)
val FooterNavy = Color(0xFF232A45)

@Composable
fun MhaAppScreen(onAboutClick: ()-> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeaderBar()
        HeroSection()
        HowWeCanHelpSection()
        WhyMhaSection()
        CommunityVoicesSection()
        FooterSection(onAboutClick = onAboutClick)
    }
}

@Composable
fun HeaderBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderTan)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("🌿 MHA", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = DarkNavy)
    }
}

@Composable
fun HeroSection() {
    val gradient = Brush.verticalGradient(listOf(HeroPurpleTop, HeroPurpleBottom))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient)
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Your Mental Health Journey Starts Here",
            fontSize = 28.sp, fontWeight = FontWeight.Bold,
            color = Color(0xFFF5EDE0), textAlign = TextAlign.Center, lineHeight = 36.sp
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Access professional support, connect with peers, and discover resources for your wellbeing. Safe, confidential, and always here for you.",
            fontSize = 14.sp, color = Color(0xFFE0DCF0), textAlign = TextAlign.Center, lineHeight = 20.sp
        )
        Spacer(Modifier.height(28.dp))
        HomeButton("Take Assessment") {}
        Spacer(Modifier.height(12.dp))
        HomeButton("Get Support") {}
        Spacer(Modifier.height(12.dp))
        HomeButton("Join Community") {}
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.85f).height(50.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0x33FFFFFF),
            contentColor = Color(0xFFF5EDE0)
        )
    ) { Text(text, fontSize = 15.sp, fontWeight = FontWeight.Medium) }
}

@Composable
fun HowWeCanHelpSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderTan)
            .padding(20.dp)
    ) {
        Text(
            "How We Can Help",
            fontSize = 26.sp, fontWeight = FontWeight.Bold, color = DarkNavy,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))

        HelpCard("📝", "Self Assessments", "Confidential screening tools for depression, anxiety, ADHD, and more.")
        Spacer(Modifier.height(16.dp))
        HelpCard("📚", "Resources & Guides", "Expert-curated articles and self-help guides on resilience, anxiety, and self-care.")
        Spacer(Modifier.height(16.dp))
        HelpCard("👥", "Peer Community", "Safe, moderated forums to share experiences and find peer support.")
        Spacer(Modifier.height(16.dp))
        HelpCard("💬", "Chat Support", "Connect with professionals or AI assistance anytime.Anonymous options available.")
    }
}

@Composable
fun HelpCard(icon: String, title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x33FFFFFF), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text(icon, fontSize = 32.sp)
        Spacer(Modifier.height(12.dp))
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkNavy)
        Spacer(Modifier.height(8.dp))
        Text(description, fontSize = 14.sp, color = Color(0xFF333333), lineHeight = 20.sp)
    }
}

@Composable
fun WhyMhaSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CreamBg)
            .padding(horizontal = 24.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Why MHA - Mental Health Awareness?",
            fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3B2FA3),
            textAlign = TextAlign.Center, lineHeight = 28.sp
        )
        Spacer(Modifier.height(20.dp))
        Text(
            "We believe everyone deserves access to quality mental health support. Our platform combines evidence-based assessments, professional resources, peer support, and immediate chat assistance.",
            fontSize = 14.sp, color = Color(0xFF555555), textAlign = TextAlign.Center, lineHeight = 20.sp
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Whether you're taking your first step toward better mental health or continuing your journey, MindBridge is here to walk alongside you.",
            fontSize = 14.sp, color = Color(0xFF555555), textAlign = TextAlign.Center, lineHeight = 20.sp
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B5FD1))
        ) {
            Text("Meet Our Team", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun CommunityVoicesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderTan)
            .padding(20.dp)
    ) {
        Text(
            "Community Voices",
            fontSize = 26.sp, fontWeight = FontWeight.Bold, color = DarkNavy,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))

        TestimonialCard(
            "MindBridge helped me understand my anxiety in ways I never could before. The assessments were eye-opening.",
            "— Anonymous User"
        )
        Spacer(Modifier.height(16.dp))
        TestimonialCard(
            "Having access to professional chat support at 2 AM when I was in crisis literally saved my life. Thank you.",
            "— Community Member"
        )
        Spacer(Modifier.height(16.dp))
        TestimonialCard(
            "The resources section helped me find coping strategies that actually work. I check in weekly as part of my self-care routine.",
            "— Regular User"
        )
    }
}

@Composable
fun TestimonialCard(quote: String, author: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x40FFFFFF), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text(
            "\"$quote\"",
            fontSize = 15.sp, fontStyle = FontStyle.Italic, color = DarkNavy, lineHeight = 22.sp
        )
        Spacer(Modifier.height(10.dp))
        Text(author, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF444444))
    }
}

@Composable
fun FooterSection(onAboutClick: () -> Unit={}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FooterNavy)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🌿 MHA - Mental Health Awareness", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(Modifier.height(6.dp))
        Text("Break the silence, speak your mind!", fontSize = 13.sp, color = Color(0xFFCCCCCC))
        Spacer(Modifier.height(16.dp))
        Text("© 2026 MHA - Mental Health Awareness. All rights reserved.", fontSize = 11.sp, color = Color(0xFF999999))
        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onAboutClick) {
            Text("About Us", fontSize = 13.sp, color = Color(0xFF8FcFC0))
        }
    }
}
@Composable
fun AboutUsScreen(onNavigateBack: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeaderBar()
        OurStoryHero()
        WhyWeExistSection()
        StatsSection()
        CoreValuesSection()
        JoinMissionSection()
        AboutFooter(onNavigateBack)
    }
}

@Composable
fun OurStoryHero() {
    val gradient = Brush.verticalGradient(listOf(Color(0xFF1B3B36), Color(0xFF2E7D6B)))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient)
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color(0x33FFFFFF), RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text("OUR STORY", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFF5EDE0))
        }
        Spacer(Modifier.height(20.dp))
        Text(
            "Mental health support for everyone.",
            fontSize = 26.sp, fontWeight = FontWeight.Bold,
            color = Color(0xFFF5EDE0), textAlign = TextAlign.Center, lineHeight = 34.sp
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "We believe that access to mental health resources should be universal — safe, stigma-free, and always within reach.",
            fontSize = 14.sp, color = Color(0xFFDCE8E4), textAlign = TextAlign.Center, lineHeight = 20.sp
        )
    }
}

@Composable
fun WhyWeExistSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEDE6DC))
            .padding(horizontal = 24.dp, vertical = 40.dp)
    ) {
        Text("Why we exist", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2C3E50))
        Spacer(Modifier.height(16.dp))
        Text(
            "We believe that mental health support should be accessible, affordable, and stigma-free. Our platform combines clinical expertise with peer support and cutting-edge technology to create a safe space for healing and growth.",
            fontSize = 14.sp, color = Color(0xFF555555), lineHeight = 21.sp
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Since our founding in 2024, we've helped thousands of individuals take their first steps toward better mental health — one conversation at a time.",
            fontSize = 14.sp, color = Color(0xFF555555), lineHeight = 21.sp
        )
    }
}

@Composable
fun StatsSection() {
    val statBg = Brush.verticalGradient(listOf(Color(0xFFD9A566), Color(0xFFEDE6DC)))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(statBg)
            .padding(24.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            StatCard("10K+", "Members supported", Modifier.weight(1f))
            Spacer(Modifier.width(12.dp))
            StatCard("24/7", "Always available", Modifier.weight(1f))
        }
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth()) {
            StatCard("98%", "Feel heard & supported", Modifier.weight(1f))
            Spacer(Modifier.width(12.dp))
            StatCard("100%", "Confidential", Modifier.weight(1f))
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0x40FFFFFF), RoundedCornerShape(16.dp))
            .padding(vertical = 24.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D6B))
        Spacer(Modifier.height(6.dp))
        Text(label, fontSize = 12.sp, color = Color(0xFF555555), textAlign = TextAlign.Center)
    }
}

@Composable
fun CoreValuesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Text(
            "WHAT DRIVES US", fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
            color = Color(0xFF2C3E50), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Our Core Values", fontSize = 24.sp, fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))

        ValueCard("🔒", "Privacy First", "Your data is encrypted and protected. We never share personal information without explicit consent.")
        Spacer(Modifier.height(16.dp))
        ValueCard("❤️", "Compassionate Care", "Every interaction is guided by empathy, respect, and a genuine desire to help.")
        Spacer(Modifier.height(16.dp))
        ValueCard("🔬", "Evidence-Based", "All our tools are grounded in peer-reviewed research and clinical best practices.")
        Spacer(Modifier.height(16.dp))
        ValueCard("🌍", "Accessibility", "Mental health support should be available to everyone, regardless of location or financial status.")
    }
}

@Composable
fun ValueCard(icon: String, title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x33FFFFFF), RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(icon, fontSize = 30.sp)
        Spacer(Modifier.height(10.dp))
        Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E))
        Spacer(Modifier.height(8.dp))
        Text(description, fontSize = 13.sp, color = Color(0xFF444444), textAlign = TextAlign.Center, lineHeight = 19.sp)
    }
}

@Composable
fun JoinMissionSection() {
    val gradient = Brush.verticalGradient(listOf(Color(0xFF1B3B36), Color(0xFF2E7D6B)))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient)
            .padding(horizontal = 24.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Join Our Mission", fontSize = 24.sp, fontWeight = FontWeight.Bold,
            color = Color(0xFFF5EDE0), textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "We're always looking for passionate individuals who want to make a difference in mental health. Whether you're a clinician, developer, or advocate, there's a place for you.",
            fontSize = 14.sp, color = Color(0xFFDCE8E4), textAlign = TextAlign.Center, lineHeight = 20.sp
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {},
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5EDE0)),
            modifier = Modifier.height(48.dp)
        ) {
            Text("View Open Positions", color = Color(0xFF1B3B36), fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun AboutFooter(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FooterNavy)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("© 2026 MHA Mental Health Awareness", fontSize = 12.sp, color = Color(0xFFAAAAAA))
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TextButton(onClick = onNavigateBack) {
                Text("About", fontSize = 13.sp, color = Color(0xFF8FCFC0))
            }
            TextButton(onClick = {}) {
                Text("Login", fontSize = 13.sp, color = Color(0xFF8FCFC0))
            }
            TextButton(onClick = {}) {
                Text("Sign Up", fontSize = 13.sp, color = Color(0xFF8FCFC0))
            }
        }
    }
}
