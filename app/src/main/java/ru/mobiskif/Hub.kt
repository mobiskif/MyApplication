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
            //Log.e("jop","==== Запрос= $action = " + body.length + " bytes, " + body);

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
                event = myParser.next()
            }
        } catch (e: Exception) {
            Log.e("jop", "Ошибка парсинга SOAP " + e.toString())
            return listOf("err1", "err2")
        }

        return ret.toMutableList()
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
                            "LPUShortName" -> set["LPUShortName"] = text
                            "LPUType" -> { set["LPUType"] = text
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

    fun GetSpec(action: String, idLPU: Int): MutableList<Map<String, String>>{
        var ret = arrayListOf<String>()
        val idPat = "452528"

        val query =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <tem:GetSpesialityList>" +
                        "         <tem:idLpu>" + idLPU + "</tem:idLpu>" +
                        "         <tem:idPat>" + idPat + "</tem:idPat>" +
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

    fun CheckPat(action: String, IdLPU: Int, args: Array<String?>): MutableMap<String, String> {

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
                            "ErrorDescription" -> set["ErrorDescription"] = text
                            "IdHistory" -> set["IdHistory"] = text
                            "Success" -> set["Success"] = text
                            "IdPat" -> { set["IdPat"] = text
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
            return mutableMapOf()
        }

        //return ret.toMutableList()
        return result
    }

}