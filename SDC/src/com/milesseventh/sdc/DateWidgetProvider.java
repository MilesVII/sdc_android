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
import android.view.View;
import android.widget.RemoteViews;

public class DateWidgetProvider extends AppWidgetProvider {
	private String FUCK_ME = "com.milesseventh.sdc.update";
	private PendingIntent roughly;
	
	@Override
	public void onEnabled(Context _ctxt){
		AlarmManager _am = (AlarmManager) _ctxt.getSystemService(Context.ALARM_SERVICE);
		
		Calendar _cal = Calendar.getInstance();
		_cal.setTimeInMillis(System.currentTimeMillis());
		_cal.set(Calendar.HOUR_OF_DAY, 0);
		_cal.set(Calendar.MINUTE, 0);
		roughly = PendingIntent.getBroadcast(_ctxt, 0, new Intent(FUCK_ME), PendingIntent.FLAG_UPDATE_CURRENT);
		onReceive(_ctxt, new Intent(FUCK_ME));
		_am.setInexactRepeating(AlarmManager.RTC, _cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, roughly);
	}
	
	@Override
	public void onDisabled(Context _ctxt){
		AlarmManager _am = (AlarmManager) _ctxt.getSystemService(Context.ALARM_SERVICE);
		
		_am.cancel(roughly);
	}
	
	public void onClick(View _x){
		onReceive(_x.getContext(), new Intent(FUCK_ME));
	}
	
	@Override
	public void onReceive(Context _ctxt, Intent _int){
		super.onReceive(_ctxt, _int);
		if (_int.getAction() != FUCK_ME)
			return;
		AppWidgetManager _apm = AppWidgetManager.getInstance(_ctxt);
		int[] _awi = _apm.getAppWidgetIds(new ComponentName(_ctxt.getPackageName(), getClass().getName()));
		Date CURRENT_DATE = GregorianCalendar.getInstance().getTime();
		for (int _horse: _awi){
			RemoteViews _stampede = new RemoteViews(_ctxt.getPackageName(), R.layout.widget_layout);
			String _date = "Today is ";
			_date +=  MainActivity.getNonaDay(CURRENT_DATE.getDate(), CURRENT_DATE.getMonth() + 1, CURRENT_DATE.getYear() + 1900) + " of Nonember, " + (CURRENT_DATE.getYear() + 1900) + "\n";
			_date += "(" + CURRENT_DATE.getDate() + "." + (CURRENT_DATE.getMonth() + 1) + "." + (CURRENT_DATE.getYear() + 1900) + ")";
			_stampede.setTextViewText(R.id.textOut, _date);
			_apm.updateAppWidget(_horse, _stampede);
		}
	}
}
