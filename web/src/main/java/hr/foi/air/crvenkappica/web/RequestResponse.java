package hr.foi.air.crvenkappica.web;

public class RequestResponse {
    public static class StaticResponse{

        public static String finalResponse;

        public static String getFinalResponse() {
            return finalResponse;
        }

        public static void setFinalResponse(String finalResponse) {
            StaticResponse.finalResponse = finalResponse;
        }
    }
}
