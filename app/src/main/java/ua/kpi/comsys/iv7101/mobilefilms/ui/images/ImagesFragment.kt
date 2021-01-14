package ua.kpi.comsys.iv7101.mobilefilms.ui.images

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_images.*
import ua.kpi.comsys.iv7101.mobilefilms.R
import ua.kpi.comsys.iv7101.mobilefilms.ui.allImages

const val PICK_IMAGE = 1001
const val PERMISSION_REQUEST_CODE = 1002

class ImagesFragment : Fragment() {

    private lateinit var imagesAdapter: ImagesListAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_images, container, false)

        imagesAdapter = ImagesListAdapter(allImages)

        val recyclerView: RecyclerView = root.findViewById(R.id.images_list)
        recyclerView.adapter = imagesAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addImageButton.setOnClickListener {innerView ->
            onAddImageClicked(innerView)
        }

    }

    private fun onAddImageClicked (view: View)
    {
        if (checkSelfPermission(view.context, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }
        val gallery =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            val imageUri = data?.data
            imagesAdapter.addImage(imageUri)
        }
    }
}