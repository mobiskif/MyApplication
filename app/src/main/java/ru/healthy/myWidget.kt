package ru.healthy

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.RemoteViews
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import ru.healthy.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ConfigActivity]
 */
class myWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            ConfigActivity.deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context)

        // Обновление виджета (вторая зона)
        //val updateIntent = Intent(context, myWidget::class.java)
        //updateIntent.action = UPDATE_ALL_WIDGETS
        //pIntent = PendingIntent.getBroadcast(ctx, widgetID, updateIntent, 0)

        val intent = Intent(context, myWidget::class.java)
        intent.action = UPDATE_ALL_WIDGETS
        intent.putExtra(
            AppWidgetManager.EXTRA_APPWIDGET_IDS,
            intArrayOf(0)
        )
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),75000, pIntent)
        Log.d("jop","alarmStarted")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context)
        val intent = Intent(context, myWidget::class.java)
        intent.action = UPDATE_ALL_WIDGETS
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pIntent)
        Log.d("jop","alarmCancel")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        reciveEvent(context, intent)
    }

    companion object {
        var cnt = 0
        val ACTION_CHANGE = "ru.my.change_count"
        val UPDATE_ALL_WIDGETS = "update_all_widgets"

        var SOAP_URL = "https://api.gorzdrav.spb.ru/Service/HubService.svc?wsdl"
        var SOAP_NAMESPACE = "http://tempuri.org/"
        //var SOAP_METHOD_NAME = "GetDistrictList"
        //var SOAP_METHOD_NAME = "GetLPUList"
        var SOAP_METHOD_NAME = "GetSpesialityList"
        var SOAP_ACTION = "http://tempuri.org/IHubService/$SOAP_METHOD_NAME"

        internal fun updateAppWidget(ctx: Context, appWidgetManager: AppWidgetManager, widgetID: Int) {
            val widgetText = ConfigActivity.loadTitlePref(ctx, widgetID)
            // Construct the RemoteViews object
            val widgetView = RemoteViews(ctx.packageName, R.layout.my_widget)
            widgetView.setTextViewText(R.id.appwidget_text, widgetText)


            val sdf = SimpleDateFormat("dd.MM.yy HH:mm:ss")
            val currentTime = sdf.format(Date(System.currentTimeMillis()))
            widgetView.setTextViewText(R.id.tvTime, currentTime.toString())
            widgetView.setTextViewText(R.id.tvCount, cnt++.toString())

            // Конфигурационный экран (первая зона)
            val configIntent = Intent(ctx, ConfigActivity::class.java)
            configIntent.action = AppWidgetManager.ACTION_APPWIDGET_CONFIGURE
            configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
            var pIntent = PendingIntent.getActivity(
                ctx, widgetID,
                configIntent, 0
            )
            widgetView.setOnClickPendingIntent(R.id.tvPressConfig, pIntent)

            // Обновление виджета (вторая зона)
            val updateIntent = Intent(ctx, myWidget::class.java)
            updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            updateIntent.putExtra(
                AppWidgetManager.EXTRA_APPWIDGET_IDS,
                intArrayOf(widgetID)
            )
            pIntent = PendingIntent.getBroadcast(ctx, widgetID, updateIntent, 0)
            widgetView.setOnClickPendingIntent(R.id.tvPressUpdate, pIntent)

            // Счетчик нажатий (третья зона)
            val countIntent = Intent(ctx, myWidget::class.java)
            countIntent.action = ACTION_CHANGE
            countIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
            pIntent = PendingIntent.getBroadcast(ctx, widgetID, countIntent, 0)
            widgetView.setOnClickPendingIntent(R.id.tvPressCount, pIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(widgetID, widgetView)
        }

        internal fun reciveEvent(context: Context, intent: Intent) {
            // Проверяем, что это intent от нажатия на третью зону
            if (intent.getAction().equals(ACTION_CHANGE, true)) {

                // извлекаем ID экземпляра
                var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
                val extras = intent.getExtras()
                if (extras != null) {
                    mAppWidgetId = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID
                    )

                }
                if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    // Читаем значение счетчика, увеличиваем на 1 и записываем
                    val sp = context.getSharedPreferences(ConfigActivity.PREFS_NAME, Context.MODE_PRIVATE)
                    var cnt = sp.getInt(ConfigActivity.WIDGET_COUNT + mAppWidgetId, 0)
                    sp.edit().putInt(ConfigActivity.WIDGET_COUNT + mAppWidgetId, ++cnt).commit()
                    //cnt++

                    DataLoader().execute()

                    // Обновляем виджет
                    updateAppWidget(context, AppWidgetManager.getInstance(context), mAppWidgetId)
                }
            }

            if (intent.action.equals(UPDATE_ALL_WIDGETS, true)) {
                Log.d("jop", "alarm")
                var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
                val extras = intent.getExtras()
                if (extras != null) {
                    mAppWidgetId = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID
                    )

                }
                Log.d("jop", "id=$mAppWidgetId")
                if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    Log.d("jop", cnt.toString())
                }
/*
                // Обновляем виджет
                val thisAppWidget = ComponentName(context.packageName, this.javaClass.name)
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val ids = appWidgetManager.getAppWidgetIds(thisAppWidget)
                val sp = context.getSharedPreferences(ConfigActivity.PREFS_NAME, Context.MODE_PRIVATE)
                for (mAppWidgetId in ids) {
                    Log.d("jop", "ID=$mAppWidgetId")
                    var cnt = sp.getInt(ConfigActivity.WIDGET_COUNT + mAppWidgetId, 0)
                    sp.edit().putInt(ConfigActivity.WIDGET_COUNT + mAppWidgetId, ++cnt).commit()
                    updateAppWidget(context, AppWidgetManager.getInstance(context), mAppWidgetId)
                }
                Log.d("jop", cnt.toString())

 */
            }
        }

        class DataLoader : AsyncTask<Void, Void, String>() {

            fun process(po: Any, pi: PropertyInfo) {
                if (po is SoapObject) {
                    Log.d("jop", "${pi.name} ....")
                    for (i in 0 until po.propertyCount) process(po.getProperty(i), po.getPropertyInfo(i))
                } else {
                    Log.d("jop", "$pi")
                }
            }

            override fun doInBackground(vararg params: Void): String {
                var strresult = ""
                val request = SoapObject(SOAP_NAMESPACE, SOAP_METHOD_NAME)
                request.addProperty("IdDistrict", 4)
                request.addProperty("idLpu", 27)
                //request.addProperty("idLpu", 565)
                //request.addProperty("idPat", 0)
                request.addProperty("guid", "6b2158a1-56e0-4c09-b70b-139b14ffee14")
                //request.addProperty("idHistory", 0)

                val envelope = SoapSerializationEnvelope(SoapEnvelope.VER10)
                envelope.bodyOut = request
                envelope.implicitTypes = true
                envelope.setOutputSoapObject(request)
                envelope.dotNet = true

                val androidHttpTransport = HttpTransportSE(SOAP_URL)

                try {
                    androidHttpTransport.debug = true
                    androidHttpTransport.call(SOAP_ACTION, envelope)
                    Log.d("jop", "request: " + androidHttpTransport.requestDump)
                    Log.d("jop", "response: " + androidHttpTransport.responseDump)

                    val soapObject = envelope.response as SoapObject
                    for (i in 0 until soapObject.propertyCount) process(
                        soapObject.getProperty(i),
                        soapObject.getPropertyInfo(i)
                    )

                } catch (e: Exception) {
                    Log.e("jop", "requestError: " + androidHttpTransport.requestDump)
                    Log.e("jop", "responseEror: " + androidHttpTransport.responseDump)
                    Log.e("jop", e.toString())
                }
                return strresult;
            }
        }

    }
}

