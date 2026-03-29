package com.example.mainactivity.mvvm.profile.model

data class ProfileUi(
    val id: Long,
    val name: String,
    val age: Int,
    val height: String,
    val language: String,
    val religionCommunity: String,
    val education: String,
    val profession: String,
    val city: String,
    val stateCountry: String,
    val profileIdCode: String,
    val isVerified: Boolean,
    val isPremiumNri: Boolean,
    val photoCount: Int,
    val photoPreviewUrl: String,
    val photoUrls: List<String>,
    val cardDescription: String,
    val detailLine1: String,
    val detailLine2: String,
    val sheetContent: ProfileSheetContentUi,
)

fun ProfileEntity.toUi(): ProfileUi {
    val line1 = "$age Yrs, $height, $language, $religionCommunity"
    val line2 = "$education, $profession, $city, $stateCountry"
    val cardDesc = buildString {
        append("$age Yrs, $height, $language, $religionCommunity\n")
        append("$education, $profession, $city, $stateCountry")
    }
    val urls = ProfileImageUrls.urlsForProfile(id)
    val sheet = sheetContent()
    return ProfileUi(
        id = id,
        name = name,
        age = age,
        height = height,
        language = language,
        religionCommunity = religionCommunity,
        education = education,
        profession = profession,
        city = city,
        stateCountry = stateCountry,
        profileIdCode = profileIdCode,
        isVerified = isVerified,
        isPremiumNri = isPremiumNri,
        photoCount = photoCount,
        photoPreviewUrl = urls.first(),
        photoUrls = urls,
        cardDescription = cardDesc,
        detailLine1 = line1,
        detailLine2 = line2,
        sheetContent = sheet,
    )
}
