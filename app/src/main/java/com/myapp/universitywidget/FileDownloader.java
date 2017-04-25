package com.myapp.universitywidget;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class FileDownloader {

    public String downloadFile() {
        try {
            URL url = new URL("http://cist.nure.ua/ias/app/tt/P_API_EVENTS_GROUP_JSON?p_id_group=5259300");
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            return readInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readInputStream(InputStream inputStream) {
        try (Scanner s = new Scanner(inputStream, "cp1251").useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        }
    }
}
