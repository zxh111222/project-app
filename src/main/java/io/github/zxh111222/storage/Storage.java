package io.github.zxh111222.storage;

import io.github.zxh111222.dto.CustomResult;
import io.github.zxh111222.util.MyReflectionUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Storage {

    static Storage getInstance(String storage) {
        return MyReflectionUtil.getInstance(storage);
    }

    void save(List<CustomResult> information) throws IOException, SQLException;
}
