package com.vnaskos.ids.adapter.influxdb.resolver;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomFloatFieldTypeResolverTest {

    private final RandomFloatFieldTypeResolver resolver = new RandomFloatFieldTypeResolver();

    @Test
    public void rawValueIsReturnedWhenInputIsNotAString() {
        Float aFloatInput = 2.5F;
        Object resolvedFloatInput = resolver.resolve(aFloatInput);
        assertThat(resolvedFloatInput).isEqualTo(aFloatInput);
    }

    @Test
    public void rawValueIsReturnedWhenInputDoesNotMatchPattern() {
        String unknownRawInput = "A RANDOM INPUT";
        Object resolvedInput = resolver.resolve(unknownRawInput);
        assertThat(resolvedInput).isEqualTo(unknownRawInput);
    }

    @Test
    public void randomFloatRespectingDefaultLimitsIsReturnedWhenNoBoundsAreGiven() {
        String patternWithUpperBound = "${randFloat}";

        for (int i=0; i<100; i++) {
            Float resolvedValue = (Float) resolver.resolve(patternWithUpperBound);

            assertThat(resolvedValue)
                    .isGreaterThan(RandomFloatFieldTypeResolver.DEFAULT_LOWER_LIMIT)
                    .isLessThanOrEqualTo(RandomFloatFieldTypeResolver.DEFAULT_UPPER_LIMIT);
        }
    }

    @Test
    public void randomFloatRespectingUpperLimitIsReturnedWhenUpperBoundIsGiven() {
        String patternWithUpperBound = "${randFloat,200}";
        float upperBound = 200F;

        for (int i=0; i<100; i++) {
            Float resolvedValue = (Float) resolver.resolve(patternWithUpperBound);

            assertThat(resolvedValue)
                    .isGreaterThan(RandomFloatFieldTypeResolver.DEFAULT_LOWER_LIMIT)
                    .isLessThanOrEqualTo(upperBound);
        }
    }

    @Test
    public void randomFloatRespectingLimitsIsReturnedWhenUpperAndLowerBoundsAreGiven() {
        String patternWithUpperBound = "${randFloat,100,200}";
        float upperBound = 200F;
        float lowerBound = 100F;

        for (int i=0; i<100; i++) {
            Float resolvedValue = (Float) resolver.resolve(patternWithUpperBound);

            assertThat(resolvedValue)
                    .isGreaterThan(lowerBound)
                    .isLessThanOrEqualTo(upperBound);
        }
    }
}