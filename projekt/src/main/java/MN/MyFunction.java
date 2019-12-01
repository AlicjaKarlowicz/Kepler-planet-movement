package MN;

public class MyFunction implements ScalarFunction {

    @Override
    public double f(double x,double t,double e,double oT) {
        return M(t,oT) + e*Math.sin(x) -x;
    } //eccentric anomaly

    @Override
    public double derivative(double x,double e) {
        return e*Math.cos(x) - 1;
    }//for newton raphson method

    public double M(double t,double T) {
        return 2*Math.PI/T *(t-0);
    } //average anomaly

}
