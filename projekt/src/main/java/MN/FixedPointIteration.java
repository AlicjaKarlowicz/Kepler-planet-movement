package MN;

public class FixedPointIteration extends Method {


    public FixedPointIteration(ScalarFunction f, double xi, double givenEa, double e, double a, double oT) {
        super(f, xi, givenEa, e, a, oT);
    }

    @Override
    public double solve(double t) {


        double xi = getXi();
        double xr=0;
        double xrprev = getXi();
        int i=1;


        do {
            xr = getF().f(xi,t,getE(),getoT()) + xi;



            xi = xr;
            xrprev = xr;

            xr = getF().f(xi,t,getE(),getoT()) + xi;
        } while(ea(xr,xrprev) > getGivenEa());

        return xr;
    }


}
