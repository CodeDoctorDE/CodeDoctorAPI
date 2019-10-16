package com.gitlab.codedoctorde.api.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.*;

public class JsonConfigurationSection extends JsonConfigurationElement {
    private HashMap<String, JsonConfigurationElement> values = new HashMap<>();

    public JsonConfigurationSection() {

    }

    public JsonConfigurationSection(JsonObject jsonObject) {
        set(jsonObject);
    }

    public JsonConfigurationSection(HashMap<String, JsonConfigurationElement> jsonObject) {
        set(jsonObject);
    }

    public JsonConfigurationSection(Map<String, JsonElement> jsonObject) {
        set(jsonObject);
    }

    public JsonConfigurationSection getSection(String... path) {
        JsonConfigurationSection jsonConfigurationSection = this;
        for (String currentPath :
                path) {
            jsonConfigurationSection = (JsonConfigurationSection) jsonConfigurationSection.values.get(currentPath);
        }
        return jsonConfigurationSection;
    }

    public HashMap<String, JsonConfigurationElement> getValues() {
        return values;
    }

    public JsonConfigurationElement getElementValue(String... path) {
        return getSection(Arrays.copyOf(path, path.length - 1)).values.get(path[path.length - 1]);
    }

    public JsonConfigurationArray getArray(String... path) {
        return (JsonConfigurationArray) getElementValue(path);
    }

    public JsonConfigurationValue getValue(String... path) {
        return (JsonConfigurationValue) getElementValue(path);
    }

    public JsonConfigurationElement setValue(JsonConfigurationElement value, String... path) {
        JsonConfigurationSection section;
        if (path.length <= 0)
            return null;
        else if (path.length == 1)
            section = this;
        else
            section = getSection(Arrays.copyOf(path, path.length - 2));
        return section.values.put(path[path.length - 1], value);
    }

    public boolean hasKey(String... path) {
        return getSection(Arrays.copyOf(path, path.length - 2)).values.containsKey(path[path.length - 1]);
    }

    public int size(String... path) {
        return getSection(path).values.size();
    }

    public Set<String> getKeys(String... path) {
        return getSection(path).values.keySet();
    }

    public void setDefaults(final JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry :
                jsonObject.entrySet())
            if (!values.containsKey(entry.getKey())) {
                if (entry.getValue().isJsonObject())
                    values.put(entry.getKey(), new JsonConfigurationSection(entry.getValue().getAsJsonObject()));
                else if (entry.getValue().isJsonArray())
                    values.put(entry.getKey(), new JsonConfigurationArray(entry.getValue().getAsJsonArray()));
                else if (entry.getValue().isJsonPrimitive())
                    values.put(entry.getKey(), new JsonConfigurationValue(entry.getValue().getAsJsonPrimitive()));
            } else if (entry.getValue().isJsonObject())
                values.put(entry.getKey(), new JsonConfigurationSection(entry.getValue().getAsJsonObject()));
    }

    public void setDefaults(final JsonConfigurationSection jsonConfigurationSection) {
        for (Map.Entry<String, JsonConfigurationElement> entry :
                jsonConfigurationSection.values.entrySet())
            if (!values.containsKey(entry.getKey()))
                values.put(entry.getKey(), entry.getValue());
    }

    public void set(final JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry :
                jsonObject.entrySet())
            if (entry.getValue().isJsonObject())
                values.put(entry.getKey(), new JsonConfigurationSection(entry.getValue().getAsJsonObject()));
            else if (entry.getValue().isJsonArray())
                values.put(entry.getKey(), new JsonConfigurationArray(entry.getValue().getAsJsonArray()));
            else if (entry.getValue().isJsonPrimitive())
                values.put(entry.getKey(), new JsonConfigurationValue(entry.getValue().getAsJsonPrimitive()));
    }

    public void set(final JsonConfigurationSection jsonConfigurationSection) {
        values.putAll(jsonConfigurationSection.values);
    }

    public void set(final HashMap<String, JsonConfigurationElement> values) {
        this.values = values;
    }

    public void set(final Map<String, JsonElement> values) {
        this.values = new HashMap<>();
        for (Map.Entry<String, JsonElement> value :
                values.entrySet()) {
            if (value.getValue().isJsonPrimitive())
                setValue(value.getValue().getAsJsonPrimitive(), value.getKey());
            else if (value.getValue().isJsonObject())
                setValue(value.getValue().getAsJsonObject(), value.getKey());
            else if (value.getValue().isJsonArray())
                setValue(value.getValue().getAsJsonArray(), value.getKey());
        }
    }


    @Override
    public JsonObject getElement() {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, JsonConfigurationElement> entry :
                values.entrySet())
            if (entry.getValue() != null)
                jsonObject.add(entry.getKey(), entry.getValue().getElement());
        return jsonObject;
    }

    @Override
    public Object getObject() {
        return values;
    }

    public String getString(String... path) {
        return ((JsonConfigurationValue) getElementValue(path)).getString();
    }

    public Integer getInteger(String... path) {
        return ((JsonConfigurationValue) getElementValue(path)).getInteger();
    }

    public Long getLong(String... path) {
        return ((JsonConfigurationValue) getElementValue(path)).getLong();
    }

    public Boolean getBoolean(String... path) {
        return ((JsonConfigurationValue) getElementValue(path)).getBoolean();
    }

    public Character getCharacter(String... path) {
        return ((JsonConfigurationValue) getElementValue(path)).getCharacter();
    }

    public Float getFloat(String... path) {
        return ((JsonConfigurationValue) getElementValue(path)).getFloat();
    }

    public Double getDouble(String... path) {
        return ((JsonConfigurationValue) getElementValue(path)).getDouble();
    }

    public Byte getByte(String... path) {
        return ((JsonConfigurationValue) getElementValue(path)).getByte();
    }

    public List<JsonConfigurationElement> getList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getList();
    }

    public List<String> getStringList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getStringList();
    }

    public List<Integer> getIntegerList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getIntegerList();
    }

    public List<Long> getLongList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getLongList();
    }

    public List<Boolean> getBooleanList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getBooleanList();
    }

    public List<Character> getCharacterList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getCharacterList();
    }

    public List<Byte> getByteList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getByteList();
    }

    public List<Double> getDoubleList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getDoubleList();
    }

    public List<Float> getFloatList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getFloatList();
    }

    public List<JsonConfigurationSection> getSectionList(String... path) {
        return ((JsonConfigurationArray) getElementValue(path)).getSectionList();
    }

    public JsonElement getJsonElement(String... path) {
        return getElementValue(path).getElement();
    }

    public Map<String, Object> getObjectMap(String... path) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, JsonConfigurationElement> entry :
                getSection(path).values.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getObject());
        }
        return map;
    }

    public void setValue(JsonPrimitive value, String... path) {
        setValue(new JsonConfigurationValue(value));
    }

    public void setValue(String value, String... path) {
        setValue(new JsonConfigurationValue(new JsonPrimitive(value)), path);
    }

    public void setValue(Double value, String... path) {
        setValue(new JsonConfigurationValue(new JsonPrimitive(value)), path);
    }

    public void setValue(Float value, String... path) {
        setValue(new JsonConfigurationValue(new JsonPrimitive(value)), path);
    }

    public void setValue(Boolean value, String... path) {
        setValue(new JsonConfigurationValue(new JsonPrimitive(value)), path);
    }

    public void setValue(Character value, String... path) {
        setValue(new JsonConfigurationValue(new JsonPrimitive(value)), path);
    }

    public void setValue(Byte value, String... path) {
        setValue(new JsonConfigurationValue(new JsonPrimitive(value)), path);
    }

    public void setValue(Integer value, String... path) {
        setValue(new JsonConfigurationValue(new JsonPrimitive(value)), path);
    }

    public void setValue(String[] value, String... path) {
        JsonArray jsonArray = new JsonArray();
        for (String current :
                value)
            jsonArray.add(current);
        setValue(new JsonConfigurationArray(jsonArray), path);
    }

    public void setValue(Integer[] value, String... path) {
        JsonArray jsonArray = new JsonArray();
        for (Integer current :
                value)
            jsonArray.add(current);
        setValue(new JsonConfigurationArray(jsonArray), path);
    }

    public void setValue(Character[] value, String... path) {
        JsonArray jsonArray = new JsonArray();
        for (Character current :
                value)
            jsonArray.add(current);
        setValue(new JsonConfigurationArray(jsonArray), path);
    }

    public void setValue(Boolean[] value, String... path) {
        JsonArray jsonArray = new JsonArray();
        for (Boolean current :
                value)
            jsonArray.add(current);
        setValue(new JsonConfigurationArray(jsonArray), path);
    }

    public void setValue(Byte[] value, String... path) {
        JsonArray jsonArray = new JsonArray();
        for (Byte current :
                value)
            jsonArray.add(current);
        setValue(new JsonConfigurationArray(jsonArray), path);
    }

    public void setValue(Double[] value, String... path) {
        JsonArray jsonArray = new JsonArray();
        for (Double current :
                value)
            jsonArray.add(current);
        setValue(new JsonConfigurationArray(jsonArray), path);
    }

    public void setValue(Float[] value, String... path) {
        JsonArray jsonArray = new JsonArray();
        for (Float current :
                value) {
            jsonArray.add(current);
        }
        setValue(new JsonConfigurationArray(jsonArray), path);
    }

    public void setValue(JsonObject value, String... path) {
        setValue(new JsonConfigurationSection(value), path);
    }

    public void setValue(JsonArray value, String... path) {
        setValue(new JsonConfigurationArray(value), path);
    }

    public boolean setValue(Object value, String... path) {
        if (value instanceof String)
            setValue((String) value, path);
        else if (value instanceof Integer)
            setValue((Integer) value, path);
        else if (value instanceof Character)
            setValue((Character) value, path);
        else if (value instanceof Float)
            setValue((Float) value, path);
        else if (value instanceof Double)
            setValue((Double) value, path);
        else if (value instanceof Boolean)
            setValue((Boolean) value, path);
        else if (value instanceof Byte)
            setValue((Byte) value, path);
        else if (value instanceof String[])
            setValue((String[]) value, path);
        else if (value instanceof Integer[])
            setValue((Integer[]) value, path);
        else if (value instanceof Character[])
            setValue((Character[]) value, path);
        else if (value instanceof Float[])
            setValue((Float[]) value, path);
        else if (value instanceof Double[])
            setValue((Double[]) value, path);
        else if (value instanceof Byte[])
            setValue((Byte[]) value, path);
        else if (value instanceof Boolean[])
            setValue((Boolean[]) value, path);
        else if (value instanceof JsonConfigurationElement)
            setValue((JsonConfigurationElement) value, path);
        else
            return false;
        return true;
    }

    public boolean setObjectMapValue(Map<String, Object> value, String... path) {
        JsonConfigurationSection section = new JsonConfigurationSection();
        for (Map.Entry<String, Object> entry :
                value.entrySet()) {
            if (!section.setValue(value, path))
                return false;
        }
        setValue(section, path);
        return true;
    }

    public void setHashMapValue(HashMap<String, JsonConfigurationElement> value, String... path) {
        setValue(new JsonConfigurationSection(value), path);
    }

    public void clear() {
        values.clear();
    }

    public void setValue(JsonConfigurationElement[] value, String... path) {
        setValue(new JsonConfigurationArray(value), path);
    }

    @Override
    public void fromElement(final JsonElement jsonElement) {
        for (Map.Entry<String, JsonElement> entry :
                jsonElement.getAsJsonObject().entrySet())
            if (entry.getValue().isJsonObject())
                values.put(entry.getKey(), new JsonConfigurationSection(entry.getValue().getAsJsonObject()));
            else if (entry.getValue().isJsonArray())
                values.put(entry.getKey(), new JsonConfigurationArray(entry.getValue().getAsJsonArray()));
            else if (entry.getValue().isJsonPrimitive())
                values.put(entry.getKey(), new JsonConfigurationValue(entry.getValue().getAsJsonPrimitive()));
    }
}
