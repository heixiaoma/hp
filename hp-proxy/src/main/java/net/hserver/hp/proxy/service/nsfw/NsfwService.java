package net.hserver.hp.proxy.service.nsfw;

public interface NsfwService {
    float getPrediction(byte[] imgBytes);
}
