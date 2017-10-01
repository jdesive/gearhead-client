/*
 * Copyright (C) 2017  GearHead
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.gearhead.utilities.requests;

import com.desive.gearhead.exceptions.IncorrectStatusCodeException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Request {

    public static String HOSTADDRESS = "http://localhost:8080";

    public static String get(String target, Map<String, String> parameters, Map<String, String> headers) throws IOException{
        return request(target, parameters, headers, "", "GET");
    }

    public static String request(String target, Map<String, String> parameters, Map<String, String> headers, String body, String method) throws IOException{

        // Create Url from target
        URL targetUrl = new URL(target + createParameterString(parameters));

        // Create connection
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();

        if(!headers.isEmpty()){
            headers.forEach(connection::setRequestProperty);
        }

        connection.setRequestMethod(method.toUpperCase());
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        // Handle request parameters
        if(body != null && body != "") {
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(body);
            outputStream.flush();
            outputStream.close();
        }

        if(connection.getResponseCode() >= 200 && connection.getResponseCode() < 210) {

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = inputStream.readLine()) != null) {
                response.append(inputLine);
            }
            inputStream.close();

            connection.disconnect();
            return response.toString();
        }else{
            throw new IncorrectStatusCodeException(connection.getURL().toExternalForm(), connection.getResponseCode());
        }
    }

    private static String createParameterString(Map<String, String> parameters){
        StringBuilder paramString = new StringBuilder("?");
        parameters.forEach((k, v) -> {
            paramString.append(k);
            paramString.append("=");
            paramString.append(v);
            paramString.append("&");
        });
        if(!parameters.isEmpty())
            paramString.deleteCharAt(paramString.length()-1);
        return paramString.toString();
    }

}
