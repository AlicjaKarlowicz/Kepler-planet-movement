package MN;

public interface ScalarFunction {
    public abstract double f(double x, double t, double e, double oT);
    public abstract double derivative(double x, double e);
}
