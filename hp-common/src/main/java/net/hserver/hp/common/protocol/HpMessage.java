package net.hserver.hp.common.protocol;

import java.util.Map;


/**
 * @author hxm
 */
public class HpMessage {

    private HpMessageType type;
    private Map<String, Object> metaData;
    private byte[] data;

    public HpMessageType getType() {
        return type;
    }

    public void setType(HpMessageType type) {
        this.type = type;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
