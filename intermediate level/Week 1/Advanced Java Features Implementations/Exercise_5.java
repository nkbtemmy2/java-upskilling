class ThreadSafeCache<K, V> {
    private final java.util.concurrent.ConcurrentHashMap<K, CacheItem<V>> cache;
    private final long defaultTtlMillis; // Time to live in milliseconds

    public ThreadSafeCache(long defaultTtlMillis) {
        this.cache = new java.util.concurrent.ConcurrentHashMap<>();
        this.defaultTtlMillis = defaultTtlMillis;

        // Start a cleanup thread to remove expired items
        startCleanupThread();
    }

    public void put(K key, V value) {
        put(key, value, defaultTtlMillis);
    }

    public void put(K key, V value, long ttlMillis) {
        long expiryTime = System.currentTimeMillis() + ttlMillis;
        cache.put(key, new CacheItem<>(value, expiryTime));
    }

    public V get(K key) {
        CacheItem<V> item = cache.get(key);
        if (item == null) {
            return null;
        }

        // Check if the item has expired
        if (System.currentTimeMillis() > item.expiryTime) {
            cache.remove(key);
            return null;
        }

        return item.value;
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        // This might not be accurate as some items may have expired
        return cache.size();
    }

    private void startCleanupThread() {
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(defaultTtlMillis / 2); // Run cleanup at half the TTL interval

                    long currentTime = System.currentTimeMillis();
                    cache.entrySet().removeIf(entry -> currentTime > entry.getValue().expiryTime);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        cleanupThread.setDaemon(true); // Make it a daemon thread so it doesn't prevent JVM shutdown
        cleanupThread.start();
    }

    // Inner class to store cache items with expiry time
    private static class CacheItem<V> {
        final V value;
        final long expiryTime;

        CacheItem(V value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }
}