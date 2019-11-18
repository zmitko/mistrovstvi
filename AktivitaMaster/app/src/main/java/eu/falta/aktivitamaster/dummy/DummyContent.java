package eu.falta.aktivitamaster.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static Map<String, DummyItem> ITEM_MAP =
            new HashMap<String, DummyItem>();

    static {
        addItem(new DummyItem("1", "Nougat",
                "https://developer.android.com/about/versions/nougat/index.html"));
        addItem(new DummyItem("2", "Marshmallow",
                "https://developer.android.com/about/versions/marshmallow/index.htmlm"));
        addItem(new DummyItem("3", "Lollipop",
                "https://developer.android.com/about/versions/lollipop.html"));
        addItem(new DummyItem("4", "KitKat",
                "https://developer.android.com/about/versions/kitkat.html"));
        addItem(new DummyItem("5", "JellyBean",
                "https://developer.android.com/about/versions/jelly-bean.html"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position,
                makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class DummyItem {
        public String id;
        public String website_name;
        public String website_url;

        public DummyItem(String id, String website_name,
                         String website_url) {
            this.id = id;
            this.website_name = website_name;
            this.website_url = website_url;
        }

        @Override
        public String toString() {
            return website_name;
        }
    }
}
