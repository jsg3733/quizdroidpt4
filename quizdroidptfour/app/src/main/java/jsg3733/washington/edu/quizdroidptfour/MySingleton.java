package jsg3733.washington.edu.quizdroidptfour;

/**
 * Created by jsg3733 on 2/16/2015.
 */
public class MySingleton {
    private static MySingleton instance;

    //public String customVar;

    public static void initInstance(){
        if(instance == null){
            instance = new MySingleton();
        }
    }

    public static MySingleton getInstance(){
        return instance;
    }

    private MySingleton(){

    }

    /*public void customSingletonMethod(){
        //Custom singleton method
    }*/


}
