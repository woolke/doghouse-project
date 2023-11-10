package pl.wolke.doghouse.utils;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class StaticFunc {


    //DATE
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Calendar removeTimeCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Date removeTime(Date date) {
        return removeTimeCalendar(date).getTime();
    }

    public static boolean equalsWithoutTime(Date date1, Date date2) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return Objects.equals(dateFormat.format(date1), dateFormat.format(date2));
    }

    public static Date notNullDate(Date date) {
        if (date == null) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date addMonth(Date date, Integer monthToAdd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthToAdd);
        return cal.getTime();
    }

    public static Date addDays(Date date, Integer daysToAdd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return cal.getTime();
    }

    public static Date parseDateFromString(String stringDate) {
        try {
            if (stringDate.length() == 19 && !stringDate.contains("-")) {
                return getDateFromStringDateTimeExcel(stringDate);
            } else if (stringDate.length() == 10) {
                return getDateFromStringRRRR_MM_DD(stringDate);
            } else {
                return getDateTimeFromStringRRRR_MM_DD_T_HH_MM(stringDate);
            }
        } catch (Exception e) {
            return getDateFromStringRRRR_MM_DD("1900-01-01");
        }
    }

    public static Date parseDateFromString(String stringDate, SimpleDateFormat sdf) {
        try {
            return sdf.parse(stringDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDateOrNullFromString(String stringDate) {
        try {
            Date date = parseDateFromString(stringDate);
            if (date.equals(getDateFromStringRRRR_MM_DD("1900-01-01"))) {
                return null;
            }
            return date;
        } catch (Exception e) {
            return null;
        }

    }

    private static Date getDateTimeFromStringRRRR_MM_DD_T_HH_MM_SS(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        if (!(stringDate == null)) {
            try {
                parse = simpleDateFormat.parse(stringDate);
            } catch (ParseException e) {
                return null;
            }
        }
        return parse;
    }

    private static Date getDateFromStringDateTimeExcel(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        if (!(stringDate == null)) {
            try {
                parse = simpleDateFormat.parse(stringDate);
            } catch (ParseException e) {
                return null;
            }
        }
        return parse;
    }


    private static Date getDateTimeFromStringRRRR_MM_DD_T_HH_MM(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            System.out.println("Nie udało sie parsować daty:" + stringDate);
        }
        return parse;
    }

    private static String randomLetter() {
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        return String.valueOf(alphabet.charAt(rnd.nextInt(alphabet.length())));
    }

    public static String replacePolishChar(String string) {
        string = string.replaceAll("ą", "a");
        string = string.replaceAll("ę", "e");
        string = string.replaceAll("ś", "s");
        string = string.replaceAll("ć", "c");
        string = string.replaceAll("ż", "z");
        string = string.replaceAll("ż", "z");
        string = string.replaceAll("ó", "o");
        string = string.replaceAll("ł", "l");
        string = string.replaceAll("ń", "n");
        return string;
    }

    public static String getRRRR_MM_DD_String(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (Exception e){
            return "";
        }
    }

    public static String getRRRR_MM_DD_String(Date date, String resultIfError) {
        try {
            return getRRRR_MM_DD_String(date);
        } catch (Exception e) {
            return resultIfError;
        }
    }

    public static String getDDMMRRR_With_Coma_Char_String(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public static String getRRRRMMDD_Without_Any_Char_String(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static String getRRRR_MM_DD__HH_MM_String(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(date);
    }

    public static String getRRRR_MM_DD__HH_MM_Path(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH.mm").format(date);
    }

    static Date getDateFromStringDDdotMMdotYYYY(String stringDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date parse = null;
        if (!(stringDate == null)) {
            try {
                parse = simpleDateFormat.parse(stringDate);
            } catch (ParseException e) {
                return null;
            }
        }

        return parse;
    }

    static Date getDateFromStringRRRR_MM_DD(String stringDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simpleDateFormatComa = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat simpleDateFormatComa2 = new SimpleDateFormat("yyyy.MM.dd");
        Date parse = null;
        if (!(stringDate == null)) {
            try {
                if (stringDate.contains(".")) {
                    if(stringDate.charAt(2)=='.') {
                        parse = simpleDateFormatComa.parse(stringDate);
                    } else {
                        parse = simpleDateFormatComa2.parse(stringDate);
                    }
                } else {
                    if (stringDate.contains("-")) {
                        if (stringDate.charAt(2) == '-') {
                            parse = simpleDateFormat2.parse(stringDate);
                        } else {
                            parse = simpleDateFormat.parse(stringDate);
                        }
                    }
                }
            } catch (ParseException e) {
                return null;
            }
        }

        return parse;
    }


    public static boolean stringToBool(String s) {
        if (s == null) {
            return false;
        }

        s = s.toLowerCase();
        Set<String> trueSet = new HashSet<String>(Arrays.asList("on", "1", "true", "yes", "tak", "+", "prawda"));
        Set<String> falseSet = new HashSet<String>(Arrays.asList("off", "0", "false", "no", "nie", "-", "fałsz", "falsz",""));

        if (trueSet.contains(s))
            return true;
        if (falseSet.contains(s))
            return false;

        throw new IllegalArgumentException(s + " is not a boolean.");
    }

    public static boolean parameterInputToBool( Map<String, String[]> parameterMap, String key) {
        if(parameterMap.containsKey(key)){
            return stringToBool(parameterMap.get(key)[0]);
        } else {
            return false;
        }

    }

    public static String parameterInputString( Map<String, String[]> parameterMap, String key) {
        if(parameterMap.containsKey(key)){
            return parameterMap.get(key)[0];
        } else {
            return "";
        }

    }


    public static BigDecimal bigDecimalFromString(String string) {

        try {
            if (string.isEmpty()) {
                return BigDecimal.valueOf(0.0);
            } else {
                return BigDecimal.valueOf(Double.parseDouble(string.replace(',', '.')
                        .replace(" ", "")));
            }
        } catch (Exception e) {
            System.out.println("Nie udało się parsowac kwoty: " + string);
            return BigDecimal.valueOf(0.0);
        }
    }

    public static int intFromStringID(String string) {
        boolean warunek = (string.isEmpty() || string == null);
        if (string.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(string);
        }
    }

    public static String zipCodeFromXXXXXtoXX_XXX(String zipCode) {
        return zipCode.substring(0, 2) + "-" + zipCode.substring(2, 5);
    }


    //addToModel
    public static void addDates(Model model) {
        Map<Integer, String> dates = new HashMap<>();
        Date today = new Date();
        dates.put(0, new SimpleDateFormat("yyyy-MM-dd").format(today));
        dates.put(1, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 1)));
        dates.put(2, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 2)));
        dates.put(3, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 3)));
        dates.put(5, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 5)));
        dates.put(7, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 7)));
        dates.put(10, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 10)));
        dates.put(14, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 14)));
        dates.put(30, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 30)));
        dates.put(60, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 60)));
        dates.put(90, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 90)));
        dates.put(365, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, 365)));
        dates.put(-1, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -1)));
        dates.put(-2, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -2)));
        dates.put(-3, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -3)));
        dates.put(-5, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -5)));
        dates.put(-7, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -7)));
        dates.put(-10, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -10)));
        dates.put(-14, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -14)));
        dates.put(-30, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -30)));
        dates.put(-60, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -60)));
        dates.put(-90, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -90)));
        dates.put(-365, new SimpleDateFormat("yyyy-MM-dd").format(addDays(today, -365)));
        model.addAttribute("dates", dates);

    }




    public static void getMacAdress() throws SocketException {
        Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
        NetworkInterface inter;
        while (networks.hasMoreElements()) {
            inter = networks.nextElement();
            byte[] mac = inter.getHardwareAddress();
            if (mac != null) {
                for (int i = 0; i < mac.length; i++) {
                    System.out.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
                }
                System.out.println("");
            }
        }
    }

    public static String removeIllegalPathCharacter(String path) {
        path = path.replaceAll("'", "_");
        path = path.replaceAll("`", "_");
        path = path.replaceAll(" ", "_");
        path = path.replaceAll(":", ",");
        path = path.replaceAll("\r", "");
        path = path.replaceAll("\n", "");
        path = path.replaceAll("/", ";");
        path = path.replaceAll("\\[", "_");
        path = path.replaceAll("]", "_");
        path = path.replaceAll("&", "_");
        path = path.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");


        return path;
    }

    public static String replaceUnicodeFromUtf(String path) {
        path = path.replaceAll("%C4%84", "Ą");
        path = path.replaceAll("%C4%86", "Ć");
        path = path.replaceAll("%C4%98", "Ę");
        path = path.replaceAll("%C5%81", "Ł");
        path = path.replaceAll("%C5%83", "Ń");
        path = path.replaceAll("%C3%93", "Ó");
        path = path.replaceAll("%C5%9A", "Ś");
        path = path.replaceAll("%C5%B9", "Ź");
        path = path.replaceAll("%C5%BB", "Ż");
        path = path.replaceAll("%C4%85", "ą");
        path = path.replaceAll("%C4%87", "ć");
        path = path.replaceAll("%C4%99", "ę");
        path = path.replaceAll("%C5%82", "ł");
        path = path.replaceAll("%C5%84", "ń");
        path = path.replaceAll("%C3%B3", "ó");
        path = path.replaceAll("%C5%9B", "ś");
        path = path.replaceAll("%C5%BA", "ź");
        path = path.replaceAll("%C5%BC", "ż");
        return path;
    }





    public static Boolean sameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public static Boolean sameYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public static int hoursDifference(Date date1, Date date2) {
        return (int) (date1.getTime() - date2.getTime()) / (1000 * 60 * 60);
    }

    public static String slownie(BigDecimal value) {
        String[] jedności = {"", "jeden ", "dwa ", "trzy ", "cztery ",
                "pięć ", "sześć ", "siedem ", "osiem ", "dziewięć ",};

        String[] nastki = {"", "jedenaście ", "dwanaście ", "trzynaście ",
                "czternaście ", "piętnaście ", "szesnaście ", "siedemnaście ",
                "osiemnaście ", "dziewiętnaście ",};

        String[] dziesiątki = {"", "dziesięć ", "dwadzieścia ",
                "trzydzieści ", "czterdzieści ", "pięćdziesiąt ",
                "sześćdziesiąt ", "siedemdziesiąt ", "osiemdziesiąt ",
                "dziewięćdziesiąt ",};

        String[] setki = {"", "sto ", "dwieście ", "trzysta ", "czterysta ",
                "pięćset ", "sześćset ", "siedemset ", "osiemset ",
                "dziewięćset ",};

        String[][] grupy = {{"", "", ""},
                {"tysiąc ", "tysiące ", "tysięcy "},
                {"milion ", "miliony ", "milionów "},
                {"miliard ", "miliardy ", "miliardów "},
                {"bilion ", "biliony ", "bilionów "},
                {"biliard ", "biliardy ", "biliardów "},
                {"trylion ", "tryliony ", "trylionów "},};

        long j = 0, n = 0, d = 0, s = 0, g = 0, k = 0;
        String słownie = "";
        String znak = "";
        Integer liczba = value.intValue();
        String groszy = String.valueOf(value.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).intValue());

        if (liczba < 0) {
            znak = "minus ";
            liczba = -liczba;
        }
        if (liczba == 0) {
            znak = "zero";
        }

        while (liczba != 0) {
            s = liczba % 1000 / 100;
            d = liczba % 100 / 10;
            j = liczba % 10;

            if (d == 1 & j > 0) {
                n = j;
                d = 0;
                j = 0;
            } else {
                n = 0;
            }
            if (j == 1 & s + d + n == 0) {
                k = 0;

                if (s + d == 0 && g > 0) {
                    j = 0;
                    słownie = grupy[(int) g][(int) k] + słownie;
                }
            } else if (j == 2) {
                k = 1;
            } else if (j == 3) {
                k = 1;
            } else if (j == 4) {
                k = 1;
            } else {
                k = 2;
            }
            if (s + d + n + j > 0) {
                słownie = setki[(int) s] + dziesiątki[(int) d] + nastki[(int) n]
                        + jedności[(int) j] + grupy[(int) g][(int) k] + słownie;
            }

            liczba = liczba / 1000;
            g = g + 1;
        }
        słownie = znak + słownie;
        return słownie + "PLN " + groszy + "/100";
    }


    public static List<String> splitComaStringToStringArray(String string) {
        String[] split = string.split(",");
        return Arrays.asList(split);
    }

    public static List<String> splitStringByCustomChar(String string, String myChar) {
        String[] split = string.split(myChar);
        return Arrays.asList(split);
    }

    public static List<String> splitStringByEnter(String string) {
        String[] split = string.split("\\r\\n");
        return Arrays.asList(split);
    }

    //Search From Pdf
    public static String getPostIdFromPdfRaport(ArrayList<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("Numer nadania") && !lines.get(i).contains("Cena")) {
                return lines.get(i + 1);
            }
        }
        return null;
    }

    public static Integer daysBetween(Date date1, Date date2, boolean abs) {
        try {
            long diff = date2.getTime() - date1.getTime();
            if (Objects.equals(abs, true)) {
                return Math.abs(Math.toIntExact(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
            } else {
                return Math.toIntExact(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static Integer secondBetween(Date date1, Date date2, boolean abs) {
        try {
            long diff = date2.getTime() - date1.getTime();
            if (Objects.equals(abs, true)) {
                return Math.abs(Math.toIntExact(TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS)));
            } else {
                return Math.toIntExact(TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS));
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static Integer integerOnEndOfString(String s) {
        try {
            int i = s.length();
            while (i > 0 && Character.isDigit(s.charAt(i - 1))) {
                i--;
            }
            return Integer.valueOf(s.substring(i));
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> parsePayloadToMap(List<Map<String, Object>> payload){
        Map<String, Object> map  = new HashMap<>();
        if (payload ==  null)
            return map;
        for (Map<String, Object> stringObjectMap : payload) {
            map.put(stringObjectMap.get("name").toString(), stringObjectMap.get("value"));
        }
        return map;
    }

    public static List<Map<String, Object>> parsePayloadToMapList(List<Map<String, Object>> payload){
        List<Map<String, Object>> list  = new ArrayList<>();
        if (payload ==  null)
            return list;
        for (Map<String, Object> stringObjectMap : payload) {
            Map<String, Object> map = new HashMap<>();
            map.put(stringObjectMap.get("name").toString(), stringObjectMap.get("value"));
        }
        return list;
    }

    public static Date combineTwoDateWithConsoleInfo(Date date1, Date date2, String infoToConsole) {
        Date emptyDate = getDateFromStringRRRR_MM_DD("1900-01-01");
        Date returnDate = null;

        if (Objects.equals(date1, date2)) {
            return date1;
        }

        if (!Objects.equals(emptyDate, date1) && Objects.nonNull(date1)) {
            returnDate = date1;
        }
        if (!Objects.equals(emptyDate, date2) && Objects.nonNull(date2)) {
            if (returnDate == null) {
                returnDate = date2;
            } else {
                System.out.println(infoToConsole + " (Zapisano:" + getRRRR_MM_DD__HH_MM_String(date1)
                        + ", Odrzucono:" + getRRRR_MM_DD__HH_MM_String(date2));
            }
        }
        return returnDate;
    }

    public static Object combineTwoObjectsWithConsoleInfo(Object object1, Object object2, String infoToConsole) {
        Object returnObject = null;

        if (Objects.equals(object1, object2)) {
            return object1;
        }

        if (Objects.nonNull(object1)) {
            returnObject = object1;
        }
        if (Objects.nonNull(object2)) {
            if (returnObject == null) {
                returnObject = object2;
            } else {
                System.out.println(infoToConsole + " (Zapisano:" + object1.toString()
                        + ", Odrzucono:" + object2.toString());
            }
        }
        return returnObject;
    }

    public static class MyListHashMap<K,V>  {

        HashMap<K, List<V>> map = new HashMap<>();

        public MyListHashMap() {
            this.map = map;
        }


        public Set<Map.Entry<K, List<V>>> entrySet() {
            return map.entrySet();
        }

        public void put (K key,V value) {
            if (map.containsKey(key)){
                List<V> valueList = map.get(key);
                valueList.add(value);
            } else {
                List<V> valueList = new ArrayList<>();
                valueList.add(value);
                map.put(key, valueList);
            }
        }

        public List<V> get (K key) {
            if (map.containsKey(key)) {
                List<V> valueList = map.get(key);
                if (valueList.size() > 0)
                    return valueList;
            }
            return null;
        }

        public boolean containsKey(K key) {
            return map.containsKey(key);
        }

        public Set<K> keySet(){
            return map.keySet();
        }

        public void clear() {
            map.clear();
        }




    }





}

