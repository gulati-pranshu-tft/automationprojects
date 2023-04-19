package com.cbla.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.SimpleDateFormat;
import java.util.*;

public class TestDataGenerator {

    public static final String DATE_WITH_SLASHES = "MM/dd/yyyy";
    public static final String ABBREV_DATE = "MMM dd, yyyy";
    public static final String ABBREV_DATE2 = "MMM d, yyyy";
    //Registration Answer options
    private static final String[] STREET_NAMES = {"Test Street", "Test Avenue", "Test Road"};
    private static final String[] VALID_ZIP_CODES = {"12345", "10001", "02451", "02111", "02222", "55555"};
    private static final String[] CITY_NAMES = {"New York", "Waltham", "Test City", "Test Town"};

    public static String generateEmail(String base) {
        long l = System.currentTimeMillis();
        String email = base + l + "@test.com";
        System.out.println("Generated email is: " + email);
        return email;
    }

    public static String generateEmail(String base, String domain) {
        long l = System.currentTimeMillis();
        String email = base + l + "@" + domain;
        System.out.println("Generated email is: " + email);
        return email;
    }

    public static String generatePhoneNumber() {
        int number = randomInt(0, 10000);
        String phone;
        if (number < 1000) {
            if (number < 100) {
                if (number < 10) {
                    phone = "000" + number;
                } else {
                    phone = "00" + number;
                }
            } else {
                phone = "0" + number;
            }
        } else {
            phone = Integer.toString(number);
        }
        return "(555) 555-" + phone;
    }

    public static String generateCompanyName(String base) {
        long l = System.currentTimeMillis();
        String company = base + l;
        System.out.println("Generated company name is is: " + company);
        return company;
    }

    public static String getCurrentTimeMillis() {
        long l = System.currentTimeMillis();
        String time = String.valueOf(l);
        return time;
    }

    //generates a random number n -> min <= n < max
    //please do not edit - used in many tests
    public static int randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }

    public static boolean randomBool() {
        Random rand = new Random();
        return rand.nextBoolean();
    }

    public static String randomZip() {
        String zip = VALID_ZIP_CODES[randomInt(0, VALID_ZIP_CODES.length)];
        return zip;
    }

    public static String randomCity() {
        String city = CITY_NAMES[randomInt(0, CITY_NAMES.length)];
        return city;
    }

    public static String randomStreetAddress() {
        String number = Integer.toString(randomInt(0, 10000));
        String streetName = STREET_NAMES[randomInt(0, STREET_NAMES.length)];
        return number + " " + streetName;
    }

    public static String selectRandomDropdownOption(WebElement dropdown, int startIndex) {
        Select select = new Select(dropdown);
        int index = randomInt(startIndex, select.getOptions().size());
        select.selectByIndex(index);
        return select.getOptions().get(index).getText();

    }

    public static Date getTodaysDate() {
        Date date = new Date();
        return date;
    }

    public static String formatDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formattedDate = sdf.format(date);
        return formattedDate;

    }

    public static String formatTodaysDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static ArrayList<String> generateStringList(ArrayList<String> list, int size) {
        int oldListSize = list.size();
        int newListSize = size;

        ArrayList<String> newList = new ArrayList<String>();

        Set<Integer> tempList = new HashSet<Integer>();
        while (tempList.size() < newListSize) {
            tempList.add(randomInt(0, oldListSize));
        }
        Iterator<Integer> iter = tempList.iterator();
        int i = 0;
        while (i < newListSize && iter.hasNext()) {
            newList.add(list.get(iter.next()));
            //System.out.println(i+": "+listOfCategories[i].getText());
            i++;
        }
        return newList;
    }

    public static String generateStringFromArrayList(ArrayList<String> list) {
        int size = list.size();
        String result = list.get(0);
        for (int i = 1; i < size; i++) {
            result = result + ", " + list.get(i);
        }
        return result;
    }

    public static WebElement[] generateList(List<WebElement> list, int numberOfElements) {
        int listSize = list.size();
        WebElement[] listOfCategories = new WebElement[numberOfElements];

        Set<Integer> categoryIds = new HashSet<Integer>();
        while (categoryIds.size() < numberOfElements) {
            categoryIds.add(randomInt(0, listSize));
            //System.out.println("List size: "+categoryIds.size());
        }
        Iterator<Integer> iter = categoryIds.iterator();
        int i = 0;
        while (i < numberOfElements && iter.hasNext()) {
            listOfCategories[i] = list.get(iter.next());
            //System.out.println(i+": "+listOfCategories[i].getText());
            i++;
        }
        return listOfCategories;
    }
}
