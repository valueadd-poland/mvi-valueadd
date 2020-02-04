package pl.valueadd.mvi.example.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils

@Parcelize
data class ExampleModel(

    var id: Long = NumberUtils.LONG_ZERO,

    var title: String = StringUtils.EMPTY

) : Parcelable
