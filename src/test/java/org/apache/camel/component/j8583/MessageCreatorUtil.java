package org.apache.camel.component.j8583;

import org.apache.camel.converter.IOConverter;
import org.apache.camel.util.ObjectHelper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: ocruz
 * Date: 10/21/12
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
public final class MessageCreatorUtil {

    public static byte[] createIso200Message() throws IOException {
        InputStream is = ObjectHelper.loadResourceAsStream("messages/iso200");
        return IOConverter.toBytes(is);
    }
}
