package com.walter.handyestimate.data.model;

import java.math.RoundingMode;

public class EstimateMathContext {

    // Rounding method math context.
    public static final java.math.MathContext MATH_CONTEXT = new java.math.MathContext(2, RoundingMode.HALF_UP);
}
