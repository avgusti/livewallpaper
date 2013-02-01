package com.avgusti.circularclock;

//import java.text.DateFormatSymbols;
//import java.util.Calendar;
//import java.util.Locale;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.BitmapShader;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.ColorFilter;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.Path.FillType;
//import android.graphics.RectF;
//import android.graphics.Shader;
//import android.graphics.Typeface;
//import android.graphics.drawable.ShapeDrawable;
//import android.graphics.drawable.shapes.PathShape;
//
//import com.avgusti.android.util.Colors.ColorsARGB;
//import com.avgusti.android.util.Colors.SaturationColorFilter;

public class CircularClockDesatRenderer {
//	public static int mWidth,sWidth;
//	public static int mHeight,sHeight;
//	public static int Rmax;
//	public static int centerX;
//	public static int centerY;
//	public static ColorFilter desatFilter = SaturationColorFilter.getFilter(0);
//	private Context context;
//	private Paint mPaint = new Paint();
//	private Paint paintS = new Paint();
//	private Paint paintDS = new Paint();
//	private Paint paintt = new Paint();
//	private Path arrow = new Path();
//	String weekdays[];
//	String monthes[];
//	private ShapeDrawable arrowDrawable;
//	private Matrix trMatrix=new Matrix();
//	public CircularClockDesatRenderer(Context ctx) {
//		context = ctx;
//		arrow.moveTo(0, 0);
//		arrow.lineTo(0, 2);
//		arrow.lineTo(1, 1);
//		arrow.setFillType(FillType.EVEN_ODD);
//		PathShape arrowShape = new PathShape(arrow, 2, 2);
//		arrowDrawable = new ShapeDrawable(arrowShape);
//		Locale usersLocale = Locale.getDefault();
//
//		DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
//		weekdays = dfs.getWeekdays();
//		monthes = dfs.getMonths();
//		paintt.setAntiAlias(true);
//		paintt.setTypeface(Typeface.DEFAULT_BOLD);
//		paintt.setColor(ColorsARGB.Slate_Gray);
//		mPaint.setColor(Color.WHITE);
//		paintDS.setColorFilter(desatFilter);
//		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.celtic);
//		BitmapShader shader = new BitmapShader(bitmap,Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//		Matrix trMatrix=new Matrix();
//		trMatrix.postScale(1, 1, 0,0);
//		shader.setLocalMatrix(trMatrix);
//		paintDS.setShader(shader);
//		paintDS.setStyle(Paint.Style.STROKE);
//		sHeight=bitmap.getHeight();
//		sWidth=bitmap.getWidth();
//		paintS.setShader(shader);
//		paintS.setAntiAlias(true);
//		// paint.setShadowLayer(Rmax/10, 0, 0, shadow);
//		paintS.setStrokeWidth(Rmax / 12);
//		paintDS.setStrokeWidth(paintS.getStrokeWidth());
//		paintS.setStrokeCap(Paint.Cap.ROUND);
//		paintS.setStyle(Paint.Style.STROKE);
//		paintS.setShader(paintDS.getShader());
//		
//	}
//
//	public void updateRect(int width, int height) {
//		mWidth = width;
//		mHeight = height;
//		Rmax = (mWidth > mHeight ? mHeight : mWidth) / 2;
//		centerX = mWidth / 2;
//		centerY = mHeight / 2;
//		float sx,sy,px,py;
//		sx=sy=1f*(2*Rmax)/sHeight;
//		px=mWidth>mHeight?(mWidth-sWidth)/2:0;
//		py=mWidth>mHeight?0:(mHeight-sHeight)/2;
//		trMatrix.reset();
//		trMatrix.postTranslate(-sWidth/2, -sHeight/2);
//		trMatrix.postScale(sx, sy);
//		trMatrix.postTranslate(sWidth*sx/2, sHeight*sy/2);
//		trMatrix.postTranslate(px/sx, py/sy);
//		paintDS.getShader().setLocalMatrix(trMatrix);
//	}
//
//	public void doDraw(long elapsed, Canvas canvas) {
//		canvas.drawColor(Color.BLACK);
//		//canvas.drawPaint(paintS);
//		Calendar cl = Calendar.getInstance();
//		// long year=cl.get(Calendar.YEAR);
//		int month = cl.get(Calendar.MONTH);
//		long dayofmonth = cl.get(Calendar.DAY_OF_MONTH);
//		long dayofyear = cl.get(Calendar.DAY_OF_YEAR);
//		long hour = cl.get(Calendar.HOUR_OF_DAY);
//		long minute = cl.get(Calendar.MINUTE);
//		long second = cl.get(Calendar.SECOND);
//		long msecond = cl.get(Calendar.MILLISECOND);
//
//		// int firstDayOfWeek = cl.getFirstDayOfWeek();
//		drawTimeArc(canvas, second * 1000 + msecond, 60000, 0.9f,
//				ColorsARGB.Red, Color.WHITE, second + " sec");
//		drawTimeArc(canvas, minute, 60, 0.8f, ColorsARGB.Orange, Color.WHITE,
//				minute + " min");
//		drawTimeArc(canvas, hour, 24, 0.7f, ColorsARGB.Yellow, Color.WHITE,
//				hour + " hour");
//		drawTimeArc(canvas, dayofmonth, 30, 0.6f, ColorsARGB.Green,
//				Color.WHITE,
//				dayofmonth + " " + weekdays[cl.get(Calendar.DAY_OF_WEEK)]);
//		drawTimeArc(canvas, month, 12, 0.5f, ColorsARGB.Cornflower_Blue,
//				Color.WHITE, monthes[month]);
//		drawTimeArc(canvas, dayofyear, 365, 0.4f, ColorsARGB.Blue_Violet,
//				Color.WHITE, "Day " + dayofyear);
//
//		canvas.drawText("FPS: " + Math.round(1000f / elapsed), 10, 10, mPaint);
//	}
//
//	private void drawTimeArc(Canvas canvas, long timePart, int timeDivisor,
//			float radiusFactor, int color, int shadow, String text) {
//		int radius = (int) (Rmax * radiusFactor);
//		int left = centerX - radius;
//		int right = centerX + radius;
//		int top = centerY - radius;
//		int bottom = centerY + radius;
//		RectF rect = new RectF(left, top, right, bottom);
//		paintS.setColor(color);
//		long angle = 360 * timePart / timeDivisor;
//		canvas.drawArc(rect, 0, 360, false, paintDS);
//		canvas.drawArc(rect, 270, angle, false, paintS);
//		Path path = new Path();
//		path.addArc(rect, 275, 359);
//		canvas.drawTextOnPath(text, path, 0, 5, paintt);
//		arrowDrawable.setBounds(centerX, top - 5, centerX + 10, top + 5);
//		arrowDrawable.getPaint().set(paintt);
//		arrowDrawable.draw(canvas);
//	}
}
