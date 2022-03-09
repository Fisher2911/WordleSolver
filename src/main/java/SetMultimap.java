import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class SetMultimap<K, V> {

    private final Map<K, Set<V>> map;
    private final Supplier<Set<V>> supplier;

    public SetMultimap(final Map<K, Set<V>> map, final Supplier<Set<V>> supplier) {
        this.map = map;
        if (supplier == null) {
            this.supplier = HashSet::new;
            return;
        }
        this.supplier = supplier;
    }

    public SetMultimap(final Map<K, Set<V>> map) {
        this(map, HashSet::new);
    }

    public Set<V> get(final K key) {
        if (key == null) return new HashSet<>();
        Set<V> set = this.map.get(key);
        if (set == null) {
            set = supplier.get();
            this.map.put(key, set);
        }
        return set;
    }

    public void put(final K key, final V value) {
        this.get(key).add(value);
    }

    public Collection<Set<V>> values() {
        return this.map.values();
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public void putAll(final Map<K, V> map) {
        for (final var entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public Map<K, Set<V>> getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "SetMultimap{" +
                "map=" + map +
                '}';
    }

    public Set<Map.Entry<K, Set<V>>> entries() {
        return this.map.entrySet();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        final SetMultimap<?, ?> that = (SetMultimap<?, ?>) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
