package com.avgusti.circularwallpaper;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ViewThread extends Thread {
    private Panel mPanel;
    private SurfaceHolder mHolder;
    private Boolean mRun = false;
private long mStartTime;
private long mElapsed;
    
    public ViewThread(Panel panel) {
        mPanel = panel;
        mHolder = mPanel.getHolder();
    }
    
    public void setRunning(boolean run) {
        mRun = run;
    }
    
    @Override
    public void run() {
        Canvas canvas = null;
        mStartTime = System.currentTimeMillis();
        
        while (mRun) {
            canvas = mHolder.lockCanvas();
            if (canvas != null) {
               // mPanel.animate(mElapsed);
            	try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					//do nothing
					//just redraw
					
				}
                mPanel.doDraw(mElapsed, canvas);
                mElapsed = System.currentTimeMillis() - mStartTime;
                mHolder.unlockCanvasAndPost(canvas);
            }
            mStartTime = System.currentTimeMillis();
        }
        
    }
}