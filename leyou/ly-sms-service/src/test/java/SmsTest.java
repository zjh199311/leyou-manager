import com.leyou.LySmsApplication;
import com.leyou.sms.properties.SmsProperties;
import com.leyou.sms.until.SmsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author river
 * @title: SmsTest
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/2313:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySmsApplication.class)
public class SmsTest {
    @Autowired
    private  SmsProperties smsProperties;
    @Autowired
    private  SmsUtil smsUtil;
    @Autowired
    private AmqpTemplate amqpTemplate;
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

   @Test
    public  void testSms(){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
   }

    @Test
    public void listenVerifyCod() throws InterruptedException {
        Map<String,String> map = new HashMap<>();
        map.put("phoneNumbers", "13968124519");
        map.put("code", "123456");
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", map);
        Thread.sleep(5000);
    }


}
