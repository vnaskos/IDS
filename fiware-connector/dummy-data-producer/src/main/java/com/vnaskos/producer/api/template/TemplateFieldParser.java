package com.vnaskos.producer.api.template;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class TemplateFieldParser {

    private Set<TemplateFieldType> fieldTypes;

    TemplateFieldParser() {
        fieldTypes = new HashSet<>();
        fieldTypes.add(new RandomFloatTemplateFieldType());
    }

    Object eval(final String rawValue) {
        return fieldTypes.stream()
                .map(fieldType -> fieldType.parse(rawValue))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(rawValue);
    }
}
