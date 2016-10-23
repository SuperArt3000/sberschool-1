package ru.sbt.Lesson7_proxy;


public class Test {
    public static void main(String[] args) {
        AImpl AImpl_test = new AImpl();
        ABImpl abTest = new ABImpl();
        BCImpl bcTest = new BCImpl();
        try{
            CachProxy CProxyImpl = new CachProxy(Test.class);

            A a = (A) CProxyImpl.newCashProxy(AImpl_test);
            C c = (C) CProxyImpl.newCashProxy(bcTest);
            A a2 = (A) CProxyImpl.newCashProxy(abTest);

            System.out.println(a.out(3));
            System.out.println(a.out(5));
            System.out.println(a2.out(5));

            System.out.println(c.cashedsum(5, 6));
            System.out.println(c.cashedsum(5, 6));


            B B_proxy = (B) CProxyImpl.newCashProxy(abTest);
            System.out.println(B_proxy.cashedsum(5, 6));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
