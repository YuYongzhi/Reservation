package com.reservation.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class RolateImageView extends ImageView {

	private boolean isOpenAnimation = true;
	private int rotateDegree = 10;

	private boolean isFirst = true;
	private float minScale = 0.95f;
	private int mWidth;
	private int mHeight;
	private boolean isFinish = true, isActionMove = false, isScale = false;
	private Camera mCamera;

	boolean XbigY = false;
	float rolateX = 0;
	float rolateY = 0;

	OnViewClickListener onclick = null;

	public RolateImageView(Context context) {
		super(context);
		mCamera = new Camera();
	}

	public RolateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCamera = new Camera();
	}

	public void SetAnimationOnOff(boolean oo) {
		isOpenAnimation = oo;
	}

	public void setOnClickIntent(OnViewClickListener onclick) {
		this.onclick = onclick;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isFirst) {
			isFirst = false;
			init();
		}
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
	}

	public void init() {
		mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
		mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
		Drawable drawable = getDrawable();
		BitmapDrawable bd = (BitmapDrawable) drawable;
		bd.setAntiAlias(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		if (!isOpenAnimation)
			return true;

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			float X = event.getX();
			float Y = event.getY();
			rolateX = mWidth / 2 - X;
			rolateY = mHeight / 2 - Y;
			XbigY = Math.abs(rolateX) > Math.abs(rolateY) ? true : false;

			isScale = X > mWidth / 3 && X < mWidth * 2 / 3 && Y > mHeight / 3
					&& Y < mHeight * 2 / 3;
			isActionMove = false;

			if (isScale) {
				handler.sendEmptyMessage(1);
			} else {
				rolateHandler.sendEmptyMessage(1);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float x = event.getX();
			float y = event.getY();
			if (x > mWidth || y > mHeight || x < 0 || y < 0) {
				isActionMove = true;
			} else {
				isActionMove = false;
			}

			break;
		case MotionEvent.ACTION_UP:
			if (isScale) {
				handler.sendEmptyMessage(6);
			} else {
				rolateHandler.sendEmptyMessage(6);
			}
			break;
		}
		return true;
	}

	public interface OnViewClickListener {
		public void onClick();
	}

	private Handler rolateHandler = new Handler() {
		private Matrix matrix = new Matrix();
		private float count = 0;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			matrix.set(getImageMatrix());
			switch (msg.what) {
			case 1:
				count = 0;
				BeginRolate(matrix, (XbigY ? count : 0), (XbigY ? 0 : count));
				rolateHandler.sendEmptyMessage(2);
				break;
			case 2:
				BeginRolate(matrix, (XbigY ? count : 0), (XbigY ? 0 : count));
				if (count < getDegree()) {
					rolateHandler.sendEmptyMessage(2);
				} else {
					isFinish = true;
				}
				count++;
				count++;
				break;
			case 3:
				BeginRolate(matrix, (XbigY ? count : 0), (XbigY ? 0 : count));
				if (count > 0) {
					rolateHandler.sendEmptyMessage(3);
				} else {
					isFinish = true;
					if (!isActionMove && onclick != null) {
						onclick.onClick();
					}
				}
				count--;
				count--;
				break;
			case 6:
				count = getDegree();
				BeginRolate(matrix, (XbigY ? count : 0), (XbigY ? 0 : count));
				rolateHandler.sendEmptyMessage(3);
				break;
			}
		}
	};

	private synchronized void BeginRolate(Matrix matrix, float rolateX,
			float rolateY) {
		// Bitmap bm = getImageBitmap();
		int scaleX = (int) (mWidth * 0.5f);
		int scaleY = (int) (mHeight * 0.5f);
		mCamera.save();
		mCamera.rotateX(rolateY > 0 ? rolateY : -rolateY);
		mCamera.rotateY(rolateX < 0 ? rolateX : -rolateX);
		mCamera.getMatrix(matrix);
		mCamera.restore();
		// 控制中心点
		if (rolateX > 0 && rolateX != 0) {
			matrix.preTranslate(-mWidth, -scaleY);
			matrix.postTranslate(mWidth, scaleY);
		} else if (rolateY > 0 && rolateY != 0) {
			matrix.preTranslate(-scaleX, -mHeight);
			matrix.postTranslate(scaleX, mHeight);
		} else if (rolateX < 0 && rolateX != 0) {
			matrix.preTranslate(-0, -scaleY);
			matrix.postTranslate(0, scaleY);
		} else if (rolateY < 0 && rolateY != 0) {
			matrix.preTranslate(-scaleX, -0);
			matrix.postTranslate(scaleX, 0);
		}
		setImageMatrix(matrix);
	}

	private Handler handler = new Handler() {
		private Matrix matrix = new Matrix();
		private float s;
		int count = 0;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			matrix.set(getImageMatrix());
			switch (msg.what) {
			case 1:
				if (!isFinish) {
					return;
				} else {
					isFinish = false;
					count = 0;
					s = (float) Math.sqrt(Math.sqrt(minScale));
					BeginScale(matrix, s);
					handler.sendEmptyMessage(2);
				}
				break;
			case 2:
				BeginScale(matrix, s);
				if (count < 4) {
					handler.sendEmptyMessage(2);
				} else {
					isFinish = true;
					if (!isActionMove && onclick != null) {
						onclick.onClick();
					}
				}
				count++;
				break;
			case 6:
				if (!isFinish) {
					handler.sendEmptyMessage(6);
				} else {
					isFinish = false;
					count = 0;
					s = (float) Math.sqrt(Math.sqrt(1.0f / minScale));
					BeginScale(matrix, s);
					handler.sendEmptyMessage(2);
				}
				break;
			}
		}
	};

	private synchronized void BeginScale(Matrix matrix, float scale) {
		int scaleX = (int) (mWidth * 0.5f);
		int scaleY = (int) (mHeight * 0.5f);
		matrix.postScale(scale, scale, scaleX, scaleY);
		setImageMatrix(matrix);
	}

	public int getDegree() {
		return rotateDegree;
	}

	public void setDegree(int degree) {
		rotateDegree = degree;
	}

	public float getScale() {
		return minScale;
	}

	public void setScale(float scale) {
		minScale = scale;
	}
}
