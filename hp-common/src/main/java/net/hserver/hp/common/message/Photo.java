package net.hserver.hp.common.message;

import java.util.Arrays;

public class Photo {
    private PhotoType photoType;

    private byte[] data;

    public Photo(PhotoType photoType, byte[] data) {
        this.photoType = photoType;
        this.data = data;
    }

    public Photo() {
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public enum PhotoType {
        PNG(".png");
        private final String tips;

        PhotoType(String tips) {
            this.tips = tips;
        }

        public String getTips() {
            return tips;
        }
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoType=" + photoType +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
