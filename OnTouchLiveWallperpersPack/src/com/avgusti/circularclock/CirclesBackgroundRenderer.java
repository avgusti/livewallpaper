package com.avgusti.circularclock;

import java.util.Random;

import com.avgusti.android.util.Colors.ColorsARGB;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class CirclesBackgroundRenderer {
	private int mWidth;
	private int mHeight;
	private int Rmax;
	private Bitmap bg;
	private Paint paint = new Paint();
	private static Random rnd = new Random();
	private static int base = ColorsARGB.Pink;
	private static int count = 8;
	private static int circles = 5;

	{

		paint.setAntiAlias(true);
	}

	public void updateRect(int width, int height) {
		Canvas canvas = null;
		if (mWidth != width || mHeight != height) {
			mWidth = width;
			mHeight = height;
			Rmax = (mWidth > mHeight ? mHeight : mWidth) / 2;
			Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf
															// types
			bg = Bitmap.createBitmap(mWidth, mHeight, conf); // this creates
																// a
																// MUTABLE
																// bitmap
			canvas = new Canvas(bg);
			canvas.drawColor(base);
			for (int i = 0; i < circles * 10; i++) {
				paintCircle(canvas, paint);
			}
		}

		if (canvas == null)
			canvas = new Canvas(bg);

		for (int i = 0; i < circles; i++) {
			paintCircle(canvas, paint);
		}
	}

	private void paintCircle(Canvas canvas, Paint paint) {
		int x = rnd.nextInt(mWidth * 2);
		int y = rnd.nextInt(mHeight * 2);
		int r = rnd.nextInt(Rmax) + Rmax / 2;
		int c = rnd.nextInt(0x808080) + base - 0x808080;

		for (int j = count; j >= 0; j--) {
			int d = j * (0xff / count * 0x1000000);
			paint.setColor((c - d));
			canvas.drawCircle(x, y, (r * j) / count, paint);
		}
	}

	public void doDraw(Canvas canvas) {
		if (canvas != null && bg != null) {
			canvas.drawBitmap(bg, 0, 0, null);
		}
	}

	public void updateRect() {
		updateRect(mWidth, mHeight);

	}
}
