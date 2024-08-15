package io.github.zxh111222.parser;

import io.github.zxh111222.dto.CustomResult;
import io.github.zxh111222.util.MyReflectionUtil;

import java.util.List;

public interface Parser {

    static Parser getInstance(String parser) { return MyReflectionUtil.getInstance(parser);}

    List<CustomResult> parse(String html);

}
