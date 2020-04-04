package com.crowvalley.tawelib.controller;

public interface SelectionAwareFXController<T> extends InitializableFXController {

    void setSelectedItem(T selectedItem);

}
