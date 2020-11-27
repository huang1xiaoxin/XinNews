package com.hx.app.xinnews.bean;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

public final class Items extends ArrayList<Object> {

    public Items(int initialCapacity) {
        super(initialCapacity);
    }

    public Items() {
        super();
    }


    public Items(@NonNull Collection<?> c) {
        super(c);
    }
}
