package com.cbla.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class DataUtil {

    public static String resourcesPath = "src/main/resources/";

    //TestNG Parameterization
    public static Object[][] getData(String sheetName, ResourceExcelReader excel) {
        // return test data;
        // read test data from xls
        int rows = excel.getRowCount(sheetName) - 1;
        if (rows <= 0) {
            Object[][] testData = new Object[1][0];
            return testData;
        }
        rows = excel.getRowCount(sheetName);  // 3
        int cols = excel.getColumnCount(sheetName);
        Object data[][] = new Object[rows - 1][cols];

        for (int rowNum = 2; rowNum <= rows; rowNum++) {

            for (int colNum = 0; colNum < cols; colNum++) {
                data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
            }
        }
        return data;
    }

    public static String getDataFromScriptResourcesConfigPropFile(String property) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "script-resources" + File.separator + "config.properties");
        prop.load(fis);
        return prop.getProperty(property);
    }

    public static String getTestDataFromPropFile(String fileName, String property) throws IOException {
        Properties prop = new Properties();
        if (!fileName.equalsIgnoreCase("testdata")) {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" +
                    File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "cbla" +
                    File.separator + "config" + File.separator + "properties" + File.separator + fileName.toLowerCase() + ".properties");
            prop.load(new InputStreamReader(fis, Charset.forName("UTF-8")));
            return String.valueOf(prop.getProperty(property));
        } else {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "script-resources" + File.separator + "testdata.properties");
            prop.load(fis);
            return String.valueOf(prop.getProperty(property));
        }
    }

    public static String getCredentialsFromPropFile(String fileName, String property) throws IOException {
        Properties prop = new Properties();
        if (!fileName.equalsIgnoreCase("testdata")) {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +
                    File.separator + fileName.toLowerCase() + ".properties");
            prop.load(new InputStreamReader(fis, Charset.forName("UTF-8")));
            return String.valueOf(prop.getProperty(property));
        } else {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "script-resources" + File.separator + "testdata.properties");
            prop.load(fis);
            return String.valueOf(prop.getProperty(property));
        }
    }

    public static String fetchExperimentalIdFromLocalStorage(Map applicationTabData) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj1 = (JSONObject) parser.parse((String) applicationTabData.get("bbn-go-experiment-data"));
        JSONObject obj2 = (JSONObject) obj1.get("optimizeData");
        return String.valueOf(obj2.get("experimentId"));
    }

    //For 'ring' api request requestName parameter will be as 'ringctr'
    public static String fetchRequestFromNetworkTabData(ArrayList networkTabData, String requestName) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObj;
        String jsonString;
        for (int i = 0; i < networkTabData.size(); i++) {
            jsonString = JSONObject.toJSONString((Map) networkTabData.get(i));
            jsonObj = (JSONObject) parser.parse(jsonString);
            if (jsonObj.get("name").toString().contains(requestName)) {
                return String.valueOf(jsonObj.get("name"));
            }
        }
        return null;
    }

    public static String fetchAttributeFromRequest(ArrayList networkTabData, String requestName, String attribute) throws ParseException {
        String requestURL = DataUtil.fetchRequestFromNetworkTabData(networkTabData, requestName);
        if (attribute.equals("request")) {
            return requestURL.split("\\?")[0];
        } else {
            String[] queryParamArray = requestURL.split("\\?")[1].split("&");
            for (String queryParam : queryParamArray) {
                if (queryParam.contains(attribute)) {
                    return queryParam.split("=")[1];
                }
            }
        }
        return null;
    }

    public static String fetchAttributeFromPerformanceLogs(LogEntries logEntries, String apiName, String attribute) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject logObject;
        JSONObject messageObject;
        JSONObject paramJsonObject;
        JSONObject requestJsonObject;
        JSONObject postDataJsonObject;
        for (LogEntry log : logEntries) {
            logObject = (JSONObject) parser.parse(log.getMessage());
            messageObject = (JSONObject) logObject.get("message");
            if (messageObject.get("method").equals("Network.requestWillBeSent")) {
                paramJsonObject = (JSONObject) messageObject.get("params");
                requestJsonObject = (JSONObject) paramJsonObject.get("request");
                if (String.valueOf(requestJsonObject.get("url")).contains(apiName)) {
                    if (attribute.equals("request")) {
                        return String.valueOf(requestJsonObject.get("url"));
                    } else if (String.valueOf(requestJsonObject.get("postData")).contains(attribute)) {
                        postDataJsonObject = (JSONObject) parser.parse(String.valueOf(requestJsonObject.get("postData")));
                        return String.valueOf(postDataJsonObject.get(attribute));
                    }
                }
            }
        }
        return null;
    }

    public static JSONObject getProviderUserInfo(String providerType, String providerFile, String name) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        JSONArray providerInfoJsonArray;
        JSONObject providerInfoJsonObject;
        JSONObject foundProviderInfoJsonObject;
        try {
            Object object = parser.parse(new FileReader(resourcesPath + providerFile));
            jsonObject = (JSONObject) object;
            providerInfoJsonArray = (JSONArray) jsonObject.get(providerType);
            for (int i = 0; i < providerInfoJsonArray.size(); i++) {
                providerInfoJsonObject = (JSONObject) providerInfoJsonArray.get(i);
                if (providerInfoJsonObject.get("name").equals(name)) {
                    foundProviderInfoJsonObject = providerInfoJsonObject;
                    return foundProviderInfoJsonObject;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String fetchRedirectionURLFromPerformanceLogs(LogEntries logEntries, String linkName) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject logObject;
        JSONObject messageObject;
        JSONObject paramJsonObject;
        for (LogEntry log : logEntries) {
            logObject = (JSONObject) parser.parse(log.getMessage());
            messageObject = (JSONObject) logObject.get("message");
            if (messageObject.get("method").equals("Network.requestWillBeSent")) {
                paramJsonObject = (JSONObject) messageObject.get("params");
                if (String.valueOf(paramJsonObject.get("documentURL")).contains(linkName)) {
                    return String.valueOf(paramJsonObject.get("documentURL"));
                }
            }
        }
        return null;
    }

    public static String fetchBBNRedirectionURL(LogEntries logEntries, String linkName) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject logObject;
        JSONObject messageObject;
        JSONObject paramJsonObject;
        for (LogEntry log : logEntries) {
            logObject = (JSONObject) parser.parse(log.getMessage());
            messageObject = (JSONObject) logObject.get("message");
            if (messageObject.get("method").equals("Page.windowOpen")) {
                paramJsonObject = (JSONObject) messageObject.get("params");
                if (String.valueOf(paramJsonObject.get("url")).contains(linkName)) {
                    return String.valueOf(paramJsonObject.get("url"));
                }
            }
        }
        return null;
    }

    public static ArrayList getNetworkTabDataWithGagoApi(WebDriver driver, String environment) {
        ArrayList networkTabData = null;
        boolean hasGagoApi = false;
        int attemptCount = 0;
        while (!hasGagoApi) {
            if (attemptCount == 40)
                break;
            networkTabData = WebDriverUtil.getNetworkTabData(driver);
            for (int i = 0; i < networkTabData.size() - 1; i++) {
                if (!networkTabData.get(i).toString().contains("gago")) {
                    networkTabData.remove(i);
                } else {
                    hasGagoApi = true;
                    break;
                }
            }
            attemptCount++;
        }
        return networkTabData;
    }

    public static String getDataFromConfigPropFile(String property) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "script-resources" + File.separator + "config.properties");
        prop.load(fis);
        return prop.getProperty(property);
    }

    public static String getMarkingKeysDataFromDocFile(String filename) {
        List<String> fileData = new ArrayList<>();
        File file = new File(filename);
        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                fileData.add(para.getText());
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileData.toString().replace("[", "").replace("]", "");
    }
}
