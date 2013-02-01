package com.avgusti.circularclock;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
//import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;

import com.avgusti.android.util.Colors.ColorsARGB;

//import com.avgusti.android.util.Colors.SaturationColorFilter;

public class CircularClockRenderer {
	private int mWidth, mHeight;
	// private int sWidth, sHeight;
	private int Rmax;
	private int centerX;
	private int centerY;
	private int centerTextShiftY, circleTextShiftY;
	private int cirleWidth;
	// private final ColorFilter desatFilter =
	// SaturationColorFilter.getFilter(0);
	private final Context context;
	private final Paint mPaint = new Paint();
	private final Paint paintS = new Paint();
	private final Paint paintDS = new Paint();
	private final Paint paintt = new Paint();
	private final Paint painttc = new Paint();
	private final Paint paintCentr = new Paint();
	private final Path arrow = new Path();
	private final String weekdays[];
	private final String monthes[];
	private ShapeDrawable arrowDrawable;
	private Typeface calendarockFont;
	private boolean drawFullCircle = true;
	private String hs, ms, ys, ds, ss;
	private DecimalFormat formatter = new DecimalFormat("00");

	// private boolean drawFullCircle = true;

	public boolean isDarawFullCircle() {
		return drawFullCircle;
	}

	public void setDarawFullCircle(boolean darawFullCircle) {
		this.drawFullCircle = darawFullCircle;
	}

	public CircularClockRenderer(Context ctx) {

		updateTimeFields();
		context = ctx;
		calendarockFont = Typeface.createFromAsset(context.getAssets(),
				"fonts/Unibody 8-Regular.otf");
		arrow.moveTo(0, 0);
		arrow.lineTo(0, 2);
		arrow.lineTo(1, 1);
		arrow.setFillType(FillType.EVEN_ODD);
		PathShape arrowShape = new PathShape(arrow, 2, 2);
		arrowDrawable = new ShapeDrawable(arrowShape);
		Locale usersLocale = Locale.getDefault();
		DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
		weekdays = dfs.getWeekdays();
		monthes = dfs.getMonths();
		paintt.setAntiAlias(true);
		paintt.setColor(ColorsARGB.Slate_Gray);
		paintt.setColor(ColorsARGB.Black);
		paintt.setTypeface(calendarockFont);
		paintt.setTextSize(10);
		painttc.set(paintt);
		painttc.setTextSize(16);
		mPaint.setColor(Color.WHITE);
		paintS.setStyle(Paint.Style.STROKE);
		paintS.setStrokeCap(Paint.Cap.ROUND);
		paintDS.setStyle(Paint.Style.STROKE);
		paintDS.setStrokeCap(Paint.Cap.ROUND);
		paintS.setAntiAlias(true);
		// paintS.setShadowLayer(Rmax/10, 0, 0, shadow);

		paintDS.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
		paintDS.setColor(ColorsARGB.Wheat);

		paintCentr.setColor(ColorsARGB.Lawn_Green);

	}

	public void updateRect(int width, int height) {
		mWidth = width;
		mHeight = height;
		Rmax = (mWidth > mHeight ? mHeight : mWidth) / 2;
		centerX = mWidth / 2;
		centerY = mHeight / 2;
		paintS.setStrokeWidth(Rmax / 12);
		paintDS.setStrokeWidth(paintS.getStrokeWidth());
		paintCentr.setShader(new RadialGradient(centerX, centerY, Rmax * .4f,
				ColorsARGB.Lawn_Green, ColorsARGB.Dark_Green,
				Shader.TileMode.CLAMP));
		Rect bounds = new Rect(0, 0, 200, 200);
		painttc.getTextBounds("00:00:00", 0, 8, bounds);
		float scale = .6f * Rmax / bounds.width();
		cirleWidth = (Rmax / 12);
		paintS.setStrokeWidth(cirleWidth);
		paintDS.setStrokeWidth(paintS.getStrokeWidth());
		painttc.setTextSize(16 * scale);
		paintt.setTextSize(10 * scale);
		painttc.getTextBounds("00:00:00", 0, 8, bounds);
		centerTextShiftY = bounds.height() / 2;
		paintt.getTextBounds("00:00:00", 0, 8, bounds);
		circleTextShiftY = bounds.height() / 2;
		cacheBitmap = null;
	}

	private final Calendar calendar = Calendar.getInstance();
	private Bitmap cacheBitmap;
	private Canvas canvas;
	private long second;
	private long msecond;
	private int year;
	private int month;
	private long dayofmonth;
	private long dayofyear;
	private long hour;
	private long minute;
	private int daysMonth;
	private int daysYear;
	

	public void doDraw(Canvas c) {
		updateSeconds();
		if (!drawFullCircle) {
			if (cacheBitmap == null) {
				Bitmap.Config conf = Bitmap.Config.ARGB_8888;
				cacheBitmap = Bitmap.createBitmap(mWidth, mHeight, conf);
				canvas = new Canvas(cacheBitmap);
				drawMinutesAndUp(canvas);
			} else if (second == 0) {
				updateTimeFields();
				canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
				drawMinutesAndUp(canvas);
			}
		}
		if (drawFullCircle) {
			updateTimeFields();
			drawMinutesAndUp(c);
		} else {
			c.drawBitmap(cacheBitmap, 0, 0, null);
		}
		
		drawTimeArc(c, second * 1000 + msecond, 60000, 0.9f, ColorsARGB.Red,
				Color.WHITE, second + " sec");
		c.drawCircle(centerX, centerY, Rmax * .34f, paintCentr);
		c.drawText(hs + ":" + ms + ":" + ss, centerX - Rmax * .3f,
				centerY + centerTextShiftY, painttc);
	}

	private void updateTimeFields() {
		calendar.setTimeInMillis(System.currentTimeMillis());
		daysMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		daysYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		month = calendar.get(Calendar.MONTH);
		dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
		dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		year = calendar.get(Calendar.YEAR);
		hs = formatter.format(hour);
		ms = formatter.format(minute);
		ss = formatter.format(second);
		//ys = formatter.format(year);
		
	}

	private void updateSeconds() {
		calendar.setTimeInMillis(System.currentTimeMillis());
		second = calendar.get(Calendar.SECOND);
		msecond = calendar.get(Calendar.MILLISECOND);
		ss = formatter.format(second);
	}

	private void drawMinutesAndUp(Canvas canvas) {
		// long year=calendar.get(Calendar.YEAR);
		// int firstDayOfWeek = calendar.getFirstDayOfWeek();

		drawTimeArc(canvas, minute, 60, 0.8f, ColorsARGB.Orange, Color.WHITE,
				minute + " min");
		drawTimeArc(canvas, hour*60+minute, 24*60, 0.7f, ColorsARGB.Yellow, Color.WHITE,
				hour + " hour");
		drawTimeArc(canvas, dayofmonth, daysMonth, 0.6f, ColorsARGB.Green,
				Color.WHITE,
				dayofmonth + " " + weekdays[calendar.get(Calendar.DAY_OF_WEEK)]);
		drawTimeArc(canvas, month+1, 12, 0.5f, ColorsARGB.Cornflower_Blue,
				Color.WHITE, monthes[month]);
		drawTimeArc(canvas, dayofyear, daysYear, 0.4f, ColorsARGB.Blue_Violet,
				Color.WHITE, "Day " + dayofyear);

	}

	private void drawTimeArc(Canvas canvas, long timePart, int timeDivisor,
			float radiusFactor, int color, int shadow, String text) {
		int radius = (int) (Rmax * radiusFactor);
		int left = centerX - radius;
		int right = centerX + radius;
		int top = centerY - radius;
		int bottom = centerY + radius;
		RectF rect = new RectF(left, top, right, bottom);
		paintS.setColor(color);
		long angle = 360 * timePart / timeDivisor;
		if (drawFullCircle) {
			canvas.drawArc(rect, 0, 360, false, paintDS);
		}
		canvas.drawArc(rect, 270, angle, false, paintS);
		Path path = new Path();
		path.addArc(rect, 270, 359);

		canvas.drawTextOnPath(text, path, cirleWidth, circleTextShiftY, paintt);
		arrowDrawable.setBounds(centerX, top - cirleWidth / 2, centerX
				+ cirleWidth, top + cirleWidth / 2);
		arrowDrawable.getPaint().set(paintt);
		arrowDrawable.draw(canvas);
	}
}
