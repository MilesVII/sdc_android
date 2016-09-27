package com.milesseventh.sdc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DateWidgetProvider extends AppWidgetProvider {
	private String FUCK_ME = "com.milesseventh.sdc.update";
	private PendingIntent lastPI;
	String b;
	@Override
	public void onEnabled(Context _ctxt){
		/*AlarmManager _am = (AlarmManager) _ctxt.getSystemService(Context.ALARM_SERVICE);
		
		Calendar _cal = Calendar.getInstance();
		_cal.setTimeInMillis(System.currentTimeMillis());
		_cal.set(Calendar.HOUR_OF_DAY, 0);
		_cal.set(Calendar.MINUTE, 0);
		_cal.set(Calendar.SECOND, 1);
		_cal.add(Calendar.DATE, 1);
		b = _cal.getTime().toString();
		_am.setInexactRepeating(AlarmManager.RTC, _cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, generatePI(_ctxt));
		refresh(_ctxt);*/
	}
	
	@Override
	public void onDisabled(Context _ctxt){
		//((AlarmManager) _ctxt.getSystemService(Context.ALARM_SERVICE)).cancel(lastPI);
	}
	
	@Override
	public void onUpdate(Context _ctxt, AppWidgetManager _awm, int[] _awi){
		refresh(_ctxt);
	}
	
	@Override
	public void onReceive(Context _ctxt, Intent _int){
		super.onReceive(_ctxt, _int);
		if (_int.getAction() != FUCK_ME)
			return;
		refresh(_ctxt);
	}
	
	private void refresh(Context _ctxt){
		Toast.makeText(_ctxt, 
			   "refresh()", 
			   Toast.LENGTH_SHORT).show();
		AppWidgetManager _apm = AppWidgetManager.getInstance(_ctxt);
		int[] _awi = _apm.getAppWidgetIds(new ComponentName(_ctxt.getPackageName(), getClass().getName()));
		Date CURRENT_DATE = GregorianCalendar.getInstance().getTime();
		RemoteViews _stampede = new RemoteViews(_ctxt.getPackageName(), R.layout.widget_layout);
		for (int _horse: _awi){
			String _date = "Today is ";
			_date +=  MainActivity.getNonaDay(CURRENT_DATE.getDate(), CURRENT_DATE.getMonth() + 1, CURRENT_DATE.getYear() + 1900) + " of Nonember, " + (CURRENT_DATE.getYear() + 1900) + "\n";
			//_date += "(" + CURRENT_DATE.getDate() + "." + (CURRENT_DATE.getMonth() + 1) + "." + (CURRENT_DATE.getYear() + 1900) + ")\n Sync scheduled to:" + b + "\n";
			//_date += "Last sync:" + CURRENT_DATE.toString();
			_stampede.setTextViewText(R.id.textOut, _date);
			_stampede.setOnClickPendingIntent(R.id.textOut, generatePI(_ctxt));
			
			_apm.updateAppWidget(_horse, _stampede);
		}
	}
	
	private PendingIntent generatePI(Context _ctxt){
		lastPI = PendingIntent.getBroadcast(_ctxt, 0, new Intent(_ctxt, this.getClass()).setAction(FUCK_ME), PendingIntent.FLAG_UPDATE_CURRENT);
		return lastPI;
	}
}
