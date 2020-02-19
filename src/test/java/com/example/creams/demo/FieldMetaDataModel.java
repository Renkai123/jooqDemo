package com.example.creams.demo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author tangyingqi
 * @date 2019-10-31
 */
@Getter
@Setter
public class FieldMetaDataModel {

    private boolean required;
    private String fieldName;
    private String fieldType;
    private boolean systemField;
    private String content;
}
