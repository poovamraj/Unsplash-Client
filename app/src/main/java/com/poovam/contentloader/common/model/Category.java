package com.poovam.contentloader.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by poovam-5255 on 8/18/2018.
 */

public class Category {

    @SerializedName("id")
    public Integer id;

    @SerializedName("title")
    public String title;

    @SerializedName("photo_count")
    public Integer photoCount;

    @SerializedName("links")
    public CategoryLinks links;

    public class CategoryLinks {

        @SerializedName("self")
        public String self;

        @SerializedName("photos")
        public String photos;

    }
}