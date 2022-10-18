package be.shark_zekrom.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NameUtil {
    public static List<String> filterByStart(List<String> list, String startingWith) {

        return list == null || startingWith == null ? Collections.emptyList() : list.stream()
                .filter(name -> name.toLowerCase().startsWith(startingWith.toLowerCase())).collect(Collectors.toList());

    }
}
