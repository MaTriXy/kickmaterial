package com.byoutline.kickmaterial.features.rewardlist

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import com.byoutline.kickmaterial.BR
import com.byoutline.kickmaterial.KickMaterialApp
import com.byoutline.kickmaterial.R
import com.byoutline.kickmaterial.model.Reward
import com.byoutline.kickmaterial.model.RewardItem
import com.byoutline.secretsauce.utils.showDebugToast
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import paperparcel.PaperParcel
import paperparcel.PaperParcelable
import java.util.*

class RewardListViewModel : ViewModel() {
    val items = ObservableArrayList<RewardItem>()
    val itemBinding: ItemBinding<RewardItem>
    @ColorInt
    private val semiGreenColor: Int = ContextCompat.getColor(KickMaterialApp.component.app, R.color.green_light)

    init {
        val itemBind = OnItemBindClass<RewardItem>()
                .map(RewardHeader::class.java, BR.header, R.layout.reward_header_item)
                .map(Reward::class.java, { itemBinding, position, _ ->
                    @ColorInt val bgColor: Int = if (position == 0) semiGreenColor else Color.WHITE
                    itemBinding.clearExtras()
                            .set(BR.reward, R.layout.reward_list_item)
                            .bindExtra(BR.bgColor, bgColor)
                            .bindExtra(BR.rewardClickListener, RewardClickListener { reward ->
                                KickMaterialApp.component.app.showDebugToast("RewardClicked " + reward)
                            })
                })
        itemBinding = ItemBinding.of(itemBind)
    }

    fun setItems(newItems: List<Reward>) {
        synchronized(items) {
            var currentThreshold = 100.0
            val step = 10.0
            items.clear()

            val rewardItems = ArrayList<RewardItem>()

            for (reward in newItems) {
                if (reward.minimum >= currentThreshold) {
                    rewardItems.add(RewardHeader(currentThreshold.toInt()))
                    currentThreshold *= step
                }
                rewardItems.add(reward)
            }

            items.addAll(rewardItems)
        }
    }
}


@PaperParcel
internal class RewardHeader(var minimum: Int) : RewardItem, PaperParcelable {

    override val itemType: Int
        get() = RewardItem.HEADER

    companion object {
        @JvmField
        val CREATOR = PaperParcelRewardHeader.CREATOR
    }
}