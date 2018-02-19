package com.hafizjef.tasmuq.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MushafResponse {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("imageModels")
    @Expose
    private List<ImageModel> imageModels = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ImageModel> getImageModels() {
        return imageModels;
    }

    public void setImageModels(List<ImageModel> imageModels) {
        this.imageModels = imageModels;
    }

}
