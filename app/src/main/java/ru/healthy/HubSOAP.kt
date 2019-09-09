package ru.healthy

import android.database.MatrixCursor
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

//import javax.swing.JColorChooser.showDialog


class HubSOAP {

    private fun readSOAP(body: String, action: String): XmlPullParser? {
        try {
            val url = URL("https://api.gorzdrav.spb.ru/Service/HubService.svc")
            var conn = url.openConnection() as HttpsURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Accept-Encoding", "gzip,deflate")
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8")
            conn.setRequestProperty("SOAPAction", "http://tempuri.org/IHubService/$action")
            conn.setRequestProperty("Content-Length", body.toByteArray().size.toString())
            conn.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)")
            //conn.doOutput = true
            conn.doInput = true

            //передача запроса
            val out = DataOutputStream(conn.outputStream)
            val outputStream = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
            outputStream.write(body)
            outputStream.flush()
            outputStream.close()
            Log.e("jop", "== Запрос == $action = " + body.length + " bytes, " + body);

            //чтение ответа
            conn.connect()
            var cis = conn.inputStream
            var isr = InputStreamReader(cis)
            var reader = BufferedReader(isr)
            var sb = StringBuilder()
            var line = reader.readLine()
            while (line != null) {
                sb.append(line)
                line = reader.readLine()
            }
            isr.close()
            reader.close()
            Log.e("jop", "== Ответ == $action = " + sb.length + " bytes, " + sb)

            //препарсинг
            var factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true// включаем поддержку namespace (по умолчанию выключена)
            var xpp = factory.newPullParser()
            xpp.setInput(StringReader(sb.toString()))
            return xpp
        } catch (e: Exception) {
            Log.d("jop", "Ошибка чтения SOAP " + e.toString())
            return null
        }

    }

    inner class DataLoader : AsyncTask<Map<String, String>, Int, MutableList<Map<String, String>>>() {
        var SOAP_URL = "https://api.gorzdrav.spb.ru/Service/HubService.svc?wsdl"
        var SOAP_NAMESPACE = "http://tempuri.org/"
        var counter = 0;

        //var result: MutableList<Map<String, String>> = mutableListOf()
        var set: MutableMap<String, String> = mutableMapOf()
        //set["IdDistrict"] = text

        fun process(po: Any, pi: PropertyInfo) {
            publishProgress(++counter)
            if (po is SoapObject) {
                //Log.d("jop", "${pi.name} ....")
                if (set.size>0) {
                    Log.d("jop", set.toString())
                    this.get().add(set)
                    set = mutableMapOf()
                    Log.d("jop", this.get().toString())
                    Log.d("jop", "Set cleared .....")
                }
                for (i in 0 until po.propertyCount) {
                    process(po.getProperty(i), po.getPropertyInfo(i))
                }
            } else {
                Log.d("jop", "${pi.name} = ${pi.value}")
                set[pi.name.toString()] = pi.value.toString()
                //if (pi.name.equals("DistrictName")) set["Name"] = pi.value.toString()
                if (pi.name.equals("DistrictName")) {
                    set["Name"] = pi.value.toString()
                    //result.add(set)
                    //set = mutableMapOf()
                }
            }
        }

        override fun doInBackground(vararg params: Map<String, String>): MutableList<Map<String, String>> {
            //var strresult = mutableListOf()
            val request = SoapObject(SOAP_NAMESPACE, params[0]["SOAP_METHOD_NAME"])
            val SOAP_ACTION = "http://tempuri.org/IHubService/" + params[0]["SOAP_METHOD_NAME"]

            params[1].forEach {
                request.addProperty(it.key, it.value)
            }
            Log.d("jop", request.toString())

            val envelope = SoapSerializationEnvelope(SoapEnvelope.VER10)
            envelope.bodyOut = request
            envelope.implicitTypes = true
            envelope.dotNet = true
            envelope.setOutputSoapObject(request)
            //MarshalBase64().register(envelope)
            //envelope.isAddAdornments = false
            //envelope.headerOut = arrayOfNulls(1)

            val androidHttpTransport = HttpTransportSE(SOAP_URL)

            try {
                androidHttpTransport.debug = true
                androidHttpTransport.call(SOAP_ACTION, envelope)
                //Log.d("jop", "request: " + androidHttpTransport.requestDump)
                //Log.d("jop", "response: " + androidHttpTransport.responseDump)

                val soapObject = envelope.response as SoapObject
                for (i in 0 until soapObject.propertyCount) {
                    process(soapObject.getProperty(i), soapObject.getPropertyInfo(i))
                    //publishProgress(1,2,3);
                }

            } catch (e: Exception) {
                Log.e("jop", "requestError: " + androidHttpTransport.requestDump)
                Log.e("jop", "responseEror: " + androidHttpTransport.responseDump)
                Log.e("jop", e.toString())
            }

            return mutableListOf();
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            //Log.d("jop", "%%%%%%${values[0]}")

        }

        override fun onPostExecute(result: MutableList<Map<String, String>>?) {
            super.onPostExecute(result)
            Log.d("jop", "%%%%%% post exec %%%%%%% $result")
        }

    }

    fun GetDistr(action: String): MutableList<Map<String, String>> {

        var params: Map<String, String> = mapOf(
                //"IdDistrict" to "4",
                "idLpu" to "27",
                "guid" to "6b2158a1-56e0-4c09-b70b-139b14ffee14"
        )

        val a = DataLoader().execute(mapOf("SOAP_METHOD_NAME" to action), params).get()
        Log.d("jop", "============= "+ a.toString())
        return a
        //return DataLoader().execute(mapOf("SOAP_METHOD_NAME" to "GetSpesialityList"), params).get()

        /*
        var ret = arrayListOf<String>()
        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <tem:GetDistrictList>" +
                        "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>" +
                        "      </tem:GetDistrictList>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>"
        val myParser = readSOAP(query, action)

        val from = arrayOf("_ID", "column1", "column2", "column3")
        var event: Int
        var text: String = ""
        val mc = MatrixCursor(from)
        val row = arrayOfNulls<Any>(from.size)
        //mc.addRow(row);
        try {
            event = myParser!!.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                val name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "DistrictName" -> {
                                row[1] = text
                                row[2] = "район"
                                set["Name"] = text
                            }
                            "IdDistrict" -> {
                                row[0] = text
                                mc.addRow(row)
                                ret.add(row[1].toString())
                                set["IdDistrict"] = text
                                result.add(set)
                                set = mutableMapOf()
                                //Log.d("jop", text)
                            }
                            else -> {
                            }
                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return mutableListOf()
        }
*/
        //return mutableListOf()
    }

    fun GetLpu(action: String, idDistrict: Int): MutableList<Map<String, String>> {
        var ret = arrayListOf<String>()
        val districtID = "17"

        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <tem:GetLPUList>" +
                        "         <tem:IdDistrict>${idDistrict + 1}</tem:IdDistrict>" +
                        "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>" +
                        "      </tem:GetLPUList>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>"

        val myParser = readSOAP(query, action)

        val from = arrayOf("_ID", "column1", "column2", "column3")
        var event: Int
        var text: String = ""
        val mc = MatrixCursor(from)
        val row = arrayOfNulls<Any>(from.size)
        //mc.addRow(row);
        var result: MutableList<Map<String, String>> = mutableListOf()
        var set: MutableMap<String, String> = mutableMapOf()
        try {
            event = myParser!!.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                val name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "Description" -> set["Description"] = text
                            "District" -> set["District"] = text
                            "IdLPU" -> set["IdLPU"] = text
                            "LPUFullName" -> set["LPUFullName"] = text
                            "LPUShortName" -> set["Name"] = text
                            "LPUType" -> {
                                set["LPUType"] = text
                                result.add(set)
                                set = mutableMapOf()
                            }

                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return mutableListOf()
        }

        return result
    }

    fun GetSpec(action: String, args: Array<Any>): MutableList<Map<String, String>> {
        var ret = arrayListOf<String>()
        val idPat = args[1] as MutableLiveData<String>
        val idLPU = args[0] as Int

        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <tem:GetSpesialityList>" +
                        "         <tem:idLpu>" + idLPU + "</tem:idLpu>" +
                        "         <tem:idPat>" + idPat.value + "</tem:idPat>" +
                        "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>" +
                        "      </tem:GetSpesialityList>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>"

        val myParser = readSOAP(query, action)

        val from = arrayOf("_ID", "column1", "column2", "column3")
        //var event: Int
        //var text: String? = null
        val mc = MatrixCursor(from)
        val row = arrayOfNulls<Any>(from.size)
        //mc.addRow(row);
        var result: MutableList<Map<String, String>> = mutableListOf()
        var event: Int
        var text: String = ""
        var set: MutableMap<String, String> = mutableMapOf()

        try {
            event = myParser!!.eventType
            while (event != XmlPullParser.END_DOCUMENT) {
                val name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "CountFreeParticipantIE" -> set["CountFreeParticipantIE"] = text
                            "IdSpesiality" -> set["IdSpesiality"] = text
                            "NameSpesiality" -> {
                                row[1] = text
                                set["NameSpesiality"] = text
                                mc.addRow(row)
                                ret.add(row[1].toString())
                                result.add(set)
                                set = mutableMapOf()
                            }
                            else -> {
                            }
                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return mutableListOf()
        }

        return result
    }

    fun GetDoc(action: String, args: Array<Any?>): MutableList<Map<String, String>> {

        val idPat = args[2]
        val specID = args[1]
        val idLPU = args[0]

        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <tem:GetDoctorList>" +
                        "         <tem:idSpesiality>" + specID + "</tem:idSpesiality>" +
                        "         <tem:idLpu>" + idLPU + "</tem:idLpu>" +
                        "         <tem:idPat>" + idPat + "</tem:idPat>" +
                        "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>" +
                        "      </tem:GetDoctorList>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>"

        val myParser = readSOAP(query, action)

        var result: MutableList<Map<String, String>> = mutableListOf()
        var event: Int
        var text: String = ""
        var set: MutableMap<String, String> = mutableMapOf()
        try {
            event = myParser!!.eventType
            //set= mutableMapOf()
            while (event != XmlPullParser.END_DOCUMENT) {
                var name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "AriaNumber" -> set["AriaNumber"] = text
                            "CountFreeParticipantIE" -> set["CountFreeParticipantIE"] = text
                            "CountFreeTicket" -> set["CountFreeTicket"] = text
                            "IdDoc" -> set["IdDoc"] = text
                            "LastDate" -> set["LastDate"] = text
                            "Name" -> set["Name"] = text
                            "NearestDate" -> set["NearestDate"] = text
                            "Snils" -> {
                                set["Snils"] = text
                                result.add(set)
                                set = mutableMapOf()
                            }
                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return mutableListOf()
        }

        //return ret.toMutableList()
        return result
    }

    fun GetHist(action: String, args: Array<Any?>): MutableList<Map<String, String>> {

        val idPat = args[1]
        val idLPU = args[0]

        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <tem:GetPatientHistory>" +
                        "         <tem:idLpu>" + idLPU + "</tem:idLpu>" +
                        "         <tem:idPat>" + idPat + "</tem:idPat>" +
                        "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>" +
                        "      </tem:GetPatientHistory>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>"

        val myParser = readSOAP(query, action)

        var result: MutableList<Map<String, String>> = mutableListOf()
        var event: Int
        var text: String = ""
        var set: MutableMap<String, String> = mutableMapOf()
        try {
            event = myParser!!.eventType
            //set= mutableMapOf()
            while (event != XmlPullParser.END_DOCUMENT) {
                var name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "DateCreatedAppointment" -> set["DateCreatedAppointment"] = text
                            "AriaNumber" -> set["AriaNumber"] = text
                            "Name" -> set["Name"] = text
                            "IdAppointment" -> set["IdAppointment"] = text
                            "NameSpesiality" -> set["NameSpesiality"] = text
                            "UserName" -> set["UserName"] = text
                            "VisitStart" -> {
                                var loc = text.indexOf("T")
                                val date = text.substring(0, loc)
                                var time = text.substring(loc + 1)
                                loc = time.lastIndexOf(":")
                                time = time.substring(0, loc)
                                //row[2] = text.split("T")[0] + "\n"+ text.split("T")[1];
                                //row[2] = "$date $time"


                                set["VisitStart"] = time
                                set["VisitEnd"] = date
                                result.add(set)
                                set = mutableMapOf()
                            }
                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return mutableListOf()
        }

        //return ret.toMutableList()
        return result
    }

    fun GetPat(action: String, IdLPU: Int, args: Array<String?>): MutableMap<String, String> {

        val idPat = "502655"
        val specID = 78
        val idLPU = 174

        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:hub=\"http://schemas.datacontract.org/2004/07/HubService2\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <tem:CheckPatient>" +
                        "         <tem:pat>" +
                        "           <hub:Birthday>" + args[3] + "</hub:Birthday>" +
                        "            <hub:Name>" + args[0] + "</hub:Name>" +
                        "            <hub:SecondName>" + args[2] + "</hub:SecondName>" +
                        "            <hub:Surname>" + args[1] + "</hub:Surname>" +
                        "         </tem:pat>" +
                        "         <tem:idLpu>" + IdLPU + "</tem:idLpu>" +
                        "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>" +
                        "     </tem:CheckPatient>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>"

        val myParser = readSOAP(query, action)

        var result: MutableMap<String, String> = mutableMapOf()
        var event: Int
        var text: String = ""
        var set: MutableMap<String, String> = mutableMapOf()
        try {
            event = myParser!!.eventType
            //set= mutableMapOf()
            while (event != XmlPullParser.END_DOCUMENT) {
                var name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "ErrorDescription" -> {
                                if (text.equals("null")) set["ErrorDescription"] = " "
                                else set["ErrorDescription"] = text
                            }
                            "IdHistory" -> set["IdHistory"] = text
                            "Success" -> set["Success"] = text
                            "IdPat" -> {
                                if (set["Success"] == "true") {
                                    set["IdPat"] = text
                                    set["ErrorDescription"] = " "
                                } else set["IdPat"] = ""
                                //result.add(set)
                                result = set
                                set = mutableMapOf()
                            }
                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            set = mutableMapOf()
            set["IdPat"] = ""
            set["ErrorDescription"] = " Проверьте формат Даты рождения"
            return set
        }

        //return ret.toMutableList()
        return result
    }

    fun GetTalons(action: String, args: Array<Any?>): MutableList<Map<String, String>> {

        val idPat = args[2]
        val idDoc = args[1]
        val idLPU = args[0]

        var s = SimpleDateFormat("yyyy-MM-dd").format(Date())

        val query = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:GetAvaibleAppointments>\n" +
                "         <tem:idDoc>" + idDoc + "</tem:idDoc>\n" +
                "         <tem:idLpu>" + idLPU + "</tem:idLpu>\n" +
                "         <tem:idPat>" + idPat + "</tem:idPat>\n" +
                "         <tem:visitStart>$s</tem:visitStart>\n" +
                "         <tem:visitEnd>2025-12-31</tem:visitEnd>\n" +
                "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>\n" +
                "      </tem:GetAvaibleAppointments>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>"


        val myParser = readSOAP(query, action)

        var result: MutableList<Map<String, String>> = mutableListOf()
        var event: Int
        var text: String = ""
        var set: MutableMap<String, String> = mutableMapOf()
        try {
            event = myParser!!.eventType
            //set= mutableMapOf()
            while (event != XmlPullParser.END_DOCUMENT) {
                var name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "IdAppointment" -> set["IdAppointment"] = text
                            "VisitEnd" -> set["VisitEnd"] = text
                            "VisitStart" -> {
                                var loc = text.indexOf("T")
                                val date = text.substring(0, loc)
                                var time = text.substring(loc + 1)
                                loc = time.lastIndexOf(":")
                                time = time.substring(0, loc)
                                //row[2] = text.split("T")[0] + "\n"+ text.split("T")[1];
                                //row[2] = "$date $time"


                                set["VisitStart"] = time
                                set["VisitEnd"] = date
                                result.add(set)
                                set = mutableMapOf()
                            }
                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return mutableListOf()
        }

        //return ret.toMutableList()
        return result
    }

    fun SetApp(action: String, args: Array<Any?>): MutableMap<String, String> {

        val idPat = args[2]
        val idAppoint = args[1]
        val idLPU = args[0]

        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <tem:SetAppointment>\n" +
                        "         <tem:idAppointment>" + idAppoint + "</tem:idAppointment>\n" +
                        "         <tem:idLpu>" + idLPU + "</tem:idLpu>\n" +
                        "         <tem:idPat>" + idPat + "</tem:idPat>\n" +
                        "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>\n" +
                        "      </tem:SetAppointment>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>"

        val myParser = readSOAP(query, action)

        var result: MutableMap<String, String> = mutableMapOf()
        var event: Int
        var text: String = ""
        var set: MutableMap<String, String> = mutableMapOf()
        try {
            Log.e("jops", query)
            event = myParser!!.eventType
            //set= mutableMapOf()
            while (event != XmlPullParser.END_DOCUMENT) {
                var name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "ErrorDescription" -> {
                                set["ErrorDescription"] = text
                                Log.d("jops", "ошибка=" + text)
                            }
                            "Success" -> {
                                Log.d("jops", "успех=" + text)
                                //set["Success"] = text
                                if (text.equals("true")) set["Success"] = "Талон отложен!"
                                else set["Success"] = "Отказано! " + set["ErrorDescription"]
                                result = set
                                set = mutableMapOf()
                            }
                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return mutableMapOf()
        }

        //return ret.toMutableList()
        return result
    }

    fun RefApp(action: String, args: Array<Any?>): MutableMap<String, String> {

        val idPat = args[2]
        val idAppoint = args[1]
        val idLPU = args[0]

        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <tem:CreateClaimForRefusal>\n" +
                        "         <tem:idLpu>" + idLPU + "</tem:idLpu>\n" +
                        "         <tem:idPat>" + idPat + "</tem:idPat>\n" +
                        "         <tem:idAppointment>" + idAppoint + "</tem:idAppointment>\n" +
                        "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>\n" +
                        "      </tem:CreateClaimForRefusal>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>"

        val myParser = readSOAP(query, action)

        var result: MutableMap<String, String> = mutableMapOf()
        var event: Int
        var text: String = ""
        var set: MutableMap<String, String> = mutableMapOf()
        try {
            Log.e("jops", query)
            event = myParser!!.eventType
            //set= mutableMapOf()
            while (event != XmlPullParser.END_DOCUMENT) {
                var name = myParser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser.text

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "ErrorDescription" -> {
                                set["ErrorDescription"] = text
                                Log.d("jops", "ошибка=" + text)
                            }
                            "Success" -> {
                                Log.d("jops", "успех=" + text)
                                //set["Success"] = text
                                if (text.equals("true")) set["Success"] = "Талон отменен!"
                                else set["Success"] = "Ошибка!\n" + set["ErrorDescription"]
                                result = set
                                set = mutableMapOf()
                            }
                        }
                        text = ""
                    }
                }
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return mutableMapOf()
        }

        //return ret.toMutableList()
        return result
    }


}