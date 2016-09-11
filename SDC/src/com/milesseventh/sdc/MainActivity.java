package com.milesseventh.sdc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private final int FIELD_ND = 0;
	private final int FIELD_NY = 1;
	private final int FIELD_TD = 2;
	private final int FIELD_TM = 3;
	private final int FIELD_TY = 4;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText[] FIELDS = { 
				(EditText)this.findViewById(R.id.field_n_day),
				(EditText)this.findViewById(R.id.field_n_year),
				(EditText)this.findViewById(R.id.field_t_day),
				(EditText)this.findViewById(R.id.field_t_month),
				(EditText)this.findViewById(R.id.field_t_year)
		};
		((Button)findViewById(R.id.button_n2t)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View _filthy) {
				try {
					FIELDS[FIELD_TD].setText("" + getGregDay(
							get(FIELDS[FIELD_ND]),
							get(FIELDS[FIELD_NY])
						));
					FIELDS[FIELD_TM].setText("" + getGregMon(
							get(FIELDS[FIELD_ND]),
							get(FIELDS[FIELD_NY])
						));
					FIELDS[FIELD_TY].setText("" + get(FIELDS[FIELD_NY]));
				} catch(Exception _ex){
					_ex.printStackTrace();
				}
			}
			
		});
		((Button)findViewById(R.id.button_t2n)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View _filthy) {
				try {
					FIELDS[FIELD_ND].setText("" + getNonaDay(
							get(FIELDS[FIELD_TD]),
							get(FIELDS[FIELD_TM]),
							get(FIELDS[FIELD_TY])
						));
					FIELDS[FIELD_NY].setText("" + get(FIELDS[FIELD_TY]));
				} catch(Exception _ex){
					_ex.printStackTrace();
				}
			}
			
		});
		Date _x = new Date();
		FIELDS[FIELD_TD].setText("" + _x.getDate());
		FIELDS[FIELD_TM].setText("" + (_x.getMonth() + 1));
		FIELDS[FIELD_TY].setText("" + (_x.getYear() + 1900));
		((Button)findViewById(R.id.button_t2n)).performClick();
	}
	private int get (EditText _ev){
		return Integer.parseInt(_ev.getText().toString());
	}
	
	public static int getNonaDay(int _d, int _m, int _y){
		//Convert Gregorian date to day of Nonanian calendar
		
		/*int nonaDay = 0;
		Calendar worker = new GregorianCalendar();
		for (int i = 0; i < _m; i++){
			worker.set(_y, i, 1);
			nonaDay += worker.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		worker.set(_y, _m, 1);
		nonaDay += _d;
		return nonaDay;
		*/

		Calendar worker = new GregorianCalendar();
		worker.set(Calendar.DAY_OF_MONTH, _d);
		worker.set(Calendar.MONTH, _m - 1);
		worker.set(Calendar.YEAR, _y);
		return worker.get(Calendar.DAY_OF_YEAR);
	}
	
	public static int getGregDay(int _d, int _y){
		//Convert Nonanian day to day of Gregorian calendar
		Calendar worker = new GregorianCalendar();
		worker.set(Calendar.DAY_OF_YEAR, _d);
		worker.set(Calendar.YEAR, _y);
		return worker.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getGregMon(int _d, int _y){
		//Convert Nonanian day to month of Gregorian calendar
		Calendar worker = new GregorianCalendar();
		worker.set(Calendar.DAY_OF_YEAR, _d);
		worker.set(Calendar.YEAR, _y);
		return worker.get(Calendar.MONTH) + 1;
	}
}

