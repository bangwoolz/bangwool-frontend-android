package com.example.bangwool.ui.home

import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class SwipeHelperCallback : ItemTouchHelper.Callback() {

    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f
    private var clamp = 0f

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, LEFT or RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        currentDx = 0f
        getDefaultUIUtil().clearView(getView(viewHolder))
        previousPosition = viewHolder.adapterPosition
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 10
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        val isClamped = getTag(viewHolder)
        // 현재 View가 고정되어있지 않고 사용자가 -clamp 이상 swipe시 isClamped true로 변경 아닐시 false로 변경
        setTag(viewHolder, !isClamped && currentDx <= -clamp)
        return 2f
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)
            val x =  clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)

            currentDx = x
            getDefaultUIUtil().onDraw(
                c,
                recyclerView,
                view,
                x,
                dY,
                actionState,
                isCurrentlyActive
            )

        }
    }

    private fun clampViewPositionHorizontal(
        view: View,
        dX: Float,
        isClamped: Boolean,
        isCurrentlyActive: Boolean
    ) : Float {
        // View의 가로 길이의 절반까지만 swipe 되도록
        val min: Float = -view.width.toFloat()/2
        // RIGHT 방향으로 swipe 막기
        val max: Float = 0f

        val x = if (isClamped) {
            // View가 고정되었을 때 swipe되는 영역 제한
            if (isCurrentlyActive) dX - clamp else -clamp
        } else {
            dX
        }

        return min(max(min, x), max)
    }

    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
        // isClamped를 view의 tag로 관리
        viewHolder.itemView.tag = isClamped
    }

    private fun getTag(viewHolder: RecyclerView.ViewHolder) : Boolean {
        // isClamped를 view의 tag로 관리
        return viewHolder.itemView.tag as? Boolean ?: false
    }

    private fun getView(viewHolder: RecyclerView.ViewHolder) : View {
        return (viewHolder as HomeAdapter.ViewHolder).binding.swipeView
    }

    fun setClamp(clamp: Float) {
        this.clamp = clamp
    }

    fun removePreviousClamp(recyclerView: RecyclerView) {
        if (currentPosition == previousPosition)
            return
        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).translationX = 0f
            setTag(viewHolder, false)
            previousPosition = null
        }
    }

}


//class SwipeHelperCallback : ItemTouchHelper.Callback() {
//
//    private var currentPosition: Int? = null
//    private var previousPosition: Int? = null
//    private var currentDx = 0f
//    private var clamp = 0f
//
//    override fun getMovementFlags(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder
//    ): Int {
//        return makeMovementFlags(0, LEFT or RIGHT)
//    }
//
//    override fun onMove(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        target: RecyclerView.ViewHolder
//    ): Boolean {
//        return false
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
//
//    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
//        currentDx = 0f
//        getDefaultUIUtil().clearView(getView(viewHolder))
//        previousPosition = viewHolder.adapterPosition
//    }
//
//    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
//        viewHolder?.let {
//            currentPosition = viewHolder.adapterPosition
//            getDefaultUIUtil().onSelected(getView(it))
//        }
//    }
//
//    //view 일정 범위 밖으로 swipe 될 시 View escape 막음
//    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
//        return defaultValue * 10
//    }
//
//    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
//        val isClamped = getTag(viewHolder)
//        // 현재 View가 고정되어있지 않고 사용자가 -clamp 이상 swipe시 isClamped true로 변경 아닐시 false로 변경
//        setTag(viewHolder, !isClamped && currentDx <= -clamp)
//        return 2f
//    }
//    //
//
//    override fun onChildDraw(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean
//    ) {
//        if (actionState == ACTION_STATE_SWIPE) {
//            val view = getView(viewHolder)
//            val isClamped = getTag(viewHolder)
//            val x =  clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)
//
//            currentDx = x
//
//            getDefaultUIUtil().onDraw(
//                c,
//                recyclerView,
//                view,
//                dX,
//                dY,
//                actionState,
//                isCurrentlyActive
//            )
//        }
//
//    }
//
//    //특정 view만 swipe
//    private fun getView(viewHolder: RecyclerView.ViewHolder): View {
//        return (viewHolder as HomeAdapter.ViewHolder).binding.swipeView
//    }
//
//    private fun clampViewPositionHorizontal(
//        view: View,
//        dX: Float,
//        isClamped: Boolean,
//        isCurrentlyActive: Boolean
//    ) : Float {
//        // View의 가로 길이의 절반까지만 swipe 되도록
//        val min: Float = -view.width.toFloat()/2
//        // RIGHT 방향으로 swipe 막기
//        val max: Float = 0f
//
//        val x = if (isClamped) {
//            // View가 고정되었을 때 swipe되는 영역 제한
//            if (isCurrentlyActive) dX - clamp else -clamp
//        } else {
//            dX
//        }
//        Log.d("dX", dX.toString())
////        Log.d("min", min.toString())
//        Log.d("x", x.toString())
//        Log.d("min(max(min, x), max)", min(max(min, x), max).toString())
//
//
//        return min(max(min, x), max)
//    }
//
//    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
//        // isClamped를 view의 tag로 관리
//        viewHolder.itemView.tag = isClamped
//    }
//
//    private fun getTag(viewHolder: RecyclerView.ViewHolder) : Boolean {
//        // isClamped를 view의 tag로 관리
//        return viewHolder.itemView.tag as? Boolean ?: false
//    }
//
//    fun setClamp(clamp: Float) {
//        this.clamp = clamp
//    }
//
//    // 다른 View가 swipe 되거나 터치되면 고정 해제
//    fun removePreviousClamp(recyclerView: RecyclerView) {
//        if (currentPosition == previousPosition)
//            return
//        previousPosition?.let {
//            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
//            getView(viewHolder).translationX = 0f
//            setTag(viewHolder, false)
//            previousPosition = null
//        }
//    }
//}