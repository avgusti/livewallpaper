package com.avgusti.android.util.Colors;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class SaturationColorFilter {
public static ColorFilter getFilter(float sat)
{
	
	ColorMatrix colorMatrix=new ColorMatrix();
	colorMatrix.reset();
	colorMatrix.setSaturation(sat);
	ColorFilter colorFilter= new ColorMatrixColorFilter(colorMatrix);
	return colorFilter;
	}
}
