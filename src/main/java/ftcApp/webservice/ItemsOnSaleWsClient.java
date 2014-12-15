package ftcApp.webservice;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ItemsOnSaleWsClient {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("client-beans.xml");
        ItemsOnSaleWs service = context.getBean(ItemsOnSaleWs.class);
        String result = (String) service.getItemsOnSaleList();
        System.out.println(result);
    }
}
