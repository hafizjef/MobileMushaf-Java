package com.hafizjef.tasmuq.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailResponse {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("verseList")
    @Expose
    private List<String> verseList = null;
    @SerializedName("overlapList")
    @Expose
    private List<String> overlapList = null;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getVerseList() {
        return verseList;
    }

    public void setVerseList(List<String> verseList) {
        this.verseList = verseList;
    }

    public List<String> getOverlapList() {
        return overlapList;
    }

    public void setOverlapList(List<String> overlapList) {
        this.overlapList = overlapList;
    }

}
