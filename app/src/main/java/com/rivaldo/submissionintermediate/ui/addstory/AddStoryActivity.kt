package com.rivaldo.submissionintermediate.ui.addstory

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.rivaldo.core.domain.Resource
import com.rivaldo.core.utils.compressFile
import com.rivaldo.core.utils.createCustomTempFile
import com.rivaldo.core.utils.rotateBitmap
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityAddStoryBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private var imageBitmap: Bitmap? = null
    lateinit var binding: ActivityAddStoryBinding
    private var getFile: File? = null

    private val viewModel: AddStoryViewModel by viewModel()

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                lateinit var result: Deferred<Bitmap>
                lifecycleScope.launch(Dispatchers.Default) {
                    getFile = compressFile(File(currentPhotoPath))
                    result = async {
                        rotateBitmap(
                            BitmapFactory.decodeFile(getFile?.path),
                            isBackCamera = true
                        )
                    }
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.imageView.setImageBitmap(result.await())
                    }
                }


            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, "Permission not granted by the user.", Toast.LENGTH_SHORT)
                    .show()
                requestCameraPermission()
            }
        }
    }

    private fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.buttonAdd.setOnClickListener {
            uploadStory()
        }
        if (allPermissionsGranted()) {
//            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            requestCameraPermission()
        }
    }

    private fun uploadStory() {
        if (getFile != null) {
            val file = getFile as File
            val description = binding.editTextTextDescription.text.toString()
                .toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.addNewStory(description = description, image = imageMultiPart)
                    .collect { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                binding.progressBar4.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                binding.progressBar4.visibility = View.GONE
                                runOnUiThread {
                                    Toast.makeText(
                                        this@AddStoryActivity,
                                        getString(R.string.story_success_upload),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    finish()
                                }


                            }
                            is Resource.Error -> {
                                binding.progressBar4.visibility = View.GONE
                                runOnUiThread {
                                    Toast.makeText(
                                        this@AddStoryActivity,
                                        getString(R.string.story_fail_upload),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            }
                        }

                    }
            }

        } else {
            Toast.makeText(
                this@AddStoryActivity,
                getString(R.string.story_image_not_set),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.rivaldo.submissionintermediate",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }


}