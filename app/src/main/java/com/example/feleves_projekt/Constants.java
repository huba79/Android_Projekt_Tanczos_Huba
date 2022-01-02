package com.example.feleves_projekt;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    //egy status map hogy megsporoljam a kulon valaszertelmezo reteget
    public static final Map<Integer, String> STATUSES;
    //egy color map a lista hatterszineit szinkodolni a statusok fuggvenyeben
    public static final Map<Integer, Integer> STATUS_COLORS;

    static {
        STATUSES = new HashMap<>();
        STATUSES.put(0, "Igenyelt");
        STATUSES.put(1, "Lefoglalt");
        STATUSES.put(2, "Kifizetve");
        STATUSES.put(3, "Használatban");
        STATUSES.put(4, "Lemondva");
        STATUSES.put(5, "Lejárt");

        STATUS_COLORS = new HashMap<>();
        STATUS_COLORS.put(0, Color.DKGRAY);
        STATUS_COLORS.put(1, Color.YELLOW);
        STATUS_COLORS.put(2, Color.BLUE);
        STATUS_COLORS.put(3, Color.BLACK);
        STATUS_COLORS.put(4, Color.RED);
        STATUS_COLORS.put(5, Color.GRAY);

    }
}
