package eu.artofcoding.wbh.bookworm;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookwormMain {

    private static final AtomicBoolean completed = new AtomicBoolean(false);

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        // Spring Filesystem Application Context
        final String[] filesystemConfigLocation = new String[]{
                "conf/bookworm-datasource.xml",
                "classpath:META-INF/spring-context.xml",
                "conf/bookworm-camel.xml"
        };
        final ApplicationContext applicationContext0 = new FileSystemXmlApplicationContext(filesystemConfigLocation);
        /*
        // Spring Classpath XML Application Context
        final String[] xmlConfigLocation = new String[]{
                "META-INF/spring-context.xml"
        };
        final ApplicationContext applicationContext1 = new ClassPathXmlApplicationContext(xmlConfigLocation, applicationContext0);
        */
        // Get Camel context
        final SpringCamelContext camel = (SpringCamelContext) applicationContext0.getBean("bookwormCamelContext");
        // Stop Camel when JVM shuts down
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    camel.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        // Await countdown latch... it's intended to wait forever
        while (!completed.get()) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

}
