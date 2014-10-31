
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


package com.gottibujiku.android.ios7cal.util;

import android.content.Context;

/**
 * This class holds one method,which help us identify the screen size of our device
 * so that we can use that to set the text size on the buttons of our calculator
 * so that the UI doesn't look distorted.
 * If you will find the need to add another method that is not basic to the functioning
 * of our application you can add it here.
 * 
 * @author Newton Bujiku
 * @since 2014
 */
public class Util {


	/**
	 * 
	 * This method returns a float value depending on the density pixels
	 * of the device on which this application is running.We use this value to
	 * set the text size of the buttons.
	 * 
	 * @param  context - the information about the environment that the application
	 *          is running on.
	 * 
	 */
	public static float getTextSize(Context context){

		
		/*
		 * The next instruction invokes the getResources() method of the context object
		 * then it gets the Display Metrics and get the density
		 * which is a ratio of the DPI of the device to 160
		 */
		float screenDensity = context.getResources().getDisplayMetrics().density;


		/*
		 * We check to determine the DPI and return a value accordingly.
		 * This helps us set the size dynamically instead of having different layouts 
		 * for each screen size
		 * 
		 * Attention we want float to be returned not double,so we add f behind the number
		 */
		if(screenDensity>=4.0){

			return 90.0f;

		}else if (screenDensity>=3.0){
			return 70.0f;

		}else if (screenDensity>=2.0){
			return 60.0f;

		}else if(screenDensity>=1.5){
			return 50.0f;

		}else if (screenDensity>=1.0){
			return 40.0f;

		}

		return 30.0f;		
	}

}
