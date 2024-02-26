package com.contract.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;

public class HttpUtil {
  public static JSONArray callApi(String urlStr) {
    try {
      System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
      URL url = new URL(urlStr);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setDoOutput(true);
      con.setUseCaches(false);
      con.setReadTimeout(50000);
      con.setConnectTimeout(50000);
      con.setRequestProperty("User-Agent", "Mozilla/5.0");
      con.connect();
      int status = con.getResponseCode();
      if (status == 200) {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"), 1024 * 1024);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return new JSONArray(content.toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
