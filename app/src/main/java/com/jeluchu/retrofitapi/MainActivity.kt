package com.jeluchu.retrofitapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var weatherData: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherData = findViewById(R.id.textView)

        findViewById<View>(R.id.button).setOnClickListener { getCurrentData() }
    }

    private fun getCurrentData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(lat, lon, AppId)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    val stringBuilder = Html.fromHtml("<b>País:</b> " +
                            weatherResponse.sys!!.country +
                            "<br>" +
                            "<b>Temperatura:</b> " +
                            weatherResponse.main!!.temp +
                            "<br>" +
                            "<b>Temperatura(Min):</b> " +
                            weatherResponse.main!!.temp_min +
                            "<br>" +
                            "<b>Temperatura(Max):</b> " +
                            weatherResponse.main!!.temp_max +
                            "<br>" +
                            "<b>Humedad:</b> " +
                            weatherResponse.main!!.humidity +
                            "<br>" +
                            "<b>Presión:</b> " +
                            weatherResponse.main!!.pressure)

                    weatherData!!.text = stringBuilder
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherData!!.text = t.message
            }
        })
    }

    companion object {

        var BaseUrl = "http://api.openweathermap.org/"
        var AppId = "2e65127e909e178d0af311a81f39948c"
        var lat = "40.4167"
        var lon = "-3.7036"
    }

}
