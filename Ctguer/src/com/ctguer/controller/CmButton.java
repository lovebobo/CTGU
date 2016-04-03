package com.ctguer.controller;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

/**
 * 自定义图片按钮（ImageButton），按下颜色改变
 * @author Leo
 * @created 2013-3-15
 */
public class CmButton extends ImageButton implements OnTouchListener, OnFocusChangeListener {

 public CmButton(Context context) {
  super(context);
  this.setOnTouchListener(this);
  this.setOnFocusChangeListener(this);
 }

 public CmButton(Context context, AttributeSet attrs) {
  this(context, attrs, android.R.attr.imageButtonStyle);
  this.setOnTouchListener(this);
  this.setOnFocusChangeListener(this);
 }

 public CmButton(Context context, AttributeSet attrs, int defStyle) {
  super(context, attrs, defStyle);
  setFocusable(true);
  this.setOnTouchListener(this);
  this.setOnFocusChangeListener(this);
 }

 @Override
 public void onFocusChange(View v, boolean hasFocus) {
  // 灰色效果
  ColorMatrix cm = new ColorMatrix();
  cm.setSaturation(0);
  if (hasFocus) {
   ((ImageButton) v).getDrawable().setColorFilter(new ColorMatrixColorFilter(cm));
  } else {
   ((ImageButton) v).getDrawable().clearColorFilter();
  }
 }

 @Override
 public boolean onTouch(View v, MotionEvent event) {
  // 灰色效果
  ColorMatrix cm = new ColorMatrix();
  cm.setSaturation(0);
  if (event.getAction() == MotionEvent.ACTION_DOWN) {
   ((ImageButton) v).getDrawable().setColorFilter(new ColorMatrixColorFilter(cm));
  } else if (event.getAction() == MotionEvent.ACTION_UP) {
   ((ImageButton) v).getDrawable().clearColorFilter();
  }
  return false;
 }
}

