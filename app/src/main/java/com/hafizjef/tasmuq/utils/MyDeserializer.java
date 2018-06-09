package com.hafizjef.tasmuq.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hafizjef.tasmuq.model.Result;

import java.lang.reflect.Type;

public class MyDeserializer implements JsonDeserializer<Result> {
    @Override
    public Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement result = json.getAsJsonObject().get("result");
        return new Gson().fromJson(result, Result.class);
    }
}
