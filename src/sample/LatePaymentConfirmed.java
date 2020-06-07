package sample;

public class LatePaymentConfirmed {

    //Login Storage is used for storing the users login details after they log in.

    //Strings for storing.
    public static boolean confimed;
    public static String holder;
    public static String holder2;
    public static String holder3;
    public static String holder4;

    public LatePaymentConfirmed() {
    }

    public static boolean isConfimed() {
        return confimed;
    }

    public static void setConfimed(boolean confimed) {
        LatePaymentConfirmed.confimed = confimed;
    }

    public static String getHolder() {
        return holder;
    }

    public static void setHolder(String holder) {
        LatePaymentConfirmed.holder = holder;
    }

    public static String getHolder2() {
        return holder2;
    }

    public static void setHolder2(String holder2) {
        LatePaymentConfirmed.holder2 = holder2;
    }

    public static String getHolder3() {
        return holder3;
    }

    public static void setHolder3(String holder3) {
        LatePaymentConfirmed.holder3 = holder3;
    }

    public static String getHolder4() {
        return holder4;
    }

    public static void setHolder4(String holder4) {
        LatePaymentConfirmed.holder4 = holder4;
    }
}
