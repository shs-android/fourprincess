package com.shs.namhansanseong


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.shs.namhansanseong.databinding.FragmentStampCameraBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class StampCameraFragment : Fragment() {

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 10
    }

    private var binding: FragmentStampCameraBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentStampCameraBinding>(
            inflater, R.layout.fragment_stamp_camera, container, false
        )
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Request camera permissions
        if (isCameraPermissionGranted()) {
            binding?.textureView?.post { startCamera() }
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }
        // 텍스쳐뷰가 변경될때마다 레이아웃을 새로 고칩니다.
        binding?.textureView?.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    private fun startCamera() {
        // We will implement this in next steps.


        val previewConfig = PreviewConfig.Builder()
            // We want to show input from back camera of the device
            .setTargetAspectRatio(Rational(1, 1))
            .setLensFacing(CameraX.LensFacing.BACK)
            .build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener { previewOutput ->
            binding?.run {
                val parent = textureView.parent as ViewGroup
                parent.removeView(textureView)
                parent.addView(textureView,0)
                textureView?.surfaceTexture = previewOutput.surfaceTexture
            }

            updateTransform()
        }


        val imageAnalysisConfig = ImageAnalysisConfig.Builder()
            .build()
        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)

        val qrCodeAnalyzer = QrCodeAnalyzer { qrCodes ->
            qrCodes.forEach {
                Log.d("MainActivity", "QR Code detected: ${it.rawValue}.")
            }
        }

        imageAnalysis.analyzer = qrCodeAnalyzer

        CameraX.bindToLifecycle(this as LifecycleOwner, preview, imageAnalysis)
    }

    private fun updateTransform() {
        binding?.run {
            val matrix = Matrix()

            // Compute the center of the view finder
            val centerX = textureView.width / 2f
            val centerY = textureView.height / 2f

            // Correct preview output to account for display rotation
            val rotationDegrees = when(textureView.display.rotation) {
                Surface.ROTATION_0 -> 0
                Surface.ROTATION_90 -> 90
                Surface.ROTATION_180 -> 180
                Surface.ROTATION_270 -> 270
                else -> return
            }
            matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

            // Finally, apply transformations to our TextureView
            binding?.textureView?.setTransform(matrix)
        }

    }

    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (isCameraPermissionGranted()) {
                binding?.textureView?.post { startCamera() }
            } else {
                Toast.makeText(requireContext(), "Camera permission is required.", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        }
    }

}
