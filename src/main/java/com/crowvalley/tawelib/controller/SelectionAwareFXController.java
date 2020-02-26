package com.crowvalley.tawelib.controller;

public interface SelectionAwareFXController<T> extends FXController {

    void setSelectedItem(T selectedItem);

}
