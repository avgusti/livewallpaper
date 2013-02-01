package com.avgusti.circularclock;

//import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Handler;
//import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyWallpaperService extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		return new MyWallpaperEngine();

	}

	private class MyWallpaperEngine extends Engine {
		/* (non-Javadoc)
		 * @see android.service.wallpaper.WallpaperService.Engine#onOffsetsChanged(float, float, float, float, int, int)
		 */
		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {
			super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
					xPixelOffset, yPixelOffset);
		}

		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			@Override
			public void run() {
				draw();
			}

		};

		
		private boolean visible = true;
		
		//private boolean touchEnabled=true;
		//private int circlesPerTouch=5;
		
		CircularClockRenderer circularClockRenderer = new CircularClockRenderer(getBaseContext());
		CirclesBackgroundRenderer backgroundRenderer = new CirclesBackgroundRenderer();
		public MyWallpaperEngine() {
//			SharedPreferences prefs = PreferenceManager
//					.getDefaultSharedPreferences(MyWallpaperService.this);
//			circlesPerTouch = prefs.getInt("circlesPerTouch", 5);
//			touchEnabled = prefs.getBoolean("touch", false);

			handler.post(drawRunner);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunner);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			circularClockRenderer.updateRect(width, height);
			backgroundRenderer.updateRect(width, height);
			super.onSurfaceChanged(holder, format, width, height);
		}

		@Override
		public void onTouchEvent(MotionEvent event) {
			backgroundRenderer.updateRect();
//			if (touchEnabled) {
//
//				float x = event.getX();
//				float y = event.getY();
//				SurfaceHolder holder = getSurfaceHolder();
//				Canvas canvas = null;
//				try {
//					canvas = holder.lockCanvas();
//					if (canvas != null) {
//						// canvas.drawColor(Color.BLACK);
//						// circles.clear();
//						// circles.add(new MyPoint(String.valueOf(circles.size()
//						// + 1), x, y));
//						// drawCircles(canvas, circles);
//
//					}
//				} finally {
//					if (canvas != null)
//						holder.unlockCanvasAndPost(canvas);
//				}
//				super.onTouchEvent(event);
//			}
		}

		long mStartTime, mElapsed;

		private void draw() {
			mStartTime = System.currentTimeMillis();
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					backgroundRenderer.doDraw(canvas);
					circularClockRenderer.doDraw(canvas);
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
			mElapsed = System.currentTimeMillis() - mStartTime;
			mStartTime = System.currentTimeMillis();
			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 100);
			}
		}

	}

}
