package dev.online.mapper;

public interface Mapper<T, R> {
    public R map(T obj);
}
