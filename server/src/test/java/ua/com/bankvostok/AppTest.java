package ua.com.bankvostok;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void testHello() {
        assertTrue(true);
    }

    @Test
    void checkRedisConnection() {
        redisTemplate.dump(123);
    }
}
