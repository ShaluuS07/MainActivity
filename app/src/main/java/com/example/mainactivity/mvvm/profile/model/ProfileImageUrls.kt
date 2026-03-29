package com.example.mainactivity.mvvm.profile.model


object ProfileImageUrls {

    private val pexelsPhotoIdByProfile: List<Long> = listOf(
        30703877L,
        5341868L,
        15434988L,
        32161001L,
        15556801L,
        17595469L,
        6842793L,
        18606416L,
        30371667L,
        27139274L,
    )

    private fun pexelsVariant(photoId: Long, w: Int, h: Int, dpr: Int? = null): String {
        val dprPart = if (dpr != null) "&dpr=$dpr" else ""
        return "https://images.pexels.com/photos/$photoId/pexels-photo-$photoId.jpeg" +
            "?auto=compress&cs=tinysrgb&w=$w&h=$h&fit=crop$dprPart"
    }

    private fun galleryUrlsForPhoto(photoId: Long): List<String> = listOf(
        pexelsVariant(photoId, w = 400, h = 720),
        pexelsVariant(photoId, w = 480, h = 640),
        pexelsVariant(photoId, w = 360, h = 600),
        pexelsVariant(photoId, w = 420, h = 780),
        pexelsVariant(photoId, w = 400, h = 600, dpr = 2),
    )

    fun urlsForProfile(profileId: Long): List<String> {
        require(profileId in 1L..10L) { "profileId must be 1..10" }
        val photoId = pexelsPhotoIdByProfile[(profileId - 1).toInt()]
        return galleryUrlsForPhoto(photoId)
    }
}
