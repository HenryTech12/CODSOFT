package com.convert.demo.service;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.*;
import java.io.*;
import java.net.*;

import com.convert.demo.dto.APIData;
import com.convert.demo.dto.Request;
import com.convert.demo.dto.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Service; 

@Service
public class ConverterService {

    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    @Value("${currency-url}")
    private String url;
    @Value("${currency-key}")
    private String apiKey;

    private Logger logger = LoggerFactory.getLogger(ConverterService.class);

    public ConverterService() {
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }
    private String convert(Request request) {

        return null;
    }

    
   public APIData getJSON() throws Exception {
     /* String apiKey = "https://api.currencyapi.com/v3/latest?apikey=cur_live_CqXZRexhp5kSz6F4NUDWzeIxX34G4aacBJsdOoxg";
		URL url = new URL(apiKey);
		HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
		urlc.setRequestMethod("GET");
*/
       HttpRequest httpRequest = HttpRequest.newBuilder()
               .uri(new URI(url.concat(apiKey)))
               .GET()
               .build();

       HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
       System.out.println(httpResponse.body());

       JSONObject obj = new JSONObject(httpResponse.body());
       JSONObject data = obj.getJSONObject("data");

       List<String> countries = new ArrayList<>();
       List<Response> responses = new ArrayList<>();
       for(String result : data.keySet()) {
           System.out.println(result);
           countries.add(result);
           JSONObject currency = data.getJSONObject(result);
           Response response = objectMapper.readValue(currency.toString(), new TypeReference<Response>(){});
           responses.add(response);
       }

       APIData apiData = new APIData();
       apiData.setCountries(countries);
       apiData.setResponses(responses);
       return apiData;
   }

   public String convert(Request request, APIData apiData) {
      try {
          List<Response> responses = apiData.getResponses();
          double toValue = 0;
          double fromValue = 0;
          String to = "";
          String from = "";
          for (Response response : responses) {
              if (response.getCode().equals(request.getTo())) {
                  toValue = Double.parseDouble(response.getValue());
                  to = response.getCode();
                  System.out.println("To: " + response.getValue());
              }
              if (response.getCode().equals(request.getFrom())) {
                  fromValue = Double.parseDouble(response.getValue());
                  from = response.getCode();
                  System.out.println("From: " + response.getValue());
              }
          }

          double amount = request.getAmount();
          double newToValue = Math.floor((amount * toValue));
          double newFromValue = Math.floor((amount * fromValue));

          String fromResult = ""+fromValue;
          String toResult = ((newToValue/newFromValue) * amount)+" "+to;

          System.out.println("amount: "+request.getAmount());

          return fromResult + " is equivalent to " + toResult;
      }
      catch(Exception ex) {
          logger.error(ex.getMessage());
          return ex.getMessage();
      }
   }

}
