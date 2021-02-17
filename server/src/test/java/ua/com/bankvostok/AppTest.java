package ua.com.bankvostok;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppTest {

    private StringRedisTemplate redisTemplate;

    @Autowired
    public AppTest(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Test
    void testRedisConnection() {
        assertNotNull(redisTemplate);
        try {
            redisTemplate.getConnectionFactory().getConnection();
        } catch (RedisConnectionFailureException exception) {
            fail(exception.getMessage());
        }
    }
}
