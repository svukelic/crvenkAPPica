package hr.foi.air.crvenkappica.calendar;

public class MoonLinks {

    public static class MoonIcons{
        private static String link = "http://www.redtesseract.sexy/crvenkappica/moon/";

        public static String getLink(String phase){

            return link + phase + ".png";
        }

    }

}
