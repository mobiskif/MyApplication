package ru.mobiskif

import android.database.MatrixCursor
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Hub {

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
            //conn.doInput = true

            //передача запроса
            val out = DataOutputStream(conn.outputStream)
            val outputStream = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
            outputStream.write(body)
            outputStream.flush()
            outputStream.close()
            //Log.e("jop","Запрос= " + body.length + " bytes, " + body);

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
            Log.e("jop", "$action= " + sb.length + " bytes, " + sb)

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

    fun GetDistr(action: String): List<String> {
        var ret = arrayListOf<String>()
        val query =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
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
                                ret.add(row[1].toString())
                                //Log.d("jop", text)
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
            return listOf("err1", "err2")
        }

        return ret.toMutableList()
    }

    fun GetLpu(action: String, idDistrict: Int): List<String> {
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
                            "Chief" -> {
                            }
                            "Contact" -> {
                            }
                            "Distric" -> {
                            }
                            "EMail" -> {
                            }
                            "IdLPU" -> row[0] = text
                            "ID" -> {
                            }
                            "Org_Address" -> {
                            }
                            "LPUFullName" -> row[1] = text
                            "LPUShortName" -> {
                                row[2] = text
                                if (row[0] != null) mc.addRow(row)
                                ret.add(row[2].toString())
                            }
                            "WWW" -> {
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
            return listOf("err1", "err2")
        }

        return ret.toMutableList()
    }

    fun GetSpec(action: String, idLPU: Int): List<String> {
        var ret = arrayListOf<String>()
        val idPat = "452528"

        val query =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <tem:GetSpesialityList>\n" +
                    "         <tem:idLpu>" + idLPU + "</tem:idLpu>\n" +
                    "         <tem:idPat>" + idPat + "</tem:idPat>\n" +
                    "         <tem:guid>6b2158a1-56e0-4c09-b70b-139b14ffee14</tem:guid>\n" +
                    "      </tem:GetSpesialityList>\n" +
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
                            "CountFreeParticipantIE" -> row[2] = text
                            "IdSpesiality" -> row[0] = text
                            "NameSpesiality" -> {
                                row[1] = text
                                mc.addRow(row)
                                ret.add(row[1].toString())
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
            return listOf("err1", "err2")
        }

        return ret.toMutableList()
    }

}