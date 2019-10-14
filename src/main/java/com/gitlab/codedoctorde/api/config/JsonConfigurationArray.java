package com.gitlab.codedoctorde.api.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonConfigurationArray extends JsonConfigurationElement {
    private List<JsonConfigurationElement> values = new ArrayList<>();

    public JsonConfigurationArray(final JsonArray jsonArray) {
        for (JsonElement jsonElement :
                jsonArray)
            if (jsonElement.isJsonObject())
                values.add(new JsonConfigurationSection(jsonElement.getAsJsonObject()));
            else if (jsonElement.isJsonArray())
                values.add(new JsonConfigurationArray(jsonElement.getAsJsonArray()));
            else if (jsonElement.isJsonPrimitive())
                values.add(new JsonConfigurationValue(jsonElement.getAsJsonPrimitive()));
    }

    public JsonConfigurationArray() {
    }

    public JsonConfigurationArray(final JsonConfigurationElement[] elements) {
        values = Arrays.asList(elements);
    }

    public List<JsonConfigurationElement> getList() {
        return values;
    }

    public List<String> getStringList() {
        List<String> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationValue)
                list.add(((JsonConfigurationValue) value).getString());
        return list;
    }

    public List<Integer> getIntegerList() {
        List<Integer> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationValue)
                list.add(((JsonConfigurationValue) value).getInteger());
        return list;
    }

    public List<Long> getLongList() {
        List<Long> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationValue)
                list.add(((JsonConfigurationValue) value).getLong());
        return list;
    }

    public List<Boolean> getBooleanList() {
        List<Boolean> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationValue)
                list.add(((JsonConfigurationValue) value).getBoolean());
        return list;
    }

    public List<Character> getCharacterList() {
        List<Character> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationValue)
                list.add(((JsonConfigurationValue) value).getCharacter());
        return list;
    }

    public List<Byte> getByteList() {
        List<Byte> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationValue)
                list.add(((JsonConfigurationValue) value).getByte());
        return list;
    }

    public List<Double> getDoubleList() {
        List<Double> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationValue)
                list.add(((JsonConfigurationValue) value).getDouble());
        return list;
    }

    public List<Float> getFloatList() {
        List<Float> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationValue)
                list.add(((JsonConfigurationValue) value).getFloat());
        return list;
    }

    public List<JsonConfigurationSection> getSectionList() {
        List<JsonConfigurationSection> list = new ArrayList<>();
        for (JsonConfigurationElement value :
                values)
            if (value instanceof JsonConfigurationSection)
                list.add((JsonConfigurationSection) value);
        return list;
    }

    @Override
    public JsonElement getElement() {
        JsonArray jsonArray = new JsonArray();
        for (JsonConfigurationElement value :
                values)
            jsonArray.add(value.getElement());
        return jsonArray;
    }

    @Override
    public Object getObject() {
        return values;
    }
}
