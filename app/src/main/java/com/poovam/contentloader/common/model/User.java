package com.poovam.contentloader.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by poovam-5255 on 8/18/2018.
 */

public class User {

    @SerializedName("id")
    public String id;

    @SerializedName("username")
    public String username;

    @SerializedName("name")
    public String name;

    @SerializedName("profile_image")
    public ProfileImage profileImage;

    @SerializedName("links")
    public UserLinks links;

    public class ProfileImage {

        @SerializedName("small")
        public String small;

        @SerializedName("medium")
        public String medium;

        @SerializedName("large")
        public String large;
    }

    public class UserLinks {

        @SerializedName("self")
        public String self;

        @SerializedName("html")
        public String html;

        @SerializedName("photos")
        public String photos;

        @SerializedName("likes")
        public String likes;
    }
}