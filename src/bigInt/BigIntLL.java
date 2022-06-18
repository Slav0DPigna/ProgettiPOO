package bigInt;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class BigIntLL extends AbstractBigInt{

    private LinkedList<Integer> numero;

    public BigIntLL(int numeroInt){
        if(numeroInt<0)
            throw new IllegalArgumentException();
        numero=new LinkedList<>();
        String s=""+numeroInt;
        for(int i=0;i<s.length();i++)
            numero.add(Integer.parseInt(s.charAt(i)+""));
    }//costruttore

    private BigIntLL(){

    }//costruttore privato

    @Override
    public BigInt factory(int x) {
        return new BigIntLL(x);
    }//factory

    @Override
    public int length(){
        return numero.size();
    }//lenght

    @Override
    public int compareTo(BigInt o) {
        if(this.value().length()>o.value().length())
            return 1;
        if(this.value().length()<o.value().length())
            return -1;
        else {
            ListIterator<Integer> thisIterator = numero.listIterator();
            String oString = o.value();
            LinkedList<Integer> tmp = new LinkedList<>();
            for (int i = 0; i < oString.length(); i++)
                tmp.add(Integer.parseInt(oString.charAt(i) + ""));
            ListIterator<Integer> oIterator = tmp.listIterator();
            while ((thisIterator.hasNext())) {
                int thisCifraSignificativa=thisIterator.next();
                int oCifraSignificativa=oIterator.next();
                if (thisCifraSignificativa>oCifraSignificativa)
                    return 1;
                if (thisCifraSignificativa<oCifraSignificativa)
                    return -1;
            }
        }
        return 0;
    }//compareTo

    @Override
    public BigInt add(BigInt a) {
        LinkedList<Integer> tmp=new LinkedList<>();//tmp conterrà i risultati delle somma ma al contario
        LinkedList<Integer> numeriDiA=new LinkedList<>();
        ListIterator<Integer> thisIterato= this.numero.listIterator(numero.size());//iterator su lista che contiene le cifre di this
        String aString=a.value();
        for(int i=0;i<aString.length();i++)
            numeriDiA.add(Integer.parseInt(aString.charAt(i)+""));
        ListIterator<Integer> aIterator=numeriDiA.listIterator(numeriDiA.size());//Iterator su lista che contiene le cifre di a
        //sommo tutte le cifre che posso sommare usando il riporto e scorrendo a ritroso le due liste
        int riporto=0;
        while(aIterator.hasPrevious() && thisIterato.hasPrevious()){
            int somma =aIterator.previous()+thisIterato.previous()+riporto;
            if(somma>=10) {
                tmp.add(somma - 10);
                riporto=1;
            }
            else {
                tmp.add(somma);
                riporto=0;
            }
        }
        //quando una dei due iterarori è arrivato in testa mi posso limitare a aggiungere le cifre rimanenti tendendo conto del riporto
        if(aIterator.hasPrevious() || thisIterato.hasPrevious()) {
            while (aIterator.hasPrevious()) {
                int somma = aIterator.previous() + riporto;
                if (somma >= 10) {
                    tmp.add(somma - 10);
                    riporto = 1;
                } else {
                    tmp.add(somma);
                    riporto = 0;
                }
            }
            while (thisIterato.hasPrevious()) {
                int somma = thisIterato.previous() + riporto;
                if (somma >= 10) {
                    tmp.add(somma - 10);
                    riporto = 1;
                } else {
                    tmp.add(somma);
                    riporto = 0;
                }
            }
        }
        //se il riporto continua ad essere 1 lo aggiungo semplicemente in coda a tmp
        if (riporto==1)
            tmp.add(riporto);
        LinkedList<Integer> lr=new LinkedList<>();
        ListIterator<Integer> tmpIterator=tmp.listIterator(tmp.size());
        //aggiungo in testa a lr i numeri in coda a tmp così facendo ho le cifre nell'ordine giusto
        while(tmpIterator.hasPrevious())
            lr.add(tmpIterator.previous());
        //poi creando semplicemente un BigIntLL senza campi vado ad assegnargli come numero lr e restituisco
        BigIntLL r=new BigIntLL();
        r.numero=lr;
        return r;
    }//add

    @Override
    public BigInt sub(BigInt s) {
        if(this.compareTo(s)<0)//mi assicuro che il risultato non sia un numero negativo
            throw new IllegalArgumentException();
        //continuo con un algoritmo simile a quello della somma
        LinkedList<Integer> numeroS=new LinkedList<>();//in numeroS avrò il big int di s che non ha una struttura dati specifica
        LinkedList<Integer> tmp=new LinkedList<>();//in tmp avrò il risultato delle sottrazioni
        for(int i=0;i<s.value().length();i++)
            numeroS.add(Integer.parseInt(s.value().charAt(i)+""));
        ListIterator<Integer> thisIterator=numero.listIterator(numero.size());
        ListIterator<Integer> sIterator=numeroS.listIterator(numeroS.size());
        //scorrendo le due liste a ritroso eseguo le sottrazione e nel caso la sottrazione abbia due numeri che danno
        //un risultato negativo usando il riporto impostato a -1
        int riporto=0;
        while(thisIterator.hasPrevious() && sIterator.hasPrevious()){
            int cifraThis=thisIterator.previous();
            int cifraS=sIterator.previous();
            if((cifraThis+riporto)<cifraS) {
                riporto = -1;
                tmp.add(cifraThis-cifraS+10);
            }
            else {
                tmp.add(cifraThis + riporto - cifraS);
                riporto=0;
            }
        }
        while(thisIterator.hasPrevious()) {
            int cifraDaInserire=thisIterator.previous()+riporto;
            if(cifraDaInserire!=0)
                tmp.add(cifraDaInserire);
            riporto=0;
        }
        LinkedList<Integer> lr=new LinkedList<>();
        ListIterator<Integer> tmpIterator=tmp.listIterator(tmp.size());
        //avendo anche in questo caso tmp con i risultati a contario lo metto a posto in lr che verrà assegnato a r che verrà restituito
        while(tmpIterator.hasPrevious())
            lr.add(tmpIterator.previous());
        BigIntLL r=new BigIntLL();
        r.numero=lr;
        return r;
    }//sub

    @Override
    public Iterator<Integer> iterator() {
        return numero.listIterator();
    }//iterator
}//BigIntLL
