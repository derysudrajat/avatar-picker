package id.derysudrajat.avatarpicker.ui

import android.content.ClipData
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.DragStartHelper
import androidx.core.view.isVisible
import androidx.draganddrop.DropHelper
import androidx.lifecycle.MutableLiveData
import coil.load
import coil.size.Size
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import id.derysudrajat.avatarpicker.R
import id.derysudrajat.avatarpicker.data.CodingWizard
import id.derysudrajat.avatarpicker.databinding.ActivityCodingChallangeBinding
import id.derysudrajat.avatarpicker.databinding.ItamAvatarBinding
import id.derysudrajat.avatarpicker.databinding.ItemWizardBinding
import id.derysudrajat.avatarpicker.utils.listOfWizard
import id.derysudrajat.easyadapter.EasyAdapter

class CodingChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCodingChallangeBinding
    private var _isChallengeValid = MutableLiveData<Boolean>()
    private val listOfChar = mutableListOf(false, false)
    private val listOfDataChar = mutableListOf(CodingWizard.EMPTY, CodingWizard.EMPTY)
    private val isChallengeValid get() = _isChallengeValid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodingChallangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Coding Challenge"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.rvChar.adapter = charAdapter

        configView(binding.cardLeft, binding.charLeft, 0)
        configView(binding.cardRight, binding.charRight, 1)

        isChallengeValid.observe(this) { binding.btnStart.isEnabled = it }

        with(binding) {
            btnStart.setOnClickListener {
                challengeProgress.isVisible = true
                btnStart.text = ""
                Handler(mainLooper).postDelayed({
                    challengeProgress.isVisible = false
                    updateChallenge(TYPE_START)
                }, 2000L)
            }
        }
    }

    private fun configView(
        card: MaterialCardView,
        char: ItemWizardBinding,
        index: Int
    ) {
        DropHelper.configureView(
            this, card, arrayOf("image/*", "text/*"),
            DropHelper.Options.Builder()
                .setHighlightColor(getColor(R.color.purple_200))
                .setHighlightCornerRadiusPx(resources.getDimensionPixelSize(R.dimen.drop_target_corner_radius))
                .build()
        ) { _, payload ->
            val item = payload.clip.getItemAt(0)
            val (_, remaining) = payload.partition { it == item }

            item.text?.let {
                if (isChallengeValid.value == true) updateChallenge(TYPE_RESET)

                val data = Gson().fromJson(it.toString(), CodingWizard::class.java)
                char.handleCustomObjectDrop(data, index)
            } ?: run {
                Log.d("TAG", "configView: index[$index] failed = Clip data is missing JSON")
            }
            remaining
        }
    }

    private fun ItemWizardBinding.handleCustomObjectDrop(data: CodingWizard, index: Int) {
        with(this) {
            ivAvatar.load(data.avatar) {
                crossfade(true)
                transformations(CircleCropTransformation())
                size(Size.ORIGINAL)
            }
            tvName.text = data.name
            val skill = (0..100).random() + data.skill
            tvPower.text = (skill / 2).toString()
            progressBar.apply {
                max = 200
                progress = skill
            }
            root.isVisible = true
            arrayOf(binding.tvDescLeft, binding.tvDescRight)[index].isVisible = false
            listOfChar[index] = true
            listOfDataChar[index] = data.apply { this.skill = skill }
            checkValidation()
        }
    }

    private fun updateChallenge(type: Int) {
        val theWinner = if (listOfDataChar[0].skill > listOfDataChar[1].skill) 0 else 1
        with(binding) {
            arrayOf(binding.tvDescLeft, binding.tvDescRight).map { desc ->
                if (type == TYPE_RESET) desc.isVisible = true
            }
            btnStart.text = if (type == TYPE_RESET) "Start Challenge"
            else buildString { append(listOfDataChar[theWinner].name).append(" Win") }
            arrayOf(charRight, charLeft).let {
                if (type == TYPE_RESET) it.map { item ->
                    item.updateColorFilter(type)
                    item.root.isVisible = false
                } else it[theWinner].apply { updateColorFilter(type) }

                it.map { item ->
                    item.progressBar.isVisible = type == TYPE_START
                    item.tvPower.isVisible = type == TYPE_START
                }
            }
            if (type == TYPE_RESET) {
                listOfDataChar.replaceAll { CodingWizard.EMPTY }
                listOfChar.replaceAll { false }
                checkValidation()
            }
        }
    }

    private fun ItemWizardBinding.updateColorFilter(type: Int) {
        ivAvatar.colorFilter = getColorFilter(if (type == TYPE_RESET) FULL_COLOR else GRAY_SCALE)
        val colorProgressBar =
            getColor(if (type == TYPE_RESET) R.color.purple_500 else R.color.black)
        progressBar.apply {
            setIndicatorColor(colorProgressBar)
            trackColor = ColorUtils.setAlphaComponent(colorProgressBar, 50)
        }
    }

    private fun checkValidation() {
        _isChallengeValid.value = listOfChar.filter { it }.size == 2
    }

    private val charAdapter =
        EasyAdapter(listOfWizard, ItamAvatarBinding::inflate) { binding, data ->
            binding.ivAvatar.load(data.avatar) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
                size(Size.ORIGINAL)
            }
            DragStartHelper(binding.ivAvatar) { view, _ ->
                val dragClipData = ClipData.newPlainText("Avatar", Gson().toJson(data))
                val dragShadow = View.DragShadowBuilder(view)
                view.startDragAndDrop(
                    dragClipData,
                    dragShadow,
                    null,
                    View.DRAG_FLAG_GLOBAL
                )

            }.attach()
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val GRAY_SCALE = 0f
        const val FULL_COLOR = 100f
        const val TYPE_RESET = 0
        const val TYPE_START = 1
        fun getColorFilter(type: Float) =
            if (type == GRAY_SCALE) ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) }) else null
    }
}