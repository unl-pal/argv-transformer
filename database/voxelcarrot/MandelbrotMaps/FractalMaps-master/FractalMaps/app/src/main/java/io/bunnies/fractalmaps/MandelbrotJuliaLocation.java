package io.bunnies.fractalmaps;

public class MandelbrotJuliaLocation {
    private double[] mandelbrotGraphArea;
    private double[] juliaGraphArea;
    private double[] juliaParams;

    public static final double[] defaultMandelbrotGraphArea = new double[]{-3.1, 1.45, 5};
    public static final double[] defaultJuliaGraphArea = new double[]{-2.2, 1.25, 4.3};
    public static final double[] defaultJuliaParams = new double[]{-0.6, -0.01875}; //Julia params place it right in the middle of the Mandelbrot home.
}