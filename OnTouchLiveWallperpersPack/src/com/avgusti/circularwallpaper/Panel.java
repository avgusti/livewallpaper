package com.avgusti.circularwallpaper;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {

	
	private CirclesBackgroundRenderer backgroundRenderer = new CirclesBackgroundRenderer();

	private ViewThread mThread;

	public Panel(Context context) {
		super(context);
		getHolder().addCallback(this);
		mThread = new ViewThread(this);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		backgroundRenderer.updateRect(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!mThread.isAlive()) {
			mThread = new ViewThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

	public void doDraw(long elapsed, Canvas canvas) {
		backgroundRenderer.doDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (backgroundRenderer) {
			backgroundRenderer.updateRect();
		}
		return super.onTouchEvent(event);
	}

}
