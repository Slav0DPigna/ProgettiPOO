package bigInt;

import java.util.Iterator;

public abstract class AbstractBigInt implements BigInt{

    public String toString(){
        StringBuilder sb=new StringBuilder();
        for(Integer x:this)
            sb.append(x);
        return sb.toString();
    }//toString

    public int hashCode(){
        int r=13;
        final int M=83;
        for(Integer x:this)
            r=r*M+x.hashCode();
        return r;
    }//hashCode

    public boolean equals(Object o){
        if(o==this)
            return true;
        if(!(o instanceof BigInt))
            return false;
        BigInt b=(BigInt) o;
        if(this.length()!=b.length())
            return false;
        Iterator<Integer> it=this.iterator();
        Iterator<Integer> it1=b.iterator();
        while(it.hasNext())
            if(!(it.next().equals(it1.next())))
                return false;
        return true;
    }//equals
}//AbstractBigInt
