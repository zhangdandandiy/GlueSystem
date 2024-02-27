package com.system.glue.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 胶水判定相关的一些工具类
 *
 * @author Dandan
 * @date 2023/11/16 8:53
 **/
public class GlueUtils {

    /**
     * 判断胶水是否到期 true 到期
     *
     * @param EncodingFormat
     * @param glueBarCode
     * @return
     */
    public static boolean IsGlueExpire(String EncodingFormat, String glueBarCode) {
        if (EncodingFormat.contains("ICT")) {
            return IsGlueExpireByICT(glueBarCode);
        } else {
            return IsGlueExpireByHenkel(glueBarCode);
        }
    }

    /**
     * 计算胶水的剩余保质期
     *
     * @param EncodingFormat
     * @param glueBarCode
     * @return
     */
    public static Long getGlueLife(String EncodingFormat, String glueBarCode) {
        if (EncodingFormat.contains("ICT")) {
            return getGlueLifeByICT(glueBarCode);
        } else {
            return getGlueLifeByHenkel(glueBarCode);
        }
    }

    /**
     * 通过胶水的ICT编码判断胶水是否到期 true 到期
     *
     * @param glueBarCode
     * @return
     */
    public static boolean IsGlueExpireByICT(String glueBarCode) {
        List<String> List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
        // 第二位是生产日期
        Date ProductionDate = GlueProductionDateByICT(List.get(2));
        // 第四位是保质期（单位为M）
        String monthString = String.valueOf(List.get(4).charAt(0));
        Long ShelfLife = Long.valueOf(monthString);
        // 到期时间
        Date Expiration = PlusDateMonth(ProductionDate, ShelfLife);
        if (Expiration.compareTo(new Date()) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过胶水的ICT编码计算胶水的剩余寿命
     *
     * @param glueBarCode
     * @return
     */
    public static Long getGlueLifeByICT(String glueBarCode) {
        List<String> List = Arrays.asList(StringUtils.split(glueBarCode, "#"));
        // 第二位是生产日期
        Date ProductionDate = GlueProductionDateByICT(List.get(2));
        // 第四位是保质期（单位为M）
        String monthString = String.valueOf(List.get(4).charAt(0));
        long ShelfLife = Long.parseLong(monthString);
        // 自生产到现在已经过了多久
        long alreadyTime = DateDifferentMinute(ProductionDate, new Date());
        // 剩余寿命
        return ShelfLife * 30 * 24 * 60 - alreadyTime;
    }

    /**
     * 通过胶水的Henkel编码判断胶水是否到期 true 到期
     *
     * @param glueBarCode
     * @return
     */
    public static boolean IsGlueExpireByHenkel(String glueBarCode) {
        List<String> List = Arrays.asList(StringUtils.split(glueBarCode, "-"));
        // 第四位是到期日期
        Date Expiration = GlueProductionDateByHenkel(List.get(4));
        if (Expiration.compareTo(new Date()) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过胶水的Henkel编码计算胶水的剩余寿命
     *
     * @param glueBarCode
     * @return
     */
    public static Long getGlueLifeByHenkel(String glueBarCode) {
        List<String> List = Arrays.asList(StringUtils.split(glueBarCode, "-"));
        // 第三位是生产日期
        Date ProductionDate = GlueProductionDateByHenkel(List.get(3));
        // 第四位是到期日期
        Date Expiration = GlueProductionDateByHenkel(List.get(4));
        // 保质期
        long ShelfLife = DateDifferenceMonth(ProductionDate, Expiration);
        // 自生产到现在已经过了多久
        long alreadyTime = DateDifferentMinute(ProductionDate, new Date());
        // 剩余寿命
        return ShelfLife * 30 * 24 * 60 - alreadyTime;
    }

    /**
     * 给一个Date类型的时间加几个月
     *
     * @param NowDate
     * @param Month
     * @return
     */
    public static Date PlusDateMonth(Date NowDate, Long Month) {
        Instant instant = NowDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        LocalDate newDate = localDate.plusMonths(Month);

        LocalDateTime newDateTime = newDate.atStartOfDay();
        ZonedDateTime zonedDateTime = newDateTime.atZone(zoneId);
        Instant newInstant = zonedDateTime.toInstant();
        Date newDateObj = Date.from(newInstant);

        return newDateObj;
    }

    /**
     * 通过胶水·ICT编码的生产编号获取胶水的生产日期(Date)
     *
     * @param productionCode
     * @return
     */
    public static Date GlueProductionDateByHenkel(String productionCode) {
        String Year = productionCode.substring(0, 4);
        String Month = productionCode.substring(4, 6);
        String Day = productionCode.substring(6);
        String ProductionDateString = String.join("-", Year, Month, Day) + " 00:00:00";
        return ConvertDateFormat(ProductionDateString);
    }

    /**
     * 通过胶水·ICT编码的生产编号获取胶水的生产日期(Date)
     *
     * @param productionCode
     * @return
     */
    public static Date GlueProductionDateByICT(String productionCode) {
        String Year = "";
        String Month = "";
        String Day = "";
        // 第一位一定表示年份
        Year = "202" + productionCode.charAt(0);
        if (productionCode.length() == 4) {
            // 长度为4，第二位表示月份
            Month = "0" + productionCode.charAt(1);
            Day = productionCode.substring(2);
        } else {
            Month = productionCode.substring(1, 3);
            Day = productionCode.substring(3);
        }
        String ProductionDateString = String.join("-", Year, Month, Day) + " 00:00:00";
        return ConvertDateFormat(ProductionDateString);
    }

    /**
     * 将字符串类型的时间更改为Date类型
     *
     * @param DateString
     * @return
     */
    public static Date ConvertDateFormat(String DateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date ProductionDate = new Date();
        try {
            ProductionDate = dateFormat.parse(DateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ProductionDate;
    }

    /**
     * 将Date类型的时间更改为字符串类型
     * @param date
     * @return
     */
    public static String ConvertStringFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 获取两个日期的月份差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int DateDifferenceMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        int monthDiff = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

        int months = yearDiff * 12 + monthDiff;
        return months;
    }

    /**
     * 获取两个日期的分钟差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long DateDifferentMinute(Date date1, Date date2) {
        long timestamp1 = date1.getTime();
        long timestamp2 = date2.getTime();

        long diffMinutes = (timestamp2 - timestamp1) / (60 * 1000);
        // 将负差值转换为零
        diffMinutes = Math.max(diffMinutes, 0);
        return diffMinutes;
    }

}
