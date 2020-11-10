package com.example.manualjsonparsing.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.manualjsonparsing.pojo.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*
import java.net.*


object JsonParser {

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    private const val TAG = "HTTP_HANDLER"

    private fun makeUrlCall(requestedUrl: String): Resource<String?> {
        var response: Resource<String>
        try {
            val jsonResponse = URL(requestedUrl).readText()

            response = Resource.success(data = jsonResponse)
            Log.i(TAG, "makeUrlCall: successful $jsonResponse")

        } catch (exception: MalformedURLException) {
            Log.e(TAG, "convertStreamToString, MalformedUrlException: ${exception.message}")
            response = Resource.error(errorMessage = exception.message)
        } catch (exception: ProtocolException) {
            Log.e(TAG, "convertStreamToString, ProtocolException: ${exception.message}")
            response = Resource.error(errorMessage = exception.message)
        } catch (exception: IOException) {
            Log.e(TAG, "convertStreamToString, IOException: ${exception.message}")
            response = Resource.error(errorMessage = exception.message)
        } catch (exception: Exception) {
            Log.e(TAG, "convertStreamToString, GeneralException: ${exception.message}")
            response = Resource.error(errorMessage = exception.message)
        }

        return response

    }


    fun parseJSon(requestUrl: String): LiveData<Resource<List<Contact>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())

            val response = makeUrlCall(requestUrl)
            when (response.status) {
                Status.SUCCESS -> {
                    val mutableContactList = mutableListOf<Contact>()
                    try {
                        val jsonObject = response.data?.let {
                            JSONObject(it)
                        }

                        val contactArray = jsonObject?.getJSONArray("contacts")
                        for (jsonIndex in 0 until contactArray!!.length()) {

                            val contactObject = contactArray.getJSONObject(jsonIndex)

                            val contactId = contactObject.getInt("id")
                            val contactName = contactObject.getString("name")
                            val contactEmail = contactObject.getString("email_address")
                            val contactPhone = contactObject.getString("phone_number")

                            mutableContactList.add(
                                Contact(
                                    contactId,
                                    contactName,
                                    contactEmail,
                                    contactPhone
                                )
                            )

                        }
                        emit(Resource.success(data = mutableContactList.toList()))


                    } catch (exception: Exception) {
                        Log.i(TAG, "parseJSon: Error = ${exception.message}")
                        emit(Resource.error(errorMessage = exception.message))
                    }

                }
                Status.ERROR -> {
                    Log.i(TAG, "parseJSon: Error = ${response.errorMessage}")
                    emit(Resource.error(errorMessage = response.errorMessage))
                }
                Status.LOADING -> emit(Resource.loading())
            }

        }

    fun sendPOSTRequest(
        requestUrl: String,
        contactJsonObject: JSONObject
    ) {
        coroutineScope.launch {
            val contact  = new ArrayList<NameValuePair>()


            val connection = URL(requestUrl).openConnection() as HttpURLConnection

            try {
                connection.apply {
                    requestMethod = "POST"
                    setRequestProperty("Content-Type", "application/json")
                    connectTimeout = 20000
                    readTimeout = 20000
                    doInput = true
                    doOutput = true

                    val wr = OutputStreamWriter(outputStream)
                    wr.write(contactJsonObject.toString())
                    wr.flush()

                    println("URL : $url")
                    println("Response Code : $responseCode")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        println("Response : $response")
                    }
                }
            } catch (malformedUrlException: MalformedURLException) {
                Log.e(TAG, "sendPOSTRequest: $malformedUrlException")
            } catch (ioException: IOException) {
                Log.e(TAG, "sendPOSTRequest: $ioException")
            } catch (exception: Exception) {
                Log.e(TAG, "sendPOSTRequest: $exception")
            }
        }




    }

    fun createJSonObject(name: String, emailAddress: String, phone: String): JSONObject {
        val contactJSONObject = JSONObject()
        contactJSONObject.put("name", name)
        contactJSONObject.put("email_address", emailAddress)
        contactJSONObject.put("phone_number", phone)

        return contactJSONObject
    }
}