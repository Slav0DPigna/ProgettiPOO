package bigInt;

public interface BigInt extends Comparable<BigInt>,Iterable<Integer>{

    default String value() {//ritorna il valore del BigInt sottoforma di stringa di caratteri
        StringBuilder sb=new StringBuilder();
        for(Integer x:this)
            sb.append(x);
        return sb.toString();
    }//value

    default int length() { //ritorna il numero di cifre di questo BigInt
        return this.value().length();
    }//lenght

    BigInt factory( int x );

    default BigInt incr(){
        return factory(1).add(this);
    }//incr

    default BigInt decr() {//eccezione se this è zero
        if(this.length()==0)
            throw new IllegalArgumentException();
        return this.sub(factory(1));
    }
    BigInt add( BigInt a );
    BigInt sub( BigInt s );//ritorna un BigInt con la differenza tra this e d; atteso this>=d

    default BigInt mul( BigInt m ){
        //per implementare la mul ho usato semplicemente una successione di somme
        BigInt r=factory(0);
        int valoreMoltiplicativo=Integer.parseInt(m.value());
        for(int i=0;i<valoreMoltiplicativo;i++){
            r=r.add(this);
        }
        return r;
    }//mul

    default BigInt div( BigInt d ) {//ritorna il quoziente della divisione intera tra this e d; atteso this>=d
        //per implementare la div ho usato una successione di sottrazioni che se non davano un risultato negativo incrementavano
        //un contatore che poi veniva restituito
        if(d.compareTo(this)>0)
            throw new IllegalArgumentException();
        BigInt r=factory(0);
        BigInt dividendo=factory(Integer.parseInt(this.value()));
        while(dividendo.compareTo(d)>=0){
            dividendo=dividendo.sub(d);
            r=r.incr();
        }
        return r;
    }//div

    default BigInt rem( BigInt d ) {//ritorna il resto della divisione intera tra this e d; atteso this>=d
        //per i resto vale la stessa logica della div con la differenza che in questo caso veniva resitutito
        //il dividendo per sopo la successione di somme
        if(d.compareTo(this)>0)
            throw new IllegalArgumentException();
        BigInt dividendo=factory(Integer.parseInt(this.value()));
        while(dividendo.compareTo(d)>=0)
            dividendo=dividendo.sub(d);
        return dividendo;
    }//rem

    default BigInt pow(int exponent ) { //calcola la potenza this^exponent
        BigInt r=factory(1);
        for(int i=0;i<exponent;i++)
            r=r.mul(this);
        return r;
    }//pow
}//BigInt