package com.bluteam.personal.shorturl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class URLShortener {
    private final ConcurrentMap<String, String> shortToLong = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, String> longToShort = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);
    private final String baseUrl;
    private final Pattern urlPattern;

    // Base62 characters برای encoding
    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public URLShortener(String baseUrl) {
        this.baseUrl = baseUrl;
        // الگوی validation برای URL
        this.urlPattern = Pattern.compile(
            "^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
        );
    }

    // تبدیل URL بلند به کوتاه
    public String shorten(String originalUrl) {
        if (!isValidUrl(originalUrl)) {
            throw new IllegalArgumentException("Invalid URL: " + originalUrl);
        }

        // چک کنیم آیا این URL قبلاً کوتاه شده
        if (longToShort.containsKey(originalUrl)) {
            return baseUrl + "/" + longToShort.get(originalUrl);
        }

        // کد جدید تولید کن
        String shortCode = generateShortCode();

        // ذخیره کن
        shortToLong.put(shortCode, originalUrl);
        longToShort.put(originalUrl, shortCode);

        return baseUrl + "/" + shortCode;
    }

    // تبدیل کد کوتاه به URL اصلی
    public String expand(String shortUrl) {
        String shortCode = extractShortCode(shortUrl);

        if (shortCode == null) {
            throw new IllegalArgumentException("Invalid short URL: " + shortUrl);
        }

        String originalUrl = shortToLong.get(shortCode);
        if (originalUrl == null) {
            throw new IllegalArgumentException("Short code not found: " + shortCode);
        }

        return originalUrl;
    }

    // تولید کد کوتاه با Base62
    private String generateShortCode() {
        long num = counter.getAndIncrement();
        return encodeBase62(num);
    }

    // تبدیل عدد به Base62
    private String encodeBase62(long num) {
        if (num == 0) return String.valueOf(BASE62_CHARS.charAt(0));

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62_CHARS.charAt((int) (num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }

    // استخراج کد کوتاه از URL
    private String extractShortCode(String shortUrl) {
        if (!shortUrl.startsWith(baseUrl + "/")) {
            return null;
        }
        return shortUrl.substring(baseUrl.length() + 1);
    }

    // تایید اعتبار URL
    private boolean isValidUrl(String url) {
        return url != null && urlPattern.matcher(url).matches();
    }

    // آمار سیستم
    public void printStats() {
        System.out.println("=== URL Shortener Stats ===");
        System.out.println("Total URLs: " + shortToLong.size());
        System.out.println("Next ID: " + counter.get());
        System.out.println("=========================");
    }

    // نمایش همه URLs
    public void printAllUrls() {
        System.out.println("\n=== All Shortened URLs ===");
        shortToLong.forEach((shortCode, originalUrl) -> {
            System.out.println(baseUrl + "/" + shortCode + " → " + originalUrl);
        });
        System.out.println("===========================\n");
    }
}
