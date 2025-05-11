package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPIWrapper {
    private double longitude;
    private double latitude;
    private String loadedCity = "";

    public WeatherAPIWrapper()
    {
    }

    public double GetLongitude() { return longitude; }
    public double GetLatitude() { return latitude; }

    private JSONObject GetLocationData(String city) {
        city = city.replaceAll(" ", "+");

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                city + "&count=1&language=en&format=json";

        try {
            HttpURLConnection connection = FetchAPIConnection(urlString);
            if(connection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            JSONParser parser = new JSONParser();
            JSONObject result = (JSONObject)parser.parse(ReadAPIResponse(connection));

            JSONArray locData = (JSONArray)result.get("results");
            return (JSONObject) locData.get(0);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpURLConnection FetchAPIConnection(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            return con;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String ReadAPIResponse(HttpURLConnection connection) {
        try {
            StringBuilder result = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());

            while(scanner.hasNext()) result.append(scanner.nextLine());

            scanner.close();

            return result.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean LoadDataForCity(String city)
    {
        if(loadedCity.equals(city))
            return true;

        loadedCity = city;
        JSONObject object = (JSONObject)GetLocationData(city);

        longitude = (double)object.get("longitude");
        latitude = (double)object.get("latitude");

        return true;
    }

    public WeatherData LoadWeatherData() throws IOException, ParseException {
        WeatherData data = new WeatherData();

        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";

        HttpURLConnection connection = FetchAPIConnection(url);
        if(connection.getResponseCode() != 200){
            System.out.println("Error: Could not connect to API");
            return null;
        }

        String json = ReadAPIResponse(connection);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);
        JSONObject jsonData = (JSONObject) jsonObject.get("current");

        data.Time = (String)jsonData.get("time");
        data.RelativeHumidity = (long)jsonData.get("relative_humidity_2m");
        data.Temperature = (double)jsonData.get("temperature_2m");
        data.WindSpeed = (double)jsonData.get("wind_speed_10m");

        return data;
    }
}
