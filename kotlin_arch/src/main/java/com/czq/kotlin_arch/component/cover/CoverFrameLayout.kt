package com.czq.kotlin_arch.component.cover

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import com.czq.kotlin_arch.R
import kotlinx.android.synthetic.main.common_error_view.view.*

class CoverFrameLayout : FrameLayout {

    var loadingView: View? = null
    var emptyView: View? = null
    var errorView: View? = null
    val inflater by lazy { LayoutInflater.from(context) }
    var doReload: (() -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    interface CoverFrameListener {
        fun onReload()
    }

    fun showLoading() {
        if (loadingView == null) {
            loadingView = inflater.inflate(R.layout.common_loading_view, null)
        }
        if (loadingView?.parent == null) {
            val lp = loadingView?.layoutParams
            this.addView(loadingView)
        }
        loadingView?.visibility = View.VISIBLE
        emptyView?.visibility = View.GONE
        errorView?.visibility = View.GONE

    }

    fun showEmpty() {
        if (emptyView == null) {
            emptyView = inflater.inflate(R.layout.common_empty_view, null)
            emptyView?.setOnClickListener {
                reload()
            }
        }
        if (emptyView?.parent == null) {
            val lp = emptyView?.layoutParams
            this.addView(emptyView)
        }

        emptyView?.visibility = View.VISIBLE
        loadingView?.visibility = View.GONE
        errorView?.visibility = View.GONE
    }

    fun showError() {
        if (errorView == null) {
            errorView = inflater.inflate(R.layout.common_error_view, null)
            errorView?.btnRetry?.setOnClickListener {
                reload()
            }
        }
        if (errorView?.parent == null) {
            val lp = errorView?.layoutParams
            this.addView(errorView)
        }

        errorView?.visibility = View.VISIBLE
        loadingView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
    }

    fun showContent() {
        errorView?.visibility = View.GONE
        loadingView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
    }

    fun reload() {
        showLoading()
        doReload?.invoke()
    }
}