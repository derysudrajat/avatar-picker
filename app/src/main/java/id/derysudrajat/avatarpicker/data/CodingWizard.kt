package id.derysudrajat.avatarpicker.data

import com.google.gson.annotations.SerializedName


data class CodingWizard(
    @field:SerializedName("avatar")
    val avatar: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("skill")
    var skill: Int, // 0..100
) {
    companion object {
        val EMPTY = CodingWizard("", "", 0)
    }
}

