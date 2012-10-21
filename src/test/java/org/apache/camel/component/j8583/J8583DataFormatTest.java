package org.apache.camel.component.j8583;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ocruz
 * Date: 10/19/12
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class J8583DataFormatTest extends CamelTestSupport {

    private IsoMessage iso200;
    private MessageFactory messageFactory;

    @Before
    public void prepare() throws IOException {
        messageFactory = ConfigParser.createDefault();
        iso200 = messageFactory.newMessage(0x200);
    }
    
    @Test
    public void testUnMarshal() throws IOException, InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:unmarshal");
        mockEndpoint.setExpectedMessageCount(1);
        mockEndpoint.message(0).body().isInstanceOf(IsoMessage.class);
        byte[] message = MessageCreatorUtil.createIso200Message();
        template.sendBody("direct:unmarshal", message);
        assertMockEndpointsSatisfied();

    }
    

    @Test
    public void testMarshall() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:marshal");
        mockEndpoint.setExpectedMessageCount(1);
        mockEndpoint.message(0).body().isNotNull();
        template().sendBody("direct:marshal", iso200);
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                DataFormat dataFormat = new J8583DataFormat();
                from("direct:marshal").marshal(dataFormat).to("mock:marshal");
                from("direct:unmarshal").unmarshal(dataFormat).to("mock:unmarshal");
            }
        };
    }
}
