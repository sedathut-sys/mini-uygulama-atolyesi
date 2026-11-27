package com.akillidevam.app;
public class DecisionTest {
 static void check(Decision.Action a,Decision.Action b){if(a!=b)throw new AssertionError(a+" != "+b);}
 public static void main(String[] args){
 Decision d=new Decision(2,20000,true);
 check(d.evaluate(0,true,false,true,false),Decision.Action.WAIT);
 check(d.evaluate(1000,false,false,true,false),Decision.Action.WAIT);
 check(d.evaluate(20999,false,false,true,false),Decision.Action.WAIT);
 check(d.evaluate(21000,false,false,true,false),Decision.Action.WRITE);
 d.accepted(21000);
 check(d.evaluate(22000,false,false,true,false),Decision.Action.WAIT);
 check(d.evaluate(81000,false,false,true,false),Decision.Action.STOP);
 check(d.evaluate(30000,true,false,true,false),Decision.Action.WAIT);
 check(d.evaluate(31000,false,true,true,false),Decision.Action.WAIT);
 check(d.evaluate(51000,false,true,true,false),Decision.Action.CLICK);
 d.accepted(51000);
 check(d.evaluate(52000,true,false,true,false),Decision.Action.STOP);
 Decision e=new Decision(10,10000,false);
 check(e.evaluate(0,false,false,true,false),Decision.Action.WAIT);
 check(e.evaluate(10000,false,false,true,false),Decision.Action.WAIT);
 check(e.evaluate(11000,false,true,false,false),Decision.Action.WAIT);
 check(e.evaluate(12000,false,true,true,true),Decision.Action.STOP);
 Decision f=new Decision(10,10000,true);
 check(f.evaluate(0,false,false,true,false),Decision.Action.WAIT);
 check(f.evaluate(10000,false,false,true,false),Decision.Action.WAIT);
 System.out.println("16 kontrol başarılı");
 }
}
