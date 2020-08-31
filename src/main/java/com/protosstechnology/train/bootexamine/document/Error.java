package com.protosstechnology.train.bootexamine.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Error {
    private final Integer code;
    private final String message;
}
