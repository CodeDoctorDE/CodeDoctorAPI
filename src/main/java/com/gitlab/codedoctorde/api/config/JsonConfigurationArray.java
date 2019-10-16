package com.gitlab.codedoctorde.api.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.*;

public class JsonConfigurationArray extends JsonConfigurationElement implements List<JsonConfigurationElement> {
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

    public JsonConfigurationArray(List<JsonConfigurationElement> teams) {
        values = teams;
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
    public void fromElement(JsonElement element) {
        for (JsonElement jsonElement :
                element.getAsJsonArray())
            if (jsonElement.isJsonObject())
                values.add(new JsonConfigurationSection(jsonElement.getAsJsonObject()));
            else if (jsonElement.isJsonArray())
                values.add(new JsonConfigurationArray(jsonElement.getAsJsonArray()));
            else if (jsonElement.isJsonPrimitive())
                values.add(new JsonConfigurationValue(jsonElement.getAsJsonPrimitive()));
    }

    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public Iterator<JsonConfigurationElement> iterator() {
        return values.iterator();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return values.toArray(ts);
    }

    @Override
    public boolean add(JsonConfigurationElement jsonConfigurationElement) {
        return values.add(jsonConfigurationElement);
    }

    @Override
    public boolean remove(Object o) {
        return values.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return values.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends JsonConfigurationElement> collection) {
        return values.addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends JsonConfigurationElement> collection) {
        return values.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return values.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return values.retainAll(collection);
    }

    @Override
    public void clear() {
        values.clear();
    }

    @Override
    public JsonConfigurationElement get(int i) {
        return null;
    }

    @Override
    public JsonConfigurationElement set(int i, JsonConfigurationElement jsonConfigurationElement) {
        return values.set(i, jsonConfigurationElement);
    }

    @Override
    public void add(int i, JsonConfigurationElement jsonConfigurationElement) {
        values.add(i, jsonConfigurationElement);
    }

    @Override
    public JsonConfigurationElement remove(int i) {
        return values.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return values.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return values.lastIndexOf(o);
    }

    @Override
    public ListIterator<JsonConfigurationElement> listIterator() {
        return values.listIterator();
    }

    @Override
    public ListIterator<JsonConfigurationElement> listIterator(int i) {
        return values.listIterator(i);
    }

    @Override
    public List<JsonConfigurationElement> subList(int i, int i1) {
        return values.subList(i, i1);
    }
}
