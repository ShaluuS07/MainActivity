package com.example.mainactivity.ui

/**
 * Portrait URLs for each profile (Indian / South Asian stock from Pexels & Unsplash, plus
 * randomuser.me women portraits to fill the gallery). Requires [android.permission.INTERNET].
 */
object ProfileImageUrls {

    private const val RANDOM_USER_WOMEN = "https://randomuser.me/api/portraits/women/"

    private fun pexels(id: Long) =
        "https://images.pexels.com/photos/$id/pexels-photo-$id.jpeg?auto=compress&cs=tinysrgb&w=400&h=600&fit=crop"

    private fun unsplash(photoSlug: String) =
        "https://images.unsplash.com/photo-$photoSlug?w=400&h=600&fit=crop&auto=format&q=80"

    private fun randomUser(index: Int) = "$RANDOM_USER_WOMEN$index.jpg"

    /** 100 distinct URLs: 5 per profile × 20 profiles. */
    private val allUrls: List<String> = buildList {
        // Pexels — Indian / South Asian women & bridal (free stock)
        val pexelsIds = listOf(
            1446161L, 14829695L, 2647744L, 1580272L, 2747267L, 725458L, 2739792L,
            4428288L, 4456255L, 7685494L, 7685509L, 36477202L, 36041239L, 5872667L,
            7686290L, 7686317L, 7176441L, 7807004L, 36041249L, 5940094L, 7686292L,
            4428278L, 7686311L, 7685582L, 2905873L, 7486649L, 7176443L, 36483814L,
            7625834L, 4623816L, 36688645L,
        )
        pexelsIds.forEach { add(pexels(it)) }

        // Unsplash — “Indian woman portrait” search results (sari / traditional / portrait)
        val unsplashSlugs = listOf(
            "1667382137969-a11fd256717d",
            "1624077292049-3ff1417810de",
            "1667382137349-0f5cb5818a7c",
            "1727179297202-b1032c0f37eb",
            "1617297873650-aef8f4e00b9b",
            "1607867856297-085b093eb8e8",
            "1612449318760-be59e9e9f81f",
            "1734527225075-e3c1fa554173",
            "1734527225029-a202aec0ad98",
            "1730037656320-b003292ac9b4",
            "1734527224906-92eaabc0f665",
            "1734527224918-bbeafe9c0cc2",
            "1734527224862-b5fdc9753818",
            "1730037656391-ba5f4da99a75",
        )
        unsplashSlugs.forEach { add(unsplash(it)) }

        // Fill to 100 with distinct randomuser women portraits (indices 0–54)
        for (i in 0..54) {
            add(randomUser(i))
        }
    }

    init {
        check(allUrls.size == 100) { "Expected 100 gallery URLs, got ${allUrls.size}" }
    }

    /** Five photos per profile for carousel / preview. */
    fun urlsForProfile(profileId: Long): List<String> {
        require(profileId in 1L..20L) { "profileId must be 1..20" }
        val start = ((profileId - 1) * 5).toInt()
        return allUrls.subList(start, start + 5)
    }
}
