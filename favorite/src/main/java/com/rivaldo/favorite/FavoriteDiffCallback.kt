package com.rivaldo.favorite

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.rivaldo.core.domain.model.StoryModel


class FavoriteDiffCallback(oldEmployeeList: List<StoryModel>, newEmployeeList: List<StoryModel>) :
    DiffUtil.Callback() {
    private val mOldEmployeeList: List<StoryModel>
    private val mNewEmployeeList: List<StoryModel>

    init {
        mOldEmployeeList = oldEmployeeList
        mNewEmployeeList = newEmployeeList
    }

    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].id === mNewEmployeeList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldStoryModel: StoryModel = mOldEmployeeList[oldItemPosition]
        val newStoryModel: StoryModel = mNewEmployeeList[newItemPosition]
        return oldStoryModel.name.equals(newStoryModel.name)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}