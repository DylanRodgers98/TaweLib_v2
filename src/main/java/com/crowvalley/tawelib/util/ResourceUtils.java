package com.crowvalley.tawelib.util;

import com.crowvalley.tawelib.model.resource.*;

public class ResourceUtils {

    public static ResourceType getResourceType(Resource resource) {
        if (resource instanceof Book) {
            return ResourceType.BOOK;
        } else if (resource instanceof Dvd) {
            return ResourceType.DVD;
        } else if (resource instanceof Laptop) {
            return ResourceType.LAPTOP;
        }
        return null;
    }
}
