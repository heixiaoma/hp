package net.hserver.hp.common.protocol;


import net.hserver.hp.common.exception.HpException;


/**
 * @author hxm
 */

public enum HpMessageType {

    REGISTER(1),
    REGISTER_RESULT(2),
    CONNECTED(3),
    DISCONNECTED(4),
    DATA(5),
    KEEPALIVE(6);

    private int code;

    HpMessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static HpMessageType valueOf(int code) throws HpException {
        for (HpMessageType item : HpMessageType.values()) {
            if (item.code == code) {
                return item;
            }
        }
        throw new HpException("HpMessageType code error: " + code);
    }
}
