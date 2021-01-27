package com.tamizna.offlinenote.helper

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(space: Int): RecyclerView.ItemDecoration() {

    var halfSpace: Int = space/2

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        if (parent.getPaddingLeft() != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
            parent.setClipToPadding(false);
        }
        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        outRect.left = halfSpace;
        outRect.right = halfSpace;
    }
}