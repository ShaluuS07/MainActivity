package com.example.mainactivity.mvvm.profile.model

data class ProfileAstrologyUi(
    val rashi: String,
    val nakshatra: String,
    val gothra: String,
    val doshamNote: String,
)

data class ProfileSheetContentUi(
    val partnerPoints: List<String>,
    val familyPoints: List<String>,
    val astrology: ProfileAstrologyUi,
    val hobbyTags: List<String>,
)

fun ProfileEntity.sheetContent(): ProfileSheetContentUi {
    val rashiIndex = ((id - 1) % rashis.size).toInt()
    val nakIndex = ((id + 3) % nakshatras.size).toInt()
    val gothraIndex = ((id + 7) % gothras.size).toInt()
    return ProfileSheetContentUi(
        partnerPoints = partnerPoints(this),
        familyPoints = familyPoints(this),
        astrology = ProfileAstrologyUi(
            rashi = rashis[rashiIndex],
            nakshatra = nakshatras[nakIndex],
            gothra = gothras[gothraIndex],
            doshamNote = "None declared — horoscope on request for matching",
        ),
        hobbyTags = hobbyTags(this),
    )
}

private fun partnerPoints(e: ProfileEntity): List<String> = listOf(
    "Age ${e.age + 1}–${e.age + 6}, professionally settled, values aligned with ${
        e.religionCommunity
    }",
    "Graduate or equivalent, non-smoker, family-oriented",
    "Open to India relocation; NRI profiles welcome if values match",
    "Mutual respect, clear communication, shared goals for marriage & family",
)

private fun familyPoints(e: ProfileEntity): List<String> = listOf(
    "Father: working professional · Mother: homemaker · One younger sibling, well educated",
    "Based in ${e.city}, roots in ${e.stateCountry} · Own residence",
    "Moderate, progressive outlook with respect for customs & festivals",
)

private fun hobbyTags(e: ProfileEntity): List<String> {
    val sets = listOf(
        listOf("Classical dance", "Fiction", "Trekking", "Cafés", e.city),
        listOf("Yoga", "Podcasts", "Painting", "Hill stations"),
        listOf("Carnatic music", "Chess", "Volunteering", "Cooking"),
        listOf("Photography", "Badminton", "Dramas", "Heritage walks"),
        listOf("Mythology", "Swimming", "Board games", "Interiors"),
    )
    return sets[((e.id - 1) % sets.size).toInt()]
}

private val rashis = listOf(
    "Mesha (Aries)",
    "Vrishabha (Taurus)",
    "Mithuna (Gemini)",
    "Karka (Cancer)",
    "Simha (Leo)",
    "Kanya (Virgo)",
    "Tula (Libra)",
    "Vrishchika (Scorpio)",
    "Dhanu (Sagittarius)",
    "Makara (Capricorn)",
    "Kumbha (Aquarius)",
    "Meena (Pisces)",
)

private val nakshatras = listOf(
    "Ashwini", "Bharani", "Krittika", "Rohini", "Mrigashira", "Ardra",
    "Punarvasu", "Pushya", "Ashlesha", "Magha", "Purva Phalguni", "Uttara Phalguni",
    "Hasta", "Chitra", "Swati", "Vishakha", "Anuradha", "Jyeshtha",
    "Mula", "Purva Ashadha", "Uttara Ashadha", "Shravana", "Dhanishta", "Shatabhisha",
)

private val gothras = listOf(
    "Kashyapa", "Vasishtha", "Bharadwaja", "Atri", "Agastya", "Gautama",
    "Angirasa", "Kaundinya", "Jamadagni", "Vishvamitra",
)
