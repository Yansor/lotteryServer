package lottery.web.content.utils;

import java.util.Set;
import javautils.redis.JedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionCounterUtil
{
  @Autowired
  private JedisTemplate jedisTemplate;
  private static final String BOUNDED_HASH_KEY_PREFIX = "spring:session:sessions:*";
  
  public int getOnlineTotal()
  {
    Set<String> keys = null;
    try
    {
      keys = this.jedisTemplate.keys("spring:session:sessions:*");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return keys != null ? keys.size() : 0;
  }
}
