package sudokuBacktraking;

import backtracking.Backtracking;
import java.util.ArrayList;
import java.util.Collection;

class Cella {
    int x, y;

    public Cella(int x, int y) {
        this.x = x;
        this.y = y;
    }
}//Cella

public class Sudoku extends Backtracking<Cella,Integer> {

    private int[][] griglia;

    public Sudoku(){
        griglia=new int[9][9];
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                griglia[i][j]=0;
    }//costruttore

    public Sudoku(int[][] griglia){
        if(griglia.length!=9)
            throw new IllegalArgumentException();
        for(int i=0;i<griglia.length;i++)
            if(griglia[i].length!=9)
                throw new IllegalArgumentException();
        this.griglia=griglia;
    }//costruttore

    public void assegna(int i, int j,int x){
        griglia[i][j]=x;
    }//assegna

    @Override
    protected boolean assegnabile(Cella cella, Integer integer) {
        if(!(controlloSottogriglia(cella,integer)))
            return false;
        //controllo sulla colonna a cui appartiene il la cella
        for(int i=0;i<griglia.length;i++)
            if(i!=cella.x)
                if(griglia[i][cella.y]==integer)
                    return false;
        //controllo sulla riga a cui appartine la cella
        for(int j=0;j<griglia.length;j++)
            if(j!=cella.y)
                if(griglia[cella.x][j]==integer)
                    return false;
        return true;
    }//assegnabile

    private boolean controlloSottogriglia(Cella cella, Integer x){
        Integer iPartenza=cella.x/3;
        Integer jPartenza=cella.y/3;
        iPartenza=iPartenza*3;
        jPartenza=jPartenza*3;
        for(int i=iPartenza;i<(iPartenza+3);i++)
            for(int j=jPartenza;j<(jPartenza+3);j++)
                if(i!= cella.x || j!= cella.y)
                    if(griglia[i][j]==x)
                        return false;
        return true;
    }//controlloSottogriglia

    @Override
    protected void assegna(Cella ps, Integer integer) {
        griglia[ps.x][ps.y]=integer;
    }//assegna

    @Override
    protected void deassegna(Cella ps, Integer integer) {
        griglia[ps.x][ps.y]=0;
    }//deassegna

    @Override
    protected void scriviSoluzione() {
        for(int i=0;i<griglia.length;i++) {
            for (int j = 0; j < griglia.length; j++){
                System.out.print(griglia[i][j]+"\t");
                if((j+1)%3==0)
                    System.out.print("|");
            }
            if((i+1)%3==0)
                System.out.println("\n-------------------------------------");
            else
                System.out.println();
        }
        System.out.println("SOLUZIONE NUMERO: "+numeroSoluzioni);
        System.out.println("##############################################");
    }//scriviSoluzione

    @Override
    protected boolean esisteSoluzione() {
        for(int i=0;i<griglia.length;i++)
            for(int j=0;j< griglia.length;j++)
                if(griglia[i][j]==0)
                    return false;
        return true;
    }//esiteSoluzione

    @Override
    protected Collection<Integer> scelte(Cella cella) {
        ArrayList<Integer> r=new ArrayList<>();
        r.add(1);r.add(2);r.add(3);r.add(4);r.add(5);r.add(6);r.add(7);r.add(8);r.add(9);
        return r;
    }

    @Override
    protected boolean esisteProssimoPuntoDiScelta(Cella cella) {
        for(int i=0;i<griglia.length;i++)
            for(int j=0;j<griglia.length;j++)
                if(griglia[i][j]==0)
                    return true;
        return false;
    }//esiteProssimoPuntoDiscelta

    @Override
    protected Cella prossimoPuntoDiScelta(Cella cella) {
        for(int i=0;i<griglia.length;i++)
            for(int j=0;j<griglia.length;j++)
                if(griglia[i][j]==0)
                    return new Cella(i,j);
        return null;
    }//prossimo

    /*public static void main(String[] args){
        int[][] v={{4,9,5,7,3,0,0,2,6},
                   {1,8,0,0,4,0,7,5,0},
                   {2,0,3,0,0,1,0,4,0},
                   {0,2,0,0,0,5,3,0,4},
                   {6,0,1,3,0,0,9,0,5},
                   {3,5,0,0,9,0,2,1,0},
                   {0,0,0,5,1,0,6,0,0},
                   {0,6,0,0,7,3,0,0,1},
                   {7,0,0,2,0,9,0,3,0}};
        Sudoku s=new Sudoku(v);
        s.risolvi(new Cella(0,0));
    }//main*/
}//Sudoku