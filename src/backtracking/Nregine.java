package backtracking;


import java.util.*;

public class Nregine extends Backtracking<Integer, Integer> {

    private Boolean[][] a;
    private int n,nrSol=0;
    private int regineAssegnate=0;

    public Nregine(int n){
        if(n<=3)
            throw new IllegalArgumentException();
        a=new Boolean[n][n];
        for(int i=0;i<a.length;i++)
            for(int j=0;j<a[i].length;j++)
                a[i][j]=false;
        this.n=n;
    }//costruttore

    @Override
    protected boolean assegnabile(Integer r, Integer c) {
        for(int i=r-1;i>=0;i--)
            if(a[i][c])
                return false;
        for(int i=r-1,j=c-1;i>=0 && j>=0;i-- , j--)
            if(a[i][j])
                return false;
        for(int i=r-1,j=c-1;i>=0 && j<n-1;i-- , j++)
            if(a[i][j])
                return false;
        return true;
    }

    @Override
    protected void assegna(Integer r, Integer c) {
        a[r][c]=true;
        regineAssegnate++;
    }

    @Override
    protected void deassegna(Integer r,Integer c) {
        a[r][c]=false;
        regineAssegnate--;
    }

    @Override
    protected void scriviSoluzione() {
        nrSol++;
        System.out.print(nrSol+": ");
        for( int i=0; i<n; ++i ) {
            for( int j=0; j<n; ++j )
                if( a[i][j] ) {
                    System.out.print("<"+i+","+j+">");
                    break; //interrompe il ciclo di for interno
                }
        }
        System.out.println();
    }//scriviSoluzione

    @Override
    protected boolean esisteSoluzione() {
        return n==regineAssegnate;
    }

    @Override
    protected Integer prossimoPuntoDiScelta(Integer p) {
        if(p>=n)
            throw new IllegalStateException();
        return p++;
    }//ProssimoPuntoDiScelta

    @Override
    protected boolean esisteProssimoPuntoDiScelta(Integer integer) {
        return integer<n;
    }//esisteProssimoPuntoDiscelta

    @Override
    protected Collection<Integer> scelte(Integer a) {
        List<Integer> r=new ArrayList<>();
        for(int i=0;i<n;i++)
            r.add(i);
        return r;
    }

    public static void main (String[] args){
        Nregine n=new Nregine(4);
        n.risolvi(0);
    }
}
