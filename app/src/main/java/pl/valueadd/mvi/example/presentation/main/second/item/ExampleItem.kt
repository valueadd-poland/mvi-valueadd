package pl.valueadd.mvi.example.presentation.main.second.item

import android.os.Parcelable
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.parcel.Parcelize
import pl.valueadd.mvi.example.R
import pl.valueadd.mvi.example.databinding.ItemExampleBinding
import pl.valueadd.mvi.example.domain.model.ExampleModel

@Parcelize
class ExampleItem(override var model: ExampleModel) :
    ModelAbstractItem<ExampleModel, ExampleItem.ExampleViewHolder>(model), Parcelable {

    override val layoutRes: Int
        get() = R.layout.item_example

    override val type: Int
        get() = R.id.item_example

    override var identifier: Long
        get() = model.id
        set(_) = Unit // has to be getter

    override fun getViewHolder(v: View): ExampleViewHolder {
        val bidning = ItemExampleBinding.bind(v)
        return ExampleViewHolder(bidning.root, bidning)
    }

    class ExampleViewHolder(view: View, private val binding: ItemExampleBinding) : FastAdapter.ViewHolder<ExampleItem>(view) {

        override fun bindView(item: ExampleItem, payloads: MutableList<Any>): Unit = with(binding) {
            itemTitle.text = item.model.title
        }

        override fun unbindView(item: ExampleItem) {
            // no-op
            // here could you free up resources from Glide
        }
    }
}