package id.derysudrajat.avatarpicker.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.DragStartHelper
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.draganddrop.DropHelper
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.android.material.chip.Chip
import id.derysudrajat.avatarpicker.R
import id.derysudrajat.avatarpicker.databinding.ActivityTextDragBinding
import id.derysudrajat.avatarpicker.databinding.ItemTagBinding
import id.derysudrajat.avatarpicker.utils.listOfGenre
import id.derysudrajat.easyadapter.EasyAdapter

class TextDragActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextDragBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextDragBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Tag Picker"
            setDisplayHomeAsUpEnabled(true)
        }

        with(binding) {
            populateDataList()
            configureTarget()

            edtTags.doAfterTextChanged { after ->
                populateDataList(listOfGenre.filter {
                    it.lowercase().contains(after.toString().lowercase())
                }.toMutableList())
            }
        }
    }

    private fun configureTarget() = DropHelper.configureView(
        this@TextDragActivity,
        binding.cardTag,
        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
        DropHelper.Options.Builder()
            .setHighlightColor(getColor(R.color.purple_200))
            .setHighlightCornerRadiusPx(resources.getDimensionPixelSize(R.dimen.drop_target_corner_radius))
            .build()
    ) { _, payload ->
        val item = payload.clip.getItemAt(0)
        val (_, remaining) = payload.partition { it == item }
        handleTagDrop(item)
        remaining
    }

    private fun ActivityTextDragBinding.populateDataList(tags: MutableList<String>? = null) {
        rvTag.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = getTagAdapter(tags)
        }
    }

    private fun handleTagDrop(item: ClipData.Item) {
        if (binding.chipGroup.childCount < 5) {
            item.text?.let {
                val chip = LayoutInflater.from(this@TextDragActivity)
                    .inflate(R.layout.chip_tags, binding.chipGroup, false) as Chip
                chip.text = it.substring(0, it.length.coerceAtMost(200))
                chip.setOnCloseIconClickListener {
                    listOfGenre.add(chip.text.toString())
                    with(binding) {
                        chipGroup.removeView(chip)
                        populateDataList()
                    }
                }
                listOfGenre.remove(chip.text)
                with(binding) {
                    chipGroup.addView(chip)
                    edtTags.text?.clear()
                    populateDataList()
                }
            }
        } else {
            binding.tvCardHelpers.isVisible = true
            Handler(mainLooper).postDelayed({
                binding.tvCardHelpers.isVisible = false
            }, 2000L)
        }
    }

    private fun getTagAdapter(tags: MutableList<String>? = null) =
        object : EasyAdapter<String, ItemTagBinding>(tags ?: listOfGenre.sorted().toMutableList()) {
            override fun create(parent: ViewGroup): ItemTagBinding = ItemTagBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

            override fun onBind(binding: ItemTagBinding, data: String) {
                binding.tvTag.text = data
                DragStartHelper(binding.tvTag) { view, _ ->
                    val text = (view as TextView).text
                    val dragClipData = ClipData.newPlainText("Text", text)
                    val dragShadow = View.DragShadowBuilder(view)
                    view.startDragAndDrop(dragClipData, dragShadow, null, View.DRAG_FLAG_GLOBAL)
                }.attach()
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }


}