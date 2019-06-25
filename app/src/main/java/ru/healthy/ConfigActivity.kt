package ru.healthy

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * The configuration screen for the [myWidget] AppWidget.
 */
class ConfigActivity : AppCompatActivity() {

    internal var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    internal lateinit var mAppWidgetText: EditText


    internal var mOnClickListener: View.OnClickListener = View.OnClickListener {
        val context = this

        // When the button is clicked, store the string locally
        val widgetText = mAppWidgetText.text.toString()
        savePref(context, mAppWidgetId, widgetText)

        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(this)
        myWidget.loadPrefs(context, appWidgetManager, mAppWidgetId)
        //val appWidgetManager = AppWidgetManager.getInstance(context)
        //myWidget.loadPrefs(context, appWidgetManager, mAppWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()

    }

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.my_widget_configure)

        // Set the result to CANCELED.  This will cause the widget host to cancel out of the widget placement if the user presses the back button.
        setResult(Activity.RESULT_CANCELED)

        findViewById<View>(R.id.add_button).setOnClickListener(mOnClickListener)

        // Find the widget id from the intent.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        mAppWidgetText = findViewById<View>(R.id.appwidget_text) as EditText
        mAppWidgetText.setText(loadTitlePref(this@ConfigActivity, mAppWidgetId))

    }


    companion object {

        val WIDGET_COUNT = "count_"
        val PREFS_NAME = "myWidget"
        val WIDGET_TITLE = "title_"

        // Write the prefix to the SharedPreferences object for this widget
        internal fun savePref(context: Context, appWidgetId: Int, text: String) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.putString(WIDGET_TITLE + appWidgetId, text)
            prefs.putString(WIDGET_COUNT + appWidgetId, text)
            prefs.apply()
        }

        // Read the prefix from the SharedPreferences object for this widget.
        // If there is no preference saved, get the default from a resource
        internal fun loadTitlePref(context: Context, appWidgetId: Int): String? {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val titleValue = prefs.getString(WIDGET_TITLE + appWidgetId, null)
            return titleValue
        }

        internal fun loadCountPref(context: Context, appWidgetId: Int): Int {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val countValue = prefs.getInt(WIDGET_COUNT + appWidgetId, 0)
            return countValue
        }

        internal fun deletePref(context: Context, appWidgetId: Int) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.remove(WIDGET_TITLE + appWidgetId)
            prefs.remove(WIDGET_COUNT + appWidgetId)
            prefs.apply()
        }
    }

}

