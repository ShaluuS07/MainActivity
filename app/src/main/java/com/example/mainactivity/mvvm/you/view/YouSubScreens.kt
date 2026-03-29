package com.example.mainactivity.mvvm.you.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mainactivity.R

sealed class YouSubDestination {
    data object MyProfile : YouSubDestination()
    data object PartnerPreferences : YouSubDestination()
    data object Privacy : YouSubDestination()
    data object Notifications : YouSubDestination()
    data object AccountSettings : YouSubDestination()
    data object Help : YouSubDestination()
    data object Contact : YouSubDestination()
    data object Safety : YouSubDestination()
    data object Terms : YouSubDestination()
    data object EditProfile : YouSubDestination()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouSubPageScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            content()
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun YouSubPageBody(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
private fun FormLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 4.dp),
    )
}

@Composable
private fun DemoProfileFormFields() {
    var name by remember { mutableStateOf("Arjun Raj") }
    var age by remember { mutableStateOf("32") }
    var height by remember { mutableStateOf("5 ft 10 in") }
    var language by remember { mutableStateOf("Hindi, English") }
    var education by remember { mutableStateOf("B.Tech") }
    var profession by remember { mutableStateOf("Software Engineer") }
    var city by remember { mutableStateOf("Bengaluru") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FormLabel(stringResource(R.string.you_field_name))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        FormLabel(stringResource(R.string.you_field_age))
        OutlinedTextField(
            value = age,
            onValueChange = { age = it.filter { ch -> ch.isDigit() } },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        FormLabel(stringResource(R.string.you_field_height))
        OutlinedTextField(value = height, onValueChange = { height = it }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        FormLabel(stringResource(R.string.you_field_language))
        OutlinedTextField(value = language, onValueChange = { language = it }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        FormLabel(stringResource(R.string.you_field_education))
        OutlinedTextField(value = education, onValueChange = { education = it }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        FormLabel(stringResource(R.string.you_field_profession))
        OutlinedTextField(value = profession, onValueChange = { profession = it }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        FormLabel(stringResource(R.string.you_field_city))
        OutlinedTextField(value = city, onValueChange = { city = it }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        Text(
            text = stringResource(R.string.you_form_demo_hint),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Composable
fun PageMyProfile() {
    DemoProfileFormFields()
}

@Composable
fun PagePartnerPreferences() {
    var ageFrom by remember { mutableStateOf("24") }
    var ageTo by remember { mutableStateOf("34") }
    var location by remember { mutableStateOf("India — Delhi NCR") }
    var sameReligion by remember { mutableStateOf(true) }
    var sameTongue by remember { mutableStateOf(false) }
    var vegetarian by remember { mutableStateOf(true) }
    var nonSmoker by remember { mutableStateOf(true) }
    var nriOk by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = stringResource(R.string.you_partner_section_basic),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = ageFrom,
                onValueChange = { ageFrom = it.filter { ch -> ch.isDigit() }.take(2) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(stringResource(R.string.you_partner_age_min)) },
            )
            Text(
                stringResource(R.string.you_partner_age_to),
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
            OutlinedTextField(
                value = ageTo,
                onValueChange = { ageTo = it.filter { ch -> ch.isDigit() }.take(2) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(stringResource(R.string.you_partner_age_to)) },
            )
        }
        FormLabel(stringResource(R.string.you_partner_location))
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
        )

        Text(
            text = stringResource(R.string.you_partner_section_lifestyle),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 8.dp),
        )
        CheckboxRow(stringResource(R.string.you_partner_cb_same_religion), sameReligion) { sameReligion = it }
        CheckboxRow(stringResource(R.string.you_partner_cb_same_tongue), sameTongue) { sameTongue = it }
        CheckboxRow(stringResource(R.string.you_partner_cb_vegetarian), vegetarian) { vegetarian = it }
        CheckboxRow(stringResource(R.string.you_partner_cb_non_smoker), nonSmoker) { nonSmoker = it }
        CheckboxRow(stringResource(R.string.you_partner_cb_nri_ok), nriOk) { nriOk = it }

        Text(
            text = stringResource(R.string.you_form_demo_hint),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Composable
private fun CheckboxRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .clickable { onCheckedChange(!checked) },
        )
    }
}

@Composable
fun PagePrivacy() {
    val showPhone = remember { mutableStateOf(true) }
    val showPhoto = remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        YouSubPageBody(stringResource(R.string.you_page_privacy_intro))
        RowSwitch(stringResource(R.string.you_page_privacy_phone), showPhone.value) { showPhone.value = it }
        RowSwitch(stringResource(R.string.you_page_privacy_photo), showPhoto.value) { showPhoto.value = it }
    }
}

@Composable
private fun RowSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun PageNotifications() {
    val match = remember { mutableStateOf(true) }
    val msg = remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        YouSubPageBody(stringResource(R.string.you_page_notifications_intro))
        RowSwitch(stringResource(R.string.you_page_notif_matches), match.value) { match.value = it }
        RowSwitch(stringResource(R.string.you_page_notif_messages), msg.value) { msg.value = it }
    }
}

@Composable
fun PageAccountSettings() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(stringResource(R.string.you_page_account_email_label), fontWeight = FontWeight.SemiBold)
        Text(stringResource(R.string.you_page_account_email_value), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.you_page_account_phone_label), fontWeight = FontWeight.SemiBold)
        Text(stringResource(R.string.you_page_account_phone_value), style = MaterialTheme.typography.bodyMedium)
    }
}

private data class FaqItem(val questionRes: Int, val answerRes: Int)

@Composable
fun PageHelp() {
    val items = listOf(
        FaqItem(R.string.you_page_help_q1, R.string.you_page_help_a1),
        FaqItem(R.string.you_page_help_q2, R.string.you_page_help_a2),
        FaqItem(R.string.you_page_help_q3, R.string.you_page_help_a3),
        FaqItem(R.string.you_page_help_q4, R.string.you_page_help_a4),
        FaqItem(R.string.you_page_help_q5, R.string.you_page_help_a5),
    )
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.forEach { item ->
            ExpandableFaqItem(
                question = stringResource(item.questionRes),
                answer = stringResource(item.answerRes),
            )
        }
    }
}

@Composable
private fun ExpandableFaqItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        ),
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Text(
                    text = answer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }
    }
}

@Composable
fun PageContact() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        YouSubPageBody(stringResource(R.string.you_page_contact_body))
    }
}

@Composable
fun PageSafety() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.you_page_safety_intro),
            style = MaterialTheme.typography.bodyLarge,
        )
        BulletList(items = stringArrayResource(R.array.you_safety_bullets).toList())
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.you_page_safety_body),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun PageTerms() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeading(stringResource(R.string.you_page_terms_section_agreement))
        BulletList(items = stringArrayResource(R.array.you_terms_bullets_intro).toList())
        SectionHeading(stringResource(R.string.you_page_terms_section_privacy))
        BulletList(items = stringArrayResource(R.array.you_terms_bullets_privacy).toList())
        Text(
            text = stringResource(R.string.you_page_terms_body),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Composable
private fun SectionHeading(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
private fun BulletList(items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { line ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "\u2022",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 10.dp, top = 2.dp),
                )
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
fun PageEditProfile() {
    var headline by remember { mutableStateOf("Looking for a meaningful match") }
    var about by remember {
        mutableStateOf(
            "Family-oriented, loves travel and music. Based in Bengaluru; open to relocating for the right person.",
        )
    }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FormLabel(stringResource(R.string.you_field_headline))
        OutlinedTextField(
            value = headline,
            onValueChange = { headline = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        FormLabel(stringResource(R.string.you_field_about))
        OutlinedTextField(
            value = about,
            onValueChange = { about = it },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
        )
        Spacer(Modifier.height(8.dp))
        DemoProfileFormFields()
    }
}

fun YouSubDestination.titleRes(): Int = when (this) {
    YouSubDestination.MyProfile -> R.string.you_menu_my_profile
    YouSubDestination.PartnerPreferences -> R.string.you_menu_partner_preferences
    YouSubDestination.Privacy -> R.string.you_menu_privacy
    YouSubDestination.Notifications -> R.string.you_menu_notifications
    YouSubDestination.AccountSettings -> R.string.you_menu_account_settings
    YouSubDestination.Help -> R.string.you_menu_help
    YouSubDestination.Contact -> R.string.you_menu_contact
    YouSubDestination.Safety -> R.string.you_menu_safety
    YouSubDestination.Terms -> R.string.you_menu_terms
    YouSubDestination.EditProfile -> R.string.you_edit_profile
}

@Composable
fun YouSubDestination.ContentPage() {
    when (this) {
        YouSubDestination.MyProfile -> PageMyProfile()
        YouSubDestination.PartnerPreferences -> PagePartnerPreferences()
        YouSubDestination.Privacy -> PagePrivacy()
        YouSubDestination.Notifications -> PageNotifications()
        YouSubDestination.AccountSettings -> PageAccountSettings()
        YouSubDestination.Help -> PageHelp()
        YouSubDestination.Contact -> PageContact()
        YouSubDestination.Safety -> PageSafety()
        YouSubDestination.Terms -> PageTerms()
        YouSubDestination.EditProfile -> PageEditProfile()
    }
}
