package com.bluteam.personal.shorturl;

public class URLShortenerMain {
    public static void main(String[] args) {
        System.out.println("=====URL Shortener Started=====");

        // ایجاد instance
        URLShortener shortener = new URLShortener("https://short.ly");

        // URLs برای تست
        String[] testUrls = {
            "https://www.google.com/search?q=java+programming",
            "https://github.com/bluteam/awesome-project",
            "https://stackoverflow.com/questions/12345/how-to-implement-url-shortener",
            "https://docs.oracle.com/javase/tutorial/",
            "https://spring.io/projects/spring-boot"
        };

        // کوتاه کردن URLs
        System.out.println("📝 Shortening URLs:");
        for (String url : testUrls) {
            try {
                String shortened = shortener.shorten(url);
                System.out.println("✅ " + url + " → " + shortened);
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }

        // نمایش آمار
        shortener.printStats();
        shortener.printAllUrls();

        // تست expand کردن
        System.out.println("🔍 Testing URL expansion:");
        String[] shortUrls = {
            "https://short.ly/b",
            "https://short.ly/c",
            "https://short.ly/d",
            "https://short.ly/invalid"
        };

        for (String shortUrl : shortUrls) {
            try {
                String expanded = shortener.expand(shortUrl);
                System.out.println("✅ " + shortUrl + " → " + expanded);
            } catch (Exception e) {
                System.out.println("❌ " + shortUrl + " → Error: " + e.getMessage());
            }
        }

        // تست duplicate URL
        System.out.println("\n🔄 Testing duplicate URL:");
        String duplicateUrl = "https://www.google.com/search?q=java+programming";
        String shortened1 = shortener.shorten(duplicateUrl);
        String shortened2 = shortener.shorten(duplicateUrl);

        System.out.println("First time: " + shortened1);
        System.out.println("Second time: " + shortened2);
        System.out.println("Same result: " + shortened1.equals(shortened2));

        // تست invalid URL
        System.out.println("\n❌ Testing invalid URL:");
        try {
            shortener.shorten("invalid-url");
        } catch (Exception e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }

        System.out.println("\n=====URL Shortener Test Complete=====");
    }
}
