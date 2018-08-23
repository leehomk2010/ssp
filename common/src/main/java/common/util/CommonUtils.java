package common.util;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/14.
 */
public class CommonUtils {

    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String newDate = format.format(date);
        return newDate;
    }

    public static Long getLongDate(String date){
        Long dateTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = (Date)sdf.parse(date);
            dateTime = date1.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime ;
    }

    public static String getStrDate(Long date){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(date * 1000);
        String str = sdf.format(date1);
        return str;
    }

    public static int getFirstResult(int page){
        int firstResult = 0;
        if(page > 0){
            firstResult = (page - 1) * 10;
        }
        return firstResult;
    }

    public static String getResultStr(String str){
        if(StringUtils.isNotEmpty(str)){
            if(str.length() > 3){
                StringBuffer sb = new StringBuffer(str);
                for(int i = str.length()-3; i > 0; i=i-3){
                    sb.insert(i, ","); //sb.insert (1, "**");
                }
                return sb.toString();
            }else{
                return str;
            }
        }
        return null;
    }

    public static String cleanHtml(String html){
        if(StringUtils.isNotEmpty(html)){
            return Jsoup.clean(html, Whitelist.none());
        }
        return "";
    }

    public static String cleanHtml2(String html){
        if(StringUtils.isNotEmpty(html)){
            Whitelist whitelist = new Whitelist();
            whitelist.addTags("em");
            return Jsoup.clean(html, whitelist);
        }
        return "";
    }

    public static String filterEmpty(Object obj){
        if(obj != null){
            String str = obj.toString();
            str = str.replaceAll(" ", "");
            str = str.replaceAll("　", "");
            str = str.replaceAll("&nbsp;", "");
            return str;
        }else{
            return "";
        }
    }

    public static String escapeQueryChars(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'
                    || Character.isWhitespace(c)) {
            }else {

                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String zyQueryChars(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'
                    || Character.isWhitespace(c)) {
                sb.append("\\");
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String getStrDate(Integer min){
        return getDate(min).getTime()/1000+"";
    }

    public static Date getDate(Integer min){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -min);
        Date date = c.getTime();
        return date;
    }

    public static String format(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String newDate = format.format(date);
        return newDate;
    }

    public static String format(Long value,String pattern){
        if (value != null){
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(Long.parseLong(value.toString())*1000);
            SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
            return dateformat.format(c.getTime());
        }
        return "";
    }
}
