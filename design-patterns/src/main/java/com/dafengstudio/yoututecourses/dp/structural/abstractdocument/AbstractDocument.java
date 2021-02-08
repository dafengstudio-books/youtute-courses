package com.dafengstudio.yoututecourses.dp.structural.abstractdocument;

import com.sun.istack.internal.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class AbstractDocument implements Document {
    private final Map<String, Object> properties;

    /**
     * 构造函数
     *
     * @param properties
     */
    protected AbstractDocument(Map<String, Object> properties) {
        Objects.requireNonNull(properties, "properties map is required");
        this.properties = properties;
    }

    @Override
    public Void put(String key, Object value) {
        properties.put(key, value);
        return null;
    }

    @Override
    public Object get(String key) {
        return properties.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        Stream<T> stream = (Stream<T>) Stream.of(get(key));
        return stream
                .filter(Objects::nonNull)
                .map(el -> (List<Map<String, Object>>) el)
                .findAny()
                .get()
                .stream()
                .map(constructor);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName()).append("[");
        properties.forEach((key, value) -> builder.append("[").append(key).append(" : ").append(value)
                .append("]"));
        builder.append("]");
        return builder.toString();
    }
}
