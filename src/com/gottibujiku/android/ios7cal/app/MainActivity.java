/*
 Copyright (C) 2014, Newton Bujiku <igotti47@gmail.com>

This program is free software: you can redistribute it and/or modify it under the 
terms of the GNU General Public License as published by the Free Software Foundation, 
either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABIL- ITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program. 
If not, see http://www.gnu.org/licenses/.
 */




package com.gottibujiku.android.ios7cal.app;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gottibujiku.android.ios7cal.customviews.CustomButton;
import com.gottibujiku.android.ios7cal.customviews.CustomTextView;
import com.gottibujiku.android.ios7cal.util.Util;


/**
 * This class,a subclass of the Activity class,it's basically what the use sees and 
 * interact with. All our buttons and display are here. All our operations are done here.
 * We define and override several methods from the super class.
 * This class,apart from inheriting from Activity it implements the View.OnClickListener
 * interface which we implement its onClick(View v) method to handle clicks by the user
 * 
 * 
 * @author Newton Bujiku
 * @since 2013
 */


/*
 * Now i suggest you pay close attention to this class. If you don't understand much of the stuff 
 * here you won't understand the application itself.
 * 
 * WARNING:This application is for educational purposes, it has bugs from hair to toe if it did have
 * the two,i am leaving these bugs for you (the one who has access to this source code) so that
 * you can fix some of them and learn something from it. That's how we learn. And maybe it has
 * bugs because by the time i was making it i was a terrible programmer in both
 * Android and Java.
 * So if you fix any of the bugs just,send me the code and i will plug it in here 
 * 
 * This application was meant to imitate the iOS 7 Calculator,some of the stuff works and 
 * some don't,it's your job to make it reach its dream
 * 
 * 
 * 
 */
public class MainActivity extends Activity implements View.OnClickListener{


	//The next line declares variables for all our Buttons,the names are self
	//explanatory.These are Java objects which we are going to reference to our
	//views
	private  CustomButton buttonZero,buttonOne,buttonTwo,buttonThree,buttonFour,
	buttonFive,buttonSix,buttonSeven,buttonEight,
	buttonNine,buttonAC,buttonPlus,buttonMinus,buttonPercentage,buttonDivision,
	buttonPoint,buttonMultiplication,buttonPositiveNegative,buttonEqual;

	//textDisplay is our display that we use to view both the entered values and the 
	//results
	private  CustomTextView textDisplay;

	//then comes our result variable,just like the name suggest 
	//it is the double value that we use to store the result before 
	//displayin them
	private double  result;
	
	//here is our eneteredString variable,beware we are taking the output from the user
	//String and not as a number
	private String enteredString;
	
	//this variable is gonna hold the operator that the user has selected,
	//which is either +,-,*,/ or =
	private char operator=' ';
	
	//lol it has been almost a year since i wrote this code and it didn't have
	//any comments,and i am too lazy to read it through, so i really don't remember
	//what is the use of the next variable LOL,you find out
	
	//private double latestValue;
	
	//Our SoundPool instance that we are going to use to play the click sound that 
	//iOS uses,the tock.wav sound
	private SoundPool spool;
	
	//the ID that will be passed to the load() method of SoundPool instance
	//to identify the resource 
	private int soundID;
	
	//This declares the object we are going to use to frormat the decimal values
	private DecimalFormat format;

	//This ArrayList object with generics <CustomButton> tells the compiler that
	//it's an ArrayList of CustomButtons.We are going to use this ArrayList for several
	//thing you will see.
	//We could have used an array like CustomButton[] but ArrayList with generics is fast
	//and the good way of doing things for good programmers.
	ArrayList<CustomButton> buttons;	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/*This method will be called instantly by the Android System when it is creating our
		 * Activity. Here we call a couple of methods. To see what the heck each method is about just 
		 * place your mouse on top of its name where it's called or scroll down to where it is
		 * written.
		 */
		
		//Make sure to call super each time in any the methods you override
		super.onCreate(savedInstanceState);
		
		//Here we call this method from the super class and pass it a resource layout 
		//Basically we tell it to make our UI like in that file. You can check this file in
		//the layout folder.We are not passing the file itself,rather a reference.
		setContentView(R.layout.activity_main);
		
		prepareSoundPool();
		findAllViewsById();
		enteredString= new String();
		format=new DecimalFormat("0.########");
		buttons = new ArrayList<CustomButton>();
		addButtonsToArrayList(buttons);

		float size = Util.getTextSize(this);
		setClickListeners(buttons);
		Log.d("Size ", ""+size);
		setTextSizeOnButtons(buttons, size);


	}


	public void findAllViewsById(){

		buttonZero=(CustomButton) findViewById(R.id.button_0);
		buttonOne=(CustomButton) findViewById(R.id.button_1);
		buttonTwo=(CustomButton) findViewById(R.id.button_2);
		buttonThree=(CustomButton) findViewById(R.id.button_3);
		buttonFour=(CustomButton) findViewById(R.id.button_4);
		buttonFive=(CustomButton) findViewById(R.id.button_5);
		buttonSix=(CustomButton) findViewById(R.id.button_6);
		buttonSeven=(CustomButton) findViewById(R.id.button_7);
		buttonEight=(CustomButton) findViewById(R.id.button_8);
		buttonNine=(CustomButton) findViewById(R.id.button_9);
		buttonAC=(CustomButton) findViewById(R.id.button_ac);
		buttonPercentage=(CustomButton) findViewById(R.id.button_percentage);
		buttonMinus=(CustomButton) findViewById(R.id.button_minus);
		buttonMultiplication=(CustomButton) findViewById(R.id.button_multiplication);
		buttonDivision=(CustomButton) findViewById(R.id.button_division);
		buttonPoint=(CustomButton) findViewById(R.id.button_point);
		buttonEqual=(CustomButton) findViewById(R.id.button_equal);
		buttonPlus=(CustomButton) findViewById(R.id.button_plus);
		buttonPositiveNegative=(CustomButton) findViewById(R.id.button_positive_negative);
		textDisplay=(CustomTextView) findViewById(R.id.text_display); 


	}

	/**
	 * This methods prepares the SoundPool.We load our tock.wav sound from the raw folder.
	 * We are using SoundPool and not MediaPlayer because it is a very short sound. 
	 */
	public void prepareSoundPool(){

		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		spool =new SoundPool(1,AudioManager.STREAM_MUSIC,0);
		soundID=spool.load(this,R.raw.tock, soundID);

	}

	public void setClickListeners(ArrayList<CustomButton> buttons){

		/**
		 * Set click listeners for each button on the screen.
		 * Now the importance of having the ArrayList of buttons can be seen here.
		 * Instead of writing the same instruction to each button we have referenced it
		 * to the ArrayList and use the for loop to iterate over it.
		 * 
		 * 
		 */

		
		/*
		 *Yeah yeah,i get it,some of you will be confused by this for loop but that's how you 
		 *become a good programmer. Lemme break it down.
		 *
		 *for (Button button:buttons)
			button.setOnClickListener(this);
		 * 
		 * So this is to say as long as there are still buttons when iterating through
		 * the ArrayList get that button make a reference to a new button #CustomButton  button#
		 * then take a reference to that button and set an on click listener. The setOnClickListener is from
		 * the View class so don't be troubled. And we pass this because this class implements
		 * and the OnClickInterface listener. So from here the Android System knows that all our Buttons
		 * can receive clicks.
		 * 
		 */
		for (CustomButton button:buttons)
			button.setOnClickListener(this);

	}


	@Override
	public void onClick(View v) {



		/*
		 * Good now that all our Buttons(Views) can be clicked,it's time to handle what should be done 
		 * when they are clicked. And for that we implement this method from the View.OnClickListener
		 */
		
		//first we play sound,the click sound
		playTock();

		switch (v.getId()){//we get the id of the View or Button in our case and use it to decide what to do

		case R.id.button_0:
		case R.id.button_1:
		case R.id.button_2:
		case R.id.button_3:
		case R.id.button_4:
		case R.id.button_5:
		case R.id.button_6:
		case R.id.button_7:
		case R.id.button_8:
		case R.id.button_9:

			//for all the buttons from 0 to 9 get the text that is in that button.
			//We set this text in the layout file so we just get it and store it
			
			//We cast the View to CustomButton if you know what Dynamic Linking if you know what i mean
			//we get the text and store it in enteredDigit
			String enteredDigit = ((CustomButton) v).getText().toString();


			if (enteredString.equals("0")){

				//No leading zero
				//We cant let the user input something like 00000 it has no sense
				enteredString=enteredDigit;

			}else { 
				
				//Otherwise we concatenate or accumulate the numbers 
				enteredString += enteredDigit;
			}


			//Then we display it on the screen
			textDisplay.setText(enteredString);
			if (operator=='='){

				//if at this point operator is = we remove it and set operator to ' '
				operator=' ';
			}

			break;


		case R.id.button_ac:
			
			//If it was the C button let's set everything to 0 and start over
			result=0;
			enteredString="0";
			operator=' ';

			textDisplay.setText("0");

			break;

		case R.id.button_percentage:
			//if this button was pressed then we should get that value from the display
			//and get percentage by dividing it to a 100
			enteredString=textDisplay.getText().toString();
			Double myDouble=Double.parseDouble(enteredString);

			if(myDouble==0){

				//if that value is equal to zero no need to divide except set it as it is
				textDisplay.setText(String.valueOf(format.format(myDouble)));
			}else{

				//else we divide by 100 and we format the value by using our DecimalFormat 
				//object before getting it's value to String and display it
				myDouble/=100;
				textDisplay.setText(String.valueOf(format.format(myDouble)));
				enteredString=textDisplay.getText().toString();
				result=0;
			}

			break;

		case R.id.button_point:
			enteredString=textDisplay.getText().toString();

			//if a point was clicked we have to do some check before accepting it
			if(enteredString.endsWith(".")||enteredString.contains(".")){

				//we certainly don't want to have a number that looks like this 
				//35..56 or 35.5.8, these values will throw Exceptions when we try to parse them
				//and send our application crashing
			}else{
				//else lets add it to our input
				enteredString+=".";
				textDisplay.setText(enteredString);
			}
			break;

		case R.id.button_plus:

			//let's try to do some computations if the value of result is zero we'll end 
			//up with the same entered value if not we add
			//then we set the operator to + unless the user changes it
			compute();
			operator='+';
			break;

		case R.id.button_multiplication:

			/*ATTENTION : A POTENTIAL BUG
			 * Since the value of result is 0 once the user presses the = button after this it will show 0
			 * so you can try and fix it,since we don't want 1233* to be equal to 0
			 * .You can try and multiply by 1 to keep the same value
			 */
			
			//then we set the operator to + unless the user changes it
			compute();
			operator='*';
			break;

		case R.id.button_division:
			//By now this should be obvious only in this one the 0 is taken care of
			//so the error message will show on the display if the user follows this sequence 122/ =.
			//But still it should show the same value so another POTENTIAL BUG is here
			compute();
			operator='/';
			break;

		case R.id.button_minus:
			
			//this button will bring no problems since the result value is 0 thus if we have
			//55465- the = we'll end with the same value.
			compute();
			operator='-';
			break;

		case R.id.button_positive_negative:
			
			//no much tod do here just get the value on the screen convert it to double 
			//multiply by - to get a negative value,if we have a - value already Java will take care 
			//that for us and make it +; we don't need to do a check here.
			//Then we format it and display it
			enteredString=textDisplay.getText().toString();
			Double myDouble1=Double.parseDouble(enteredString);
			myDouble1*=(-1);
			myDouble1.toString();
			textDisplay.setText(String.valueOf(format.format(myDouble1)));
			enteredString=String.valueOf(myDouble1);
			break;

		case R.id.button_equal:
			String checker=textDisplay.getText().toString();

			//If our last operation ended with an Error like dividing by 0 we set the text to 0
			//on the display
			if(checker.equals("Error")){//Checks if the string on text view is Error
				textDisplay.setText("0");
			}else{

				compute();


			}
			operator='=';
			break;
		}
	}

	/**
	 * The compute method does all of our operations. It's basically the brain of our
	 * calculator
	 * 
	 */
	private void compute(){
		
		//get the enteredString and parse it to get a double values
		//Remember we can't do calculations with String 
		//and we use double not int or long because we want to handle decimal values too
		Double myDouble=Double.parseDouble(enteredString);
		//after that set the enteredString to 0 ready for the next input
		enteredString="0";	
		
		if(operator==' ') {
			//if there is no operator select just set the same value on the screen
			//after formatting it and getting it string value off course
			result=myDouble;
			textDisplay.setText(String.valueOf(format.format(result)));
		}else if (operator=='+'){
			//Same stuff,add it display it
			result+=myDouble;
			textDisplay.setText(String.valueOf(format.format(result)));

		}else if (operator=='-'){
			//and again subtract it and display it
			result-=myDouble;
			textDisplay.setText(String.valueOf(format.format(result)));
		}else if (operator=='*'){
			//Even more redundancy,multiply it display it
			result*=myDouble;
			textDisplay.setText(String.valueOf(format.format(result)));
		}else if (operator=='/'){
			//I think it's boring now,Divide it and display it
			//only if it's is not infinite and NaN
			//Sorry it's not boring we have something different here
			result/=myDouble;
			
			//Since our implementation could bring problems with the 0
			//Let's show that number only if it is not infinite (case of 1/0) 
			//or NaN(Not a number),i don't know how we could get NaN here LOL
			if (Double.isInfinite(result)||Double.isNaN(result)){
				//if any of the two is true let's show Error
				textDisplay.setText("Error");

			}else {

				//Else let's display it
				textDisplay.setText(String.valueOf(format.format(result)));
			}
		}else if (operator=='='){
			//You can figure this out on your own
			if (result==0){
				textDisplay.setText(String.valueOf(format.format(result)));

			}
			textDisplay.setText(String.valueOf(format.format(result)));

		}



	}
	
	/**
	 * A method that will be called each time once the button is pressed with the
	 * intention of playing the tock sound. We get instance of the AudioManager by asking 
	 * for a System Service in this case AUDIO SERVICE
	 * the we tell our SoundPool to play the sound
	 */

	public void playTock(){

		AudioManager audio=(AudioManager)getSystemService(AUDIO_SERVICE);
		float volume=(float) audio.getStreamVolume(AudioManager.STREAM_MUSIC);
		spool.play(soundID,volume, volume, 1,0, 1f);

	}

	/**
	 * This method iterates on all the Buttons on the screen and setting the size
	 * of the text on each button by invoking the setTExtSize() method of the button
	 * in question.
	 * 
	 * @param buttons ArrayList referencing all the buttons on the screen
	 * @param size    A float value to serve as size of the text on the buttons
	 *                this size will determine how the Buttons will be placed on the
	 *                screen to make the UI good looking
	 */
	public void setTextSizeOnButtons(ArrayList<CustomButton> buttons,float size){

		/* IF you have any problem with the next instruction just check the 
		 * 	setClickListeners(ArrayList<CustomButton> buttons) method. It contains elaboration of this 
		 * method
		 */
		for(CustomButton button:buttons)
			button.setTextSize(size);

	}

	/**
	 * Well as the name suggests,this methods upon its returned it will have made
	 * references to all the buttons on the screen. IT MAKES REFERENCES NOT CREATE NEW BUTTONS..ummhh
	 * technically it creates new Buttons THEY REFERENCE OUR BUTTONS ON THE SCREEN. 
	 * Why do that??You ask. Looks like redundancy but we want to simplify our code
	 * 
	 * For example when we set the listeners or set text size we don't wanna have something like this,
	 * <br>
	 * b0.setOnClickListener(this);<br>
		b1.setOnClickListener(this);<br>
		b2.setOnClickListener(this);<br>
		b3.setOnClickListener(this);<br>
		b4.setOnClickListener(this);<br>
		b5.setOnClickListener(this);<br>
		b6.setOnClickListener(this);<br>
		b7.setOnClickListener(this);<br>
		b8.setOnClickListener(this);<br>
		b9.setOnClickListener(this);<br>
		bac.setOnClickListener(this);<br>
		bplus.setOnClickListener(this);<br>
		bmin.setOnClickListener(this);<br>
		bper.setOnClickListener(this);<br>
		bdiv.setOnClickListener(this);<br>
		bpoint.setOnClickListener(this);<br>
		bmul.setOnClickListener(this);<br>
		bpn.setOnClickListener(this);<br>
		beq.setOnClickListener(this);<br>

	 * Well we had this in the code,i wrote last year,and now that i am a good programmer i decided to dump 
	 * it.Choosing simplicity in code over memory management LOL am not so sure if am a good programmer actually,
	 * you be the judge of that
	 * @param arrayListOfButtons
	 */
	public void  addButtonsToArrayList(ArrayList<CustomButton> arrayListOfButtons){

		//We declare our array here then reference all the buttons 
		CustomButton[] buttons={

				buttonZero,buttonOne,buttonTwo,buttonThree,buttonFour,
				buttonFive,buttonSix,buttonSeven,buttonEight,
				buttonNine,buttonAC,buttonPlus,buttonMinus,buttonPercentage,buttonDivision,
				buttonPoint,buttonMultiplication,buttonPositiveNegative,buttonEqual

		};

		//Then we loop to add then to our ArrayList
		for(CustomButton button:buttons)

			arrayListOfButtons.add(button);

	}

}
