package id.derysudrajat.avatarpicker.ui

import android.content.ClipData
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.DRAG_FLAG_GLOBAL
import android.view.View.DRAG_FLAG_GLOBAL_URI_READ
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.DragStartHelper
import androidx.core.view.isVisible
import androidx.draganddrop.DropHelper
import coil.load
import coil.size.Size
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import id.derysudrajat.avatarpicker.R
import id.derysudrajat.avatarpicker.databinding.ActivityMainBinding
import id.derysudrajat.avatarpicker.databinding.ItamAvatarBinding
import id.derysudrajat.avatarpicker.utils.listOfAvatar
import id.derysudrajat.easyadapter.EasyAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvAvatar.adapter = avatarAdapter
        supportActionBar?.apply {
            title = "Avatar Picker"
            setDisplayHomeAsUpEnabled(true)
        }

        configureTarget()

        with(binding) {
            buttonClearImage.setOnClickListener {
                targetImage.setImageResource(0)
                tvDesc.isVisible = true
                buttonCard.isVisible = false
            }
        }
    }

    private fun configureTarget() = DropHelper.configureView(
        this, binding.cardAvatar,
        arrayOf("image/*", "text/*"),
        DropHelper.Options.Builder()
            .setHighlightColor(getColor(R.color.purple_200))
            .setHighlightCornerRadiusPx(resources.getDimensionPixelSize(R.dimen.drop_target_avatar))
            .build()
    ) { _, payload ->
        val item = payload.clip.getItemAt(0)
        val (_, remaining) = payload.partition { it == item }

        item.uri?.let { uri -> handleImageDrop(uri) }
            ?: run { Log.d("TAG", "avatarDrop: failed = Clip data is missing URI") }
        remaining
    }

    private fun handleImageDrop(uri: Uri) = with(binding) {
        targetImage.load(uri) {
            crossfade(true)
            transformations(CircleCropTransformation())
            size(Size.ORIGINAL)
        }
        buttonCard.isVisible = true
        tvDesc.isVisible = false
    }

    private val avatarAdapter = object : EasyAdapter<String, ItamAvatarBinding>(listOfAvatar) {
        override fun create(parent: ViewGroup): ItamAvatarBinding = ItamAvatarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        override fun onBind(binding: ItamAvatarBinding, data: String) {
            binding.ivAvatar.load(data) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
                size(Size.ORIGINAL)
            }
            DragStartHelper(binding.ivAvatar) { view, _ ->
                val dragClipData = ClipData.newUri(contentResolver, "Image", Uri.parse(data))
                val dragShadow = View.DragShadowBuilder(view)
                view.startDragAndDrop(
                    dragClipData,
                    dragShadow,
                    null,
                    DRAG_FLAG_GLOBAL.or(DRAG_FLAG_GLOBAL_URI_READ)
                )

            }.attach()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}