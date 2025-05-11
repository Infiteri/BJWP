package org.example;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Window {
    private JFrame frame;

    public Window(int width, int height)
    {
        frame = new JFrame();
        frame.setResizable(true);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = frame.getContentPane();
        c.setLayout(new FlowLayout());

        JTextField inputCityName = new JTextField(25);
        c.add(inputCityName);

        JButton btn = new JButton("Calculate");
        c.add(btn);

        JLabel outputLabel = new JLabel("");
        c.add(outputLabel);

        // Add callback
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = inputCityName.getText();
                System.out.println("City entered: " + city);

                WeatherAPIWrapper wrapper = new WeatherAPIWrapper();
                wrapper.LoadDataForCity(city);
                try {
                    WeatherData data = wrapper.LoadWeatherData();

                    outputLabel.setText("<html><br>Time: " + data.Time + "<br>Humidity: " + data.RelativeHumidity + "<br>Temperature: " + data.Temperature + "<br>Wind Speed: " + data.WindSpeed + "</html>");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }
}
