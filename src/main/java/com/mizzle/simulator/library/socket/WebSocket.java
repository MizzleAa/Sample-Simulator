package com.mizzle.simulator.library.socket;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mizzle.simulator.payload.request.ReceiveSocketPayload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@PropertySource(value = { "properties/fileio.properties" })
public class WebSocket {

    @Value("${file-io.file-path}")
    private String filePath;

    private final Environment environment;

    public int send(String sendUrl, String method, String data) {
        int responseCode = -1;
        /**
        curl -X 'POST' \
        'http://localhost/api/websocket/receive' \
        -H 'accept: application/json' \
        -H 'Content-Type: application/json' \
         */
        try {
            URL url = new URL(sendUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setDoOutput(true);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            
            outputStream.write(data.getBytes("UTF-8"));
            
            outputStream.flush();
            outputStream.close();
            
            responseCode = connection.getResponseCode();
            connection.disconnect();

        } catch (Exception e) {
            //TODO: handle exception
        }

        return responseCode;
    }

    public String makeJson(String path, String name, Object raw) {
        try{
            ReceiveSocketPayload.Image image = new ReceiveSocketPayload.Image();

            image.setPath(path);
            image.setName(name);
    
            ReceiveSocketPayload.Information information = new ReceiveSocketPayload.Information();
    
            information.setKey(raw);
    
            ReceiveSocketPayload receiveSocketPayload = ReceiveSocketPayload.builder().image(image).information(information).build();
    
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(receiveSocketPayload);
            
            return json;
        }catch(Exception e){

        }
        return null;
    }

    public String urlPath(long id){
        InetAddress local;
        try {
            local = InetAddress.getLocalHost();
            String port = environment.getProperty("local.server.port");
            String url = String.format("http://%s:%s/%s/%d", local.getHostAddress(), port, filePath, id);
            return url;
        } catch (UnknownHostException e) {

        }
        return "";

    }
}
