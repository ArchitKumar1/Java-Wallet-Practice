package practice.oops.api;

public interface HttpCaller<K,V> {
    V get();
    V post();
}
