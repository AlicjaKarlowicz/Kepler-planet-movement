package MN;


public class Secant extends Method {
    private double xiPrev;

    public Secant(ScalarFunction f, double xi, double givenEa, double e, double a, double oT, double xiPrev) {
        super(f, xi, givenEa, e, a, oT);
        this.xiPrev = xiPrev;
    }

    public double getXiPrev() {
        return xiPrev;
    }

    public void setXiPrev(double xiPrev) {
        this.xiPrev = xiPrev;
    }

    @Override
    public double solve(double t) {

        double xiPrev = getXiPrev();
        double xi = getXi();
        double xr;
        double xrprev=getXi();
        int i=1;


        do{

            xr = xi - (getF().f(xi,t,getE(),getoT())*(xiPrev-xi))/(getF().f(xiPrev,t,getE(),getoT())-getF().f(xi,t,getE(),getoT()));


            xiPrev = xi;
            xi = xr;
            xrprev=xr;

            xr = xi - (getF().f(xi,t,getE(),getoT())*(xiPrev-xi))/(getF().f(xiPrev,t,getE(),getoT())-getF().f(xi,t,getE(),getoT()));

        }while (ea(xr,xrprev) > getGivenEa());


        return xr;
    }


}
