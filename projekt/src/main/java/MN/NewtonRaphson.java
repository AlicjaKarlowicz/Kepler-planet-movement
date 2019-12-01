package MN;

public class NewtonRaphson extends Method {


    public NewtonRaphson(ScalarFunction f, double xi, double givenEa, double e, double a, double oT) {
        super(f, xi, givenEa, e, a, oT);
    }

    @Override
    public double solve(double t) {

        double xi = getXi();
        double xrprev=getXi();
        double xr=0;
        int i=1;


        do{
            xr = xi - (getF().f(xi,t,getE(),getoT())/getF().derivative(xi,getE()));



            xi = xr;
            xrprev=xr;
            xr = xi - (getF().f(xi,t,getE(),getoT())/getF().derivative(xi,getE()));
        }while (ea(xr,xrprev) > getGivenEa());

        return xr;
    }


}

