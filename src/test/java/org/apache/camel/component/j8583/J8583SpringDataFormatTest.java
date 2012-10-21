package org.apache.camel.component.j8583;

import com.solab.iso8583.IsoMessage;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ocruz
 * Date: 10/21/12
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class J8583SpringDataFormatTest extends CamelSpringTestSupport {

    @Test
    public void testUnmarshal() throws IOException, InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:unmarshal");
        mockEndpoint.setExpectedMessageCount(1);
        mockEndpoint.message(0).body().isInstanceOf(IsoMessage.class);
        byte[] message = MessageCreatorUtil.createIso200Message();
        template.sendBody("direct:unmarshal", message);
        assertMockEndpointsSatisfied();
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("org/apache/camel/component/j8583/J8583SpringDataFormatTest-context.xml");
    }
}
