package vn.guno.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class CommonUtils {

    public synchronized static String parseDate(Date inputDate) {
        if (inputDate == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(inputDate.toInstant());
    }

    public static String getHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }


    /**
     * generate random number between min and max
     *
     * @param min min value
     * @param max max value
     * @return random number between min and max
     */
    public static int generateRandomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static long ipv4ToLong(String ipV4) throws Exception {
        long ip = 0;
        if (ipV4 == null || ipV4.isEmpty()) throw new Exception("Invalid ip " + ipV4);
        String[] octets = ipV4.split(Pattern.quote("."));
        if (octets.length != 4) throw new Exception("Invalid ip " + ipV4);
        for (int i = 3; i >= 0; i--) {
            long octet = Long.parseLong(octets[3 - i]);
            if (octet > 255 || octet < 0) throw new Exception("Invalid ip " + ipV4);
            ip |= octet << (i * 8);
        }

        return ip;
    }

    public static String longToHex(long input) {
        String hexString = Long.toHexString(input);

        StringBuilder sb = new StringBuilder();

        for (int i = hexString.length(); i < 16; i++) {
            sb.append("0");
        }

        sb.append(hexString);

        return sb.toString();
    }

    public static String intToHex(int input) {
        String hexString = Integer.toHexString(input);

        StringBuilder sb = new StringBuilder();

        for (int i = hexString.length(); i < 8; i++) {
            sb.append("0");
        }

        sb.append(hexString);

        return sb.toString();
    }

    public static String md5(String input) throws Exception {
        if (input == null) throw new NullPointerException();
        String result;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        byte[] digest = md5.digest(inputBytes);
        StringBuilder sb = new StringBuilder();
        for (byte d : digest) {
            sb.append(Integer.toHexString((d & 0xFF) | 0x100).substring(1, 3));
        }
        result = sb.toString();

        return result;
    }

    public static long crc32(String input) {
        // get bytes from string
        byte bytes[] = input.getBytes(StandardCharsets.UTF_8);

        Checksum checksum = new CRC32();

        // update the current checksum with the specified array of bytes
        checksum.update(bytes, 0, bytes.length);

        // get the current checksum value
        return checksum.getValue();
    }

    public static boolean isASCII(String source) {
        String pattern = "^\\p{ASCII}*$";
        return source.matches(pattern);
    }

    public static Timestamp parseTime(long timeInMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);
        String t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(c.getTime());
        return Timestamp.valueOf(t);
    }

    public static boolean validateLatinChars(String input) {
        String pattern = "^[a-zA-Z0-9 _-]{3,50}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        return m.find();
    }

    public static boolean validateRoleName(String input) {
        String pattern = "^[a-zA-Z0-9 ]{2,50}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        return m.find();
    }

    public static boolean validateCharracter(String input, String regex) {
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(input);
        return m.find();
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }


    public synchronized static String getActiveProfile() {
        String profileName = System.getProperty("spring.profiles.active", "local");
        if (profileName == null || profileName.isEmpty()) {
            profileName = "local"; // default env
        }
        return profileName;
    }

    private static String normalize(String input) {
        return input == null ? null : Normalizer.normalize(input, Normalizer.Form.NFKD);
    }

    /**
     * remove Accents from non-latin string <br>
     * A Special thanks to <a href="https://www.baeldung.com/java-remove-accents-from-text"> THIS LINK</a>
     *
     * @param input input, eg: Tiệm trà Bánh
     * @return normalized str, eg: Tiem tra Banh
     */
    public static String removeAccents(String input) {
        return normalize(input).replaceAll("\\p{M}", "");
    }

}
