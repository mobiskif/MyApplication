package ru.healthy

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE


class MainActivity : AppCompatActivity() {
    private lateinit var model: MyViewModel
    private lateinit var binding: ru.healthy.databinding.ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        //setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        Storer(this).loadModel(model, Storer(this).restoreuser())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.modelmain = model

        setSupportActionBar(toolbar)
        //fab.setColorFilter(Color.WHITE)
        //fab.setOnClickListener { NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0) }
        model.cfam.observe(this, Observer { binding.invalidateAll() })
        model.cerror.observe(this, Observer { Snackbar.make(constraintLayout, it, Snackbar.LENGTH_LONG).show() })
        if (model.cdate.value!!.length <= 8) NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)

        //DataLoader().execute()
    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.draw_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_menu0 -> {
                NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.Fragment0)
                true
            }
            R.id.nav_menu1 -> {
                //NavHostFragment.findNavController(nav_host_fragment).navigate(R.id.activity2)
                val intent = Intent(this, ConfigActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.nav_menu2 -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    inner class DataLoader : AsyncTask<Void, Void, String>() {
        var SOAP_URL = "https://api.gorzdrav.spb.ru/Service/HubService.svc?wsdl"
        var SOAP_NAMESPACE = "http://tempuri.org/"
        //var SOAP_METHOD_NAME = "GetDistrictList"
        //var SOAP_METHOD_NAME = "GetLPUList"
        var SOAP_METHOD_NAME = "GetSpesialityList"
        var SOAP_ACTION = "http://tempuri.org/IHubService/$SOAP_METHOD_NAME"

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

            //envelope.bodyOut = request
            //MarshalBase64().register(envelope)
            //envelope.isAddAdornments = false
            //envelope.headerOut = arrayOfNulls(1)

            val androidHttpTransport = HttpTransportSE(SOAP_URL)

            try {
                androidHttpTransport.debug = true
                androidHttpTransport.call(SOAP_ACTION, envelope)
                Log.d("jop", "request: " + androidHttpTransport.requestDump)
                Log.d("jop", "response: " + androidHttpTransport.responseDump)

                val soapObject = envelope.response as SoapObject
                for (i in 0 until soapObject.propertyCount) process(soapObject.getProperty(i), soapObject.getPropertyInfo(i))

            } catch (e: Exception) {
                Log.e("jop", "requestError: " + androidHttpTransport.requestDump)
                Log.e("jop", "responseEror: " + androidHttpTransport.responseDump)
                Log.e("jop", e.toString())
            }
            return strresult;
        }
    }


}
