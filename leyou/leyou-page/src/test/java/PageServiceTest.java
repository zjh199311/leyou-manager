import com.leyou.page.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author river
 * @title: PageServiceTest
 * @projectName leyou
 * @description: TODO
 * @date 2019/8/1913:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PageService.class})
public class PageServiceTest {
     @Autowired
     private PageService pageService;
     @Test
     public  void createHtml(){
         pageService.createHtml(3L);
     }
}
