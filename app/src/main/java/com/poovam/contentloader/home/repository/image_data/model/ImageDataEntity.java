package com.poovam.contentloader.home.repository.image_data.model;

import com.google.gson.annotations.SerializedName;
import com.poovam.contentloader.common.model.Category;
import com.poovam.contentloader.common.model.User;

import java.util.List;

/*) * Created by poovam-5255 on 8/18/2018.
 */

public class ImageDataEntity {

    
	@SerializedName("id")    
    public String id;
    
	@SerializedName("created_at")    
    public String createdAt;
    
	@SerializedName("width")    
    public Integer width;
    
	@SerializedName("height")    
    public Integer height;
    
	@SerializedName("color")    
    public String color;
    
	@SerializedName("likes")    
    public Integer likes;
    
	@SerializedName("liked_by_user")    
    public Boolean likedByUser;
    
	@SerializedName("user")    
    public User user;
    
	@SerializedName("current_user_collections")    
    public List<Object> currentUserCollections = null;
    
	@SerializedName("urls")    
    public Urls urls;
    
	@SerializedName("categories")    
    public List<Category> categories = null;
    
	@SerializedName("links")    
    public ImageLinks links;

    public class Urls {

	@SerializedName("raw")        
        public String raw;
        
	@SerializedName("full")        
        public String full;
        
	@SerializedName("regular")        
        public String regular;
        
	@SerializedName("small")        
        public String small;
        
	@SerializedName("thumb")        
        public String thumb;

    }

    public class ImageLinks {

	@SerializedName("self")        
        public String self;
        
	@SerializedName("html")        
        public String html;
        
	@SerializedName("download")        
        public String download;

    }
}
