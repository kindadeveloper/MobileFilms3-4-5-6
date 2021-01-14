package ua.kpi.comsys.iv7101.mobilefilms.ui.images

import android.net.Uri

class ImageSet {
    private val imagesCount = 10
    val images = MutableList<Uri?>(imagesCount){null}
    var nextImageIdx = 0

    fun isFull(): Boolean {return nextImageIdx == imagesCount}

    fun addImage(image: Uri?): Boolean {
        if (nextImageIdx == imagesCount)
            return false
        images[nextImageIdx] = image
        nextImageIdx++
        return true
    }
}