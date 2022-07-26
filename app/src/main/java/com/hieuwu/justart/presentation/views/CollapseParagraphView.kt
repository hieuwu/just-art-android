package com.hieuwu.justart.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Transformation
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.hieuwu.justart.R
import java.nio.file.FileSystemLoopException

@SuppressLint("ViewConstructor")
class CollapseParagraphView(
    context: Context,
    attrs: AttributeSet?,
) : ConstraintLayout(context, attrs) {

    private var title: String? = null
    private var content: String? = null

    private var titleTextView: TextView? = null
    private var contentTextView: TextView? = null
    private val DURATION = 250L
    init {
        val view = inflate(
            context,
            R.layout.custom_collapse_paragraph_view, this
        )

        titleTextView = view.findViewById(R.id.titleText)
        contentTextView = view.findViewById(R.id.paragraphText)
        val titleLayout: View = view.findViewById(R.id.titleLayout)
        val arrowButton: View = view.findViewById(R.id.arrowButton)
        contentTextView?.visibility = GONE
        context.withStyledAttributes(attrs, R.styleable.CollapseParagraphView) {
            titleTextView?.text = getString(R.styleable.CollapseParagraphView_titleValue)
            contentTextView?.text = getString(R.styleable.CollapseParagraphView_contentValue)
        }
        titleLayout.setOnClickListener {
            contentTextView?.let {
                if (it.visibility == VISIBLE) {
                    collapse(it)
                    rotateUp(arrowButton)
                } else {
                    expand(it)
                    rotateDown(arrowButton)
                }
            }
        }
    }

    fun setTitle(text: String?) {
        text?.let {
            titleTextView?.text = text
        }
    }

    fun setContent(text: String?) {
        text?.let {
            contentTextView?.text = text
        }
    }

    private fun expand(v: View) {
        v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight: Int = v.measuredHeight
        v.layoutParams.height = 0
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                v.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
                    else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        v.visibility = View.VISIBLE
        a.duration = (targetHeight / v.context.resources
            .displayMetrics.density).toLong()
        v.startAnimation(a)
    }

    private fun collapse(v: View) {
        val initialHeight: Int = v.measuredHeight
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = DURATION
        v.startAnimation(a)
    }


    private fun rotateUp(view: View) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.rotate_up)
        view.startAnimation(anim)
    }

    private fun rotateDown(view: View) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.rotate_down)
        view.startAnimation(anim)
    }
}