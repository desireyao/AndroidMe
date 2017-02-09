package com.example.question;

/**
 * Package com.example.question.
 * Created by yaoh on 2016/11/08.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class Ques1014 {

    public static void main(String[] args) {
        new Obj();
    }
}

class Obj {

    Other other = new Other();

    public Obj() {
        this("name");
        System.out.println("===obj===");
    }

    public Obj(String name) {
       System.out.println("---obj---");
    }
}

class Other{

    public Other(){
        System.out.println("---other---");
    }
}




