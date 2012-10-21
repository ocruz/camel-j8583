package org.apache.camel.component.j8583;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ocruz
 * Date: 10/19/12
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class J8583RouteTest extends CamelTestSupport {

    //@Test
    public void testSendMessage(){
        MockEndpoint mock = getMockEndpoint("mock:iso200");
        mock.expectedMessageCount(1);

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                DataFormat j8583 = new J8583DataFormat();
                from("mina2:tcp://127.0.0.1:4045?sync=true").
                        unmarshal(j8583).
                        choice().
                        when(header(J8583Constants.J8583_MESSAGE_TYPE).isEqualTo("0200")).
                        to("mock:iso200").
                        when(header(J8583Constants.J8583_MESSAGE_TYPE).isEqualTo("0420")).
                        to("mock:iso220").
                        otherwise().to("mock:invalid").
                        end().
                        marshal(j8583);
            }
        };
    }
}
