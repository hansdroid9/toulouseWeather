package com.example.weather.presentation.utils

import android.widget.ImageView
import com.example.weather.data.room.WeatherEntity
import java.time.format.DateTimeFormatter

class utils {
    companion object{


        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        fun getIconRessource(weatherForcast:WeatherEntity):String {
            var iconRessource: String
            /**check if day or night **/
            if (weatherForcast.isDay.equals(1)) {//1 = Yes 0 = No
                // it is day time
                /**sort the data **/
                val iconNum=getCharsBetween("/day/",".png" , weatherForcast.conditionIcon)
                /**append '_' and 'm' ressource data**/
                iconRessource = appendStartEndChars('_', 'm', iconNum)
            }else{
                //it is night time
                /**sort the data **/
                val iconNum=getCharsBetween("/night/",".png" , weatherForcast.conditionIcon)
                /**append '_' and 'n' ressource data**/
                iconRessource = appendStartEndChars('_', 'n', iconNum)
            }
            return iconRessource
        }
        /**sort string between 2 string pattern**/
        fun getCharsBetween(startDelimiter: String, endDelimiter: String, inputString: String): String {
            val startIndex = inputString.indexOf(startDelimiter) + startDelimiter.length
            val endIndex = inputString.indexOf(endDelimiter, startIndex)

            return inputString.substring(startIndex, endIndex)
        }
        /**append character at the start and at the end of a string**/
        fun appendStartEndChars(startChar: Char, endChar: Char, inputString: String): String {
            val outputString = startChar + inputString + endChar
            return outputString
        }
        /**set drawable src with a string name**/
        fun setImageResource(imageView: ImageView, drawableName: String) {
            val drawableResourceId = imageView.context.resources.getIdentifier(drawableName, "drawable", imageView.context.packageName)
            imageView.setImageResource(drawableResourceId)
        }

    }
}