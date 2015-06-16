package org.bgu.service.Exceptions;

/**
 * Created by hodai on 6/16/15.
 */
public class RetObj<T> {

    public final Result _result;
    public final T _value;

    public RetObj(Result result, T value){
        _result = result;
        _value = value;
    }

    public RetObj(Result result){
        _result = result;
        _value = null;
    }

    public boolean compareResult(Result result){
        if(result != _result){
            System.err.println("expected result:  " + _result + " but was: " + result);
            return false;
        }
        return true;
    }
}
