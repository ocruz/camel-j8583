package org.apache.camel.component.j8583;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.util.ExchangeHelper;
import org.apache.camel.util.ObjectHelper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: ocruz
 * Date: 10/18/12
 * Time: 12:08 PM
 */
public class J8583DataFormat implements DataFormat{

    /**
     * j8583 configuration file
     */
    private MessageFactory messageFactory = null;
    private boolean assignDate = true;
    public static final int HEADER_MSG_SIZE = 12;

    /**
     * Default Constructor
     */
    public J8583DataFormat() throws IOException {
        this.messageFactory = ConfigParser.createDefault();
    }

    /**
     * Constructor specifying the j8583 configuration file;
     * @param configuration the path to the j8583 configuration file
     */
    public J8583DataFormat(String configuration) throws IOException {
        createMessageFactory(configuration);
    }

    /**
     * Constructor setting the reference to the {@linkplain MessageFactory}
     * @param messageFactory reference to the {@linkplain MessageFactory}
     */
    public J8583DataFormat(MessageFactory messageFactory){
        this.messageFactory = messageFactory;
    }

    /**
     * @see DataFormat#marshal(org.apache.camel.Exchange, Object, java.io.OutputStream)
     */
    @Override
    public void marshal(Exchange exchange, Object body, OutputStream stream) throws Exception {
        if(this.messageFactory == null)
            throw new Exception("MessageFactory is null. Configuration wasn't found.");
        //IsoMessage message = ExchangeHelper.convertToMandatoryType(exchange,IsoMessage.class, body);
        ObjectHelper.notNull(body, "Body can't be null for marshalling");
        IsoMessage message = (IsoMessage) body;
        stream.write(message.writeData());
    }

    /**
     * @see DataFormat#unmarshal(org.apache.camel.Exchange, java.io.InputStream)
     */
    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        if(this.messageFactory == null)
            throw new Exception("MessageFactory is null. Configuration wasn't found.");
        //byte[] message =  ExchangeHelper.convertToMandatoryType(exchange, byte[], stream);
        byte[] message = IOUtils.toByteArray(stream);
        IsoMessage isoMessage = this.messageFactory.parseMessage(message, HEADER_MSG_SIZE);
        IsoMessage isoResponse = this.messageFactory.createResponse(isoMessage);
        exchange.getOut().setHeader(J8583Constants.J8583_ORIGINAL_MESSAGE, message);
        exchange.getOut().setHeader(J8583Constants.J8583_MESSAGE_RESPONSE, isoResponse);
        setCommonHeaders(exchange, isoMessage);
        return isoMessage;
    }

    /**
     * Private method to create the message factory to parse/create
     * transactions
     *
     * @param configuration path to configuration file
     * @return the {@linkplain MessageFactory}
     * @throws IOException
     */
    private MessageFactory createMessageFactory(String configuration) throws IOException {
        MessageFactory messageFactory = configuration == null ?
                ConfigParser.createDefault() : ConfigParser.createFromClasspathConfig(configuration);
        messageFactory.setAssignDate(assignDate);
        return messageFactory;
    }

    /**
     * Private method to set common headers to the {@linkplain Exchange}
     * from the just created {@linkplain IsoMessage}
     *
     * @param exchange {@linkplain Exchange} reference
     * @param isoMessage {@linkplain IsoMessage} reference
     */
    private void setCommonHeaders(Exchange exchange, IsoMessage isoMessage) {
        String isoHeader = isoMessage.getIsoHeader();
        exchange.getOut().setHeader(J8583Constants.J8583_MESSAGE_HEADER, isoHeader);
        exchange.getOut().setHeader(J8583Constants.J8583_MESSAGE_HEADER_LENGTH, isoHeader.length());
        exchange.getOut().setHeader(J8583Constants.J8583_MESSAGE_TYPE, isoMessage.getType());
    }

    /**
     * Sets the flag to tell the message factory
     * automatically assign the date to IsoMessage.
     *
     * @param assignDate
     */
    public void setAssignDate(boolean assignDate) {
        this.assignDate = assignDate;
        messageFactory.setAssignDate(assignDate);
    }
}
