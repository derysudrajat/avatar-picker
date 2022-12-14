package id.derysudrajat.avatarpicker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.size.Size
import coil.transform.CircleCropTransformation
import id.derysudrajat.avatarpicker.databinding.ActivityMenuBinding
import id.derysudrajat.avatarpicker.databinding.ItemMenuBinding
import id.derysudrajat.avatarpicker.utils.listOfMenu
import id.derysudrajat.easyadapter.EasyAdapter

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMenu.adapter = menuAdapter
    }

    private val menuAdapter = EasyAdapter(listOfMenu, ItemMenuBinding::inflate) { binding, data ->
        with(binding) {
            tvMenu.text = data.title
            tvDesc.text = data.desc
            ivMenu.load(data.icon) {
                crossfade(true)
                transformations(CircleCropTransformation())
                size(Size.ORIGINAL)
            }
            root.setOnClickListener {
                val target = when (data.title) {
                    listOfMenu[0].title -> TextDragActivity::class.java
                    listOfMenu[1].title -> MainActivity::class.java
                    else -> CodingChallengeActivity::class.java
                }
                startActivity(Intent(this@MenuActivity, target))
            }
        }
    }
}