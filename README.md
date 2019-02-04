# RETROFIT API EXAMPLE

[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![Download](https://img.shields.io/badge/Kotlin-1.3.20-brightgreen.svg?style=flat&logo=kotlin)](https://kotlinlang.org/docs/reference/whatsnew13.html)

### INTRODUCTION
Test to get data from Open Weather Map and show temperature (now, min and max), humidity, pressure and country. For it, I'm using **Retrofit 2** a library for Android. This app is for Kotlin 100%

Implementation library on **build.gradle** (Module:app)
```
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
implementation 'com.squareup.retrofit2:converter-gson:2.5.0' // for convert JSON
```

It's very important to put in the **Manifest** the next permission
```
<uses-permission android:name="android.permission.INTERNET"/>
```

### WEATHER SERVICE

In the **WeatherService** (interface), you GET de data, doing the query to the data that you need
```
@GET("data/2.5/weather?")
fun getCurrentWeatherData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("APPID") app_id: String): Call<WeatherResponse>
```

**¡Not all API's have the same pattern, so keep that in mind!**


### WEATHER RESPONSE

Serialized Name the value that I collect from the API and convert it to a float (in these case)
```
@SerializedName("temp")
var temp: Float = 0.0f

[...]

@SerializedName("country")
var country: String? = null

[...]
```

### MAIN ACTIVITY

I create a function from which we will implement the **base link** where the API is located, and we will access it. In addition we will implement another function in which we will show the data by screen
```
val retrofit = Retrofit.Builder()
    .baseUrl(BaseUrl)
    .addConve
    
val service = retrofit.create(WeatherService::class.java)

val call = service.getCurrentWeatherData(lat, lon, AppId)
```

```
val stringBuilder = Html.fromHtml(
                    "<b>País:</b> " + weatherResponse.sys!!.country + "<br>" +
                    "<b>Temperatura:</b> " +(weatherResponse.main!!.temp - 273 ) + " ºC"
                    [...]
```

And finally, a **Companion Object** is created in which we will introduce the data of the variables, such as the link, base, longitude / latitude, etc.



