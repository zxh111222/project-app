package io.github.zxh111222.notificator;

import io.github.zxh111222.dto.CustomResult;
import io.github.zxh111222.util.MyReflectionUtil;

import java.util.ArrayList;
import java.util.List;
import static io.github.zxh111222.App.PROPERTIES;

public interface Notificator {

    static Notificator getInstance() {
        return MyReflectionUtil.getInstance(PROPERTIES.getProperty("notificator"));
    }

    void notify(List<CustomResult> information) throws Exception;

    static String getMsgFromResult(List<CustomResult> results) {
        String[] kws = PROPERTIES.getProperty("keywords").split(",");
        List<CustomResult> hitResults;
        StringBuilder sb = new StringBuilder();
        for (String kw : kws) {
            hitResults = new ArrayList<>();
            for (CustomResult cr : results) {
                if (cr.getTitle().contains(kw)) {
                    hitResults.add(cr);
                }
            }
            if (!hitResults.isEmpty()) {
                sb.append("*** 命中关键词【").append(kw).append("】").append(hitResults.size()).append("条\n");
                for (CustomResult hitCr : hitResults) {
                    sb.append(hitCr.getTitle()).append("\n").append(hitCr.getUrl()).append("\n").append(hitCr.getCreatedAt()).append("\n").append(hitCr.getUpdatedAt()).append("\n");
                }
            }
        }
        return sb.toString();
    }

}
