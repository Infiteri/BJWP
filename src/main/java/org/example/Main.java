package org.example;

import java.util.Scanner;

public class Main {
    private static Window window;

    public static void Display(WeatherData data) {
        System.out.println("Current Time: " + data.Time);
        System.out.println("Current Temperature (C): " + data.Temperature);
        System.out.println("Relative Humidity: " + data.RelativeHumidity);
        System.out.println("Weather Description: " + data.WindSpeed);
    }

    public static void main(String[] args) {
        window = new Window(1080, 720);
/*
        try {
            Scanner scanner = new Scanner(System.in);
            String city;
            do {
                System.out.println("==============================");
                System.out.println("Write city name: (No for Exit)");
                city = scanner.nextLine();

                if(city.equalsIgnoreCase("No")) break;

                WeatherAPIWrapper weth = new WeatherAPIWrapper();
                weth.LoadDataForCity(city);

                try {
                    WeatherData data = weth.LoadWeatherData();
                    Display(data);
                } catch(Exception e) {
                    e.printStackTrace();
                }

            } while(!city.equalsIgnoreCase("No"));
        } catch(Exception e) {
            e.printStackTrace();
        } */
    }
}