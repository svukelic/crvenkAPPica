package hr.foi.air.crvenkappica;

public class LoginStatus {

    public static class LoginInfo{

        public static String loginName;
        public static Boolean loginState;


        public static String getLoginName() {
            return loginName;
        }

        public static void setLoginName(String loginName) {
            LoginInfo.loginName = loginName;
        }

        public static Boolean getLoginState() {
            return loginState;
        }

        public static void setLoginState(Boolean loginState) {
            LoginInfo.loginState = loginState;
        }
    }

}
