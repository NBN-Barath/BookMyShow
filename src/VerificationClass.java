public class VerificationClass {
    public static int otpGenerator(){
        return (int) (1000 +(Math.random() * ((9999 - 1000) + 1)));
    }
}
