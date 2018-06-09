package com.hafizjef.tasmuq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("result")
    @Expose
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}