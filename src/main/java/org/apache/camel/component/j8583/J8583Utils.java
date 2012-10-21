package org.apache.camel.component.j8583;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: ocruz
 * Date: 10/19/12
 * Time: 9:07 AM
 * To change this template use File | Settings | File Templates.
 */
public final class J8583Utils {

    /**
     * Utility method to safely get a {@code String} value
     * from a field in the {@linkplain IsoMessage}
     *
     * @param isoMessage the referenced {@linkplain IsoMessage}
     * @param field the field to retrieve in the {@linkplain IsoMessage}
     * @return the {@code String} value
     */
    public static String getStringValue(IsoMessage isoMessage, int field) {
        if (!isoMessage.hasField(field) || !validStringValue(isoMessage.getField(field))) {
            return null;
        }
        return (String)isoMessage.getField(field).getValue();
    }

    private static boolean validStringValue(IsoValue<Object> field) {
        IsoType type = field.getType();
        return (type == IsoType.ALPHA || type == IsoType.LLLVAR || type == IsoType.LLLVAR );
    }

    /**
     * Utility method to safely get a {@code BigDecimal} value
     * from a field in the {@linkplain IsoMessage}
     *
     * @param isoMessage the referenced {@linkplain IsoMessage}
     * @param field the field to retrieve in the {@linkplain IsoMessage}
     * @return the {@code String} value
     */
    public static BigDecimal getBigDecimalValue(IsoMessage isoMessage, int field) {
        if (!isoMessage.hasField(field) || !validNumericValue(isoMessage.getField(field))) {
            return null;
        }
        return (BigDecimal) isoMessage.getField(field).getValue();
    }

    /**
     * Utility method to safely get a {@code Date} value
     * from a field in the {@linkplain IsoMessage}
     *
     * @param isoMessage the referenced {@linkplain IsoMessage}
     * @param field the field to retrieve in the {@linkplain IsoMessage}
     * @return the {@code Date} value
     */
    public static Date getDateValue(IsoMessage isoMessage, int field) {
        if (!isoMessage.hasField(field) || !validDateValue(isoMessage.getField(field))) {
            return null;
        }
        return (Date) isoMessage.getField(field).getValue();
    }

    /**
     * Private method to validate if field has a valid
     * {@code Date} {@code IsoType}
     *
     * @param field the {@linkplain IsoValue} field
     * @return true if is valid {@code Date} value, false otherwise
     */
    private static boolean validDateValue(IsoValue<Object> field) {
        IsoType type = field.getType();
        return (type == IsoType.DATE_EXP || type == IsoType.DATE4 || type == IsoType.DATE10);
    }

    /**
     * Utility method to safely get a int value
     * from a field in the {@linkplain IsoMessage}
     *
     * @param isoMessage the referenced {@linkplain IsoMessage}
     * @param field the field to retrieve in the {@linkplain IsoMessage}
     * @return the integer value
     */
    public static int getNumericValue(IsoMessage isoMessage, int field) {
        if (!isoMessage.hasField(field) || !validNumericValue(isoMessage.getField(field))) {
            return -1;
        }
        String number = (String) isoMessage.getField(field).getValue();
        return number != null ? Integer.parseInt(number) : -1;
    }

    private static boolean validNumericValue(IsoValue<Object> field) {
        IsoType type = field.getType();
        return (type == IsoType.AMOUNT || type == IsoType.NUMERIC);
    }

    /**
     * Utility method to safely set a {@code String}
     * value to a {@linkplain IsoMessage} using the corresponding
     * {@linkplain IsoType}
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     * @param positions the position that will be set.
     */
    public static void setStringValue(IsoMessage isoMessage, String value, int field, int positions) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.ALPHA, positions);
        }
    }

    /**
     * Utility method to safely set a {@code BigDecimal}
     * value to a {@linkplain IsoMessage} using the corresponding
     * {@linkplain IsoType}
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     */
    public static void setBigDecimalValue(IsoMessage isoMessage, BigDecimal value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.AMOUNT, 0);
        }
    }

    /**
     * Utility method to safely set a {@code Date}
     * value to a {@linkplain IsoMessage} as a TIME {@linkplain IsoType}
     *
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     */
    public static void setTimeValue(IsoMessage isoMessage, Date value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.TIME, 0);
        }
    }

    /**
     * Utility method to safely set a {@code Date}
     * value to a {@linkplain IsoMessage} as a DATE10 {@linkplain IsoType}
     *
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     */
    public static void setDateValue(IsoMessage isoMessage, Date value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.DATE10, 0);
        }
    }

    /**
     * Utility method to safely set a {@code Date}
     * value to a {@linkplain IsoMessage} as a DATE4 {@linkplain IsoType}
     *
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     */
    public static void setDate4Value(IsoMessage isoMessage, Date value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.DATE4, 0);
        }
    }

    /**
     * Utility method to safely set a {@code Date}
     * value to a {@linkplain IsoMessage} as a DATE_EXP {@linkplain IsoType}
     *
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     */
    public static void setDateExpValue(IsoMessage isoMessage, Date value, int field) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.DATE_EXP, 0);
        }
    }

    /**
     * Utility method to safely set a variable alphanumeric
     * value to a {@linkplain IsoMessage} using the corresponding
     * {@linkplain IsoType} depending on the variable length
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     * @param variableLength length to set the corresponding {@linkplain IsoType}
     */
    public static void setVarLengthValue(IsoMessage isoMessage, String value, int field, int variableLength) {
        IsoType isoType = null;
        if (value != null && (variableLength == 2 || variableLength == 3)) {
            if (variableLength == 2) {
                isoType = IsoType.LLVAR;
            } else if (variableLength == 3) {
                isoType = IsoType.LLLVAR;
            }
            isoMessage.setValue(field, value, isoType, 0);
        }
    }

    /**
     * Utility method to safely set a {@code int}
     * value to a {@linkplain IsoMessage} using the corresponding
     * {@linkplain IsoType}
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     * @param  length the length of the field
     */
    public static void setNumericValue(IsoMessage isoMessage, int value, int field, int length) {
        if (value != -1) {
            isoMessage.setValue(field, String.valueOf(value), IsoType.NUMERIC, length);
        }
    }

    /**
     * Utility method to safely set a {@code String}
     * value to a {@linkplain IsoMessage} using the corresponding
     * {@linkplain IsoType}
     *
     * @param isoMessage reference to the {@linkplain IsoMessage}
     * @param value the value to be stored
     * @param field the field number
     * @param  length the length of the field
     */
    public static void setNumericValue(IsoMessage isoMessage, String value, int field, int length) {
        if (value != null) {
            isoMessage.setValue(field, value, IsoType.NUMERIC, length);
        }
    }

    /**
     * Utility to correctly format a {@code Date} as a
     * {@linkplain IsoType} DATE10 field.
     *
     * @param date the {@code Date} for formatting
     * @return formatted date
     */
    public static String formatDate10(Date date) {
        IsoType isoType = IsoType.DATE10;
        return isoType.format(date);
    }

    /**
     * Utility to correctly format a {@code Date} as a
     * {@linkplain IsoType} DATE4 field.
     *
     * @param date the {@code Date} for formatting
     * @return formatted date
     */
    public static String formatDate4(Date date) {
        IsoType isoType = IsoType.DATE4;
        return isoType.format(date);
    }

    /**
     * Utility to correctly format a {@code Date} as a
     * {@linkplain IsoType} DateExp field.
     *
     * @param date the {@code Date} for formatting
     * @return formatted date
     */
    public static String formatDateExp(Date date) {
        IsoType isoType = IsoType.DATE_EXP;
        return isoType.format(date);
    }

    /**
     * Utility to correctly format a {@code Date} as a
     * {@linkplain IsoType} TIME field.
     *
     * @param date the {@code Date} for formatting
     * @return formatted date
     */
    public static String formatTime(Date date) {
        IsoType isoType = IsoType.TIME;
        return isoType.format(date);
    }

    /**
     * Utility to correctly format a {@code BigDecimal} as a
     * {@linkplain IsoType} Amount field.
     *
     * @param value the {@code BigDecimal} for formatting
     * @return formatted value
     */
    public static String formatAmount(BigDecimal value, int length) {
        IsoType isoType = IsoType.AMOUNT;
        return isoType.format(value, length);
    }

    /**
     * Utility to correctly format a {@code long} as a
     * {@linkplain IsoType} NUMERIC field.
     *
     * @param value the {@code long} for formatting
     * @return formatted value
     */
    public static String formatNumeric(long value, int length) {
        IsoType isoType = IsoType.NUMERIC;
        return isoType.format(value, length);
    }

    /**
     * Utility to correctly format a {@code String} as a
     * {@linkplain IsoType} NUMERIC field.
     *
     * @param value the {@code String} for formatting
     * @return formatted value
     */
    public static String formatNumeric(String value, int length) {
        IsoType isoType = IsoType.NUMERIC;
        return isoType.format(value, length);
    }

    /**
     * Utility to correctly format a {@code String} as a
     * {@linkplain IsoType} ALPHA field.
     *
     * @param value the {@code String} for formatting
     * @return formatted value
     */
    public static String formatAlphaNumeric(String value, int length) {
        IsoType isoType = IsoType.ALPHA;
        return isoType.format(value, length);
    }
}
