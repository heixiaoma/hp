package net.hserver.hp.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author hxm
 */
public class Data implements Serializable {

    private boolean flag;

    private boolean active;

    private byte[] data;

    public Data() {
    }

    public Data(boolean flag, byte[] data) {
        this.flag = flag;
        this.data = data;
    }

    public Data(boolean active) {
        this.active = active;
    }

    public Data(boolean flag, boolean active, byte[] data) {
        this.flag = flag;
        this.active = active;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "flag=" + flag +
                ", active=" + active +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
