
/*
 Copyright (C) 2014,Newton Bujiku <igotti47@gmail.com>
 
This program is free software: you can redistribute it and/or modify it under the 
terms of the GNU General Public License as published by the Free Software Foundation, 
either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABIL- ITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program. 
If not, see http://www.gnu.org/licenses/.
 */




package com.gottibujiku.android.ios7cal.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * This class,a subclass of a Button class. We extend this class with only one intention
 * of changing the font used on our buttons so that they look better. For that purpose 
 * we use Roboto -Thin font which is located in the Assets/font/ folder
 * 
 * @author Newton Bujiku
 * @since 2014
 */

public class CustomButton extends Button {

	
	/*
	 * The next three constructors only do two things,call constructor of a 
	 * super class and then call our method that sets our typeface to Roboto-Thin
	 * 
	 * 
	 * Constructors:
	 * 
	 * 	public CustomButton(Context context) 
	 * 
	 * 	public CustomButton(Context context, AttributeSet attrs, int defStyle) 
	 * 
	 * 	public CustomButton(Context context, AttributeSet attrs) 
	 */
	
	
	public CustomButton(Context context) {
		super(context);
		setCustomFont(context);
	}

	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context);

	}

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context);


	}


	/**
	 * 	 public void setCustomFont(Context context)
	 * 
	 * 
	 * This method creates an object of Typeface from Asset folder and pass to it path to
	 * the custom font we are going to use,"Roboto-Thin"
	 * First we get an instance of the AssetManager by calling getAssets() from the context
	 * object(the environment of our application).
	 * then we invoke static method Typeface.createFromAsset(AssetMAnager mgr,String path)
	 * passing it the respective values
	 * 
	 * Then once we have our Typeface object we call the method setTypeface(Typeface typeface) 
	 * from the super class and pass it our Typeface object.
	 * You can use super.setTypeface(typeface) if that will make life easy for you
	 * 
	 * @param context the environment of our application
	 */
	public void setCustomFont(Context context){	
		
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Thin.ttf");
		setTypeface(typeface);		
	}

}
