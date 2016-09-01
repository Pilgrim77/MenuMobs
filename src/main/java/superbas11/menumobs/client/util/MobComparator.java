package superbas11.menumobs.client.util;

import java.util.Comparator;
import java.util.Map;

public class MobComparator implements Comparator<Map.Entry<Object, String>> {

    @Override
    public int compare(Map.Entry<Object, String> o1, Map.Entry<Object, String> o2) {
        String mob1 = o1.getKey().toString();
        String mob2 = o2.getKey().toString();
        if (mob1.contains(".") == mob2.contains(".")) {
            return mob1.compareToIgnoreCase(mob2);
        } else if (mob1.contains(".")) {
            return 1;
        } else {
            return -1;
        }
    }
}
