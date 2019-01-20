package ru.mobiskif

import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Hub(val context: Context) {

    private fun readSOAP(body: String, action: String): XmlPullParser? {
        try {
            val url = URL("https://api.gorzdrav.spb.ru/Service/HubService.svc")
            val conn = url.openConnection() as HttpsURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Accept-Encoding", "gzip,deflate")
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8")
            conn.setRequestProperty("SOAPAction", "http://tempuri.org/IHubService/$action")
            conn.setRequestProperty("Content-Length", body.toByteArray().size.toString())
            conn.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)")
            conn.doOutput = true

            //передача запроса
            val out = DataOutputStream(conn.outputStream)
            val outputStream = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
            outputStream.write(body)
            outputStream.flush()
            outputStream.close()
            Log.e("jop","Запрос= " + body.length + " bytes, " + body);

            //чтение ответа
            conn.connect()
            var line: String?
            val isr = InputStreamReader(conn.inputStream)
            val reader = BufferedReader(isr)
            val sb = StringBuilder()
            line = reader.readLine()
            while (line  != null) {
                sb.append(line)
                line = reader.readLine()
            }
            isr.close()
            reader.close()
            Log.e("jop", "Ответ= " + sb.length + " bytes, " + sb)

            //препарсинг
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true// включаем поддержку namespace (по умолчанию выключена)
            val xpp = factory.newPullParser()
            xpp.setInput(StringReader(sb.toString()))
            return xpp
        } catch (e: Exception) {
            Log.d("jop", "Ошибка чтения SOAP " + e.toString())
            return null
        }

    }

    fun getDisrictList(): Cursor {
        val action = "GetDistrictList"
        val query = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <tem:GetDistrictList>\n" +
                "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>\n" +
                "      </tem:GetDistrictList>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>"

        val myParser = readSOAP(query, action)

        val from = arrayOf("_ID", "column1", "column2", "column3")
        var event: Int
        var text: String? = null
        val mc = MatrixCursor(from)
        val row = arrayOfNulls<Any>(from.size)
        //mc.addRow(row);
        try {
            event = myParser!!.getEventType()
            while (event != XmlPullParser.END_DOCUMENT) {
                val name = myParser!!.getName()
                when (event) {
                    XmlPullParser.START_TAG -> {
                    }

                    XmlPullParser.TEXT -> text = myParser!!.getText()

                    XmlPullParser.END_TAG -> {
                        when (name) {
                            "DistrictName" -> {
                                row[1] = text
                                row[2] = "район"
                            }
                            "IdDistrict" -> {
                                row[0] = text
                                mc.addRow(row)
                            }
                            else -> {
                            }
                        }
                        text = null
                    }
                }
                event = myParser!!.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            //return context.resources.getStringArray(R.array.area).toMutableList()
        }

        return mc
    }

}