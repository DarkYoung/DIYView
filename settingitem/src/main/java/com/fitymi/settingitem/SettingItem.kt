package com.fitymi.settingitem

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.item_setting.view.*

class SettingItem(private val mContext: Context, private val mAttr: AttributeSet?, private val mDefStyleAttr: Int) :
    RelativeLayout(mContext, mAttr, mDefStyleAttr) {
    private var mOnSettingSettingItemClick: OnSettingItemClick? = null
    private var mView: View? = null
    private var mRightIconStyle = 0
    private var mRightTipStyle = 0
    private var mChecked = false

    constructor(mContext: Context) : this(mContext, null)
    constructor(mContext: Context, mAttr: AttributeSet?) : this(mContext, mAttr, 0)

    init {
        initView()
        initData()
        initListener()
        initStyle()
    }

    private fun initView() {
        mView = View.inflate(mContext, R.layout.item_setting, this)
    }

    private fun initListener() {
        rl_container.setOnClickListener { onClick() }
        switch_right.setOnCheckedChangeListener { _, isChecked -> mOnSettingSettingItemClick?.click(isChecked) }
    }

    @SuppressLint("CustomViewStyleable", "Recycle", "ResourceAsColor")
    private fun initData() {
        val typeArray = mContext.obtainStyledAttributes(mAttr, R.styleable.SettingItem)
        val size = typeArray.indexCount
        for (i in 0 until size) {
            val attr = typeArray.getIndex(i)
            when (attr) {
                R.styleable.SettingItem_left_text ->
                    tv_left.text = typeArray.getString(attr)
                R.styleable.SettingItem_left_text_size -> {
                    tv_left.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        typeArray.getDimensionPixelSize(attr, R.dimen.largeTextSize).toFloat()
                    )
                }
                R.styleable.SettingItem_left_text_color -> {
                    tv_left.setTextColor(typeArray.getColor(attr, Color.GRAY))
                }
                R.styleable.SettingItem_left_text_margin_left -> {
                    tv_left.layoutParams = (tv_left.layoutParams as LayoutParams).apply {
                        marginStart = typeArray.getDimension(attr, 8F).toInt()
                    }
                }
                R.styleable.SettingItem_left_icon -> {
                    iv_left_icon?.apply {
                        setImageDrawable(typeArray.getDrawable(attr))
                        visibility = View.VISIBLE
                    }
                }
                R.styleable.SettingItem_left_icon_size -> {
                    iv_left_icon.layoutParams = (iv_left_icon.layoutParams as LayoutParams).apply {
                        width = typeArray.getDimension(attr, 16F).toInt()
                        height = typeArray.getDimension(attr, 16F).toInt()
                    }
                }
                R.styleable.SettingItem_right_icon -> {
                    iv_right_icon?.apply {
                        setImageDrawable(typeArray.getDrawable(attr))
                    }
                }
                R.styleable.SettingItem_right_icon_tint -> {
                    iv_right_icon?.apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            foregroundTintList = typeArray.getColorStateList(attr)
                        }
                    }
                }
                R.styleable.SettingItem_right_icon_style -> {
                    mRightIconStyle = typeArray.getInt(attr, 0)
                }
                R.styleable.SettingItem_right_text -> {
                    tv_right.text = typeArray.getString(attr)
                }
                R.styleable.SettingItem_right_text_size -> {
                    tv_right.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        typeArray.getDimensionPixelSize(attr, R.dimen.largeTextSize).toFloat()
                    )
                }
                R.styleable.SettingItem_right_text_color -> {
                    tv_right.setTextColor(typeArray.getColor(attr, Color.GRAY))
                }
                R.styleable.SettingItem_right_circle_image -> {
                    circle_image?.apply {
                        setImageDrawable(typeArray.getDrawable(attr))
                    }
                }
                R.styleable.SettingItem_right_circle_image_size -> {
                    circle_image.layoutParams = (circle_image.layoutParams as LayoutParams).apply {
                        width = typeArray.getDimension(attr, 32F).toInt()
                        height = typeArray.getDimension(attr, 32F).toInt()
                    }
                }
                R.styleable.SettingItem_right_tip_style -> {
                    mRightTipStyle = typeArray.getInt(attr, 0)
                }
            }

        }
        typeArray.recycle()
    }


    private fun initStyle() {
        when (mRightIconStyle) {
            0 -> {//默认显示
                iv_right_icon.visibility = View.VISIBLE
                switch_right.visibility = View.GONE
            }
            1 -> {//默认不显示
                iv_right_icon.visibility = View.GONE
                switch_right.visibility = View.GONE
            }
            2 -> {//显示Switch
                iv_right_icon.visibility = View.GONE
                switch_right.visibility = View.VISIBLE
            }
        }
        when (mRightTipStyle) {
            0 -> {//显示文字
                tv_right.visibility = View.VISIBLE
                circle_image.visibility = View.GONE
            }
            1 -> {//显示圆形图片
                tv_right.visibility = View.GONE
                circle_image.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 处理单击事件
     */
    private fun onClick() {
        when (mRightIconStyle) {
            0, 1 -> if (null != mOnSettingSettingItemClick) {
                mOnSettingSettingItemClick!!.click(mChecked)
            }
            2 -> {
                switch_right.isChecked = !switch_right.isChecked
                mChecked = switch_right.isChecked
            }
        }
    }

    fun setOnSettingItemClick(mOnSettingSettingItemClick: OnSettingItemClick) {
        this.mOnSettingSettingItemClick = mOnSettingSettingItemClick
    }

    interface OnSettingItemClick {
        fun click(isChecked: Boolean)
    }
}