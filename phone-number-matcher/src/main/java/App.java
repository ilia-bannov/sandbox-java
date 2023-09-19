import sandbox.algorithm.matcher.PhoneNumberMatcher;
import sandbox.algorithm.matcher.impl.PhoneNumberMatcherImpl;

public class App {

    public static void main(String[] args) {
        PhoneNumberMatcher phoneNumberMatcher = new PhoneNumberMatcherImpl();
        System.out.println(phoneNumberMatcher.matches("AAAABBBB", "11112222"));
        System.out.println(phoneNumberMatcher.matches("XY123456", "98123456"));
        System.out.println(phoneNumberMatcher.matches("XYAAABBZ", "98111116"));
        System.out.println(phoneNumberMatcher.matches("ABCDABCD", "12891289"));
        System.out.println(phoneNumberMatcher.matches("AAA00BBB", "11100999"));
    }
}
