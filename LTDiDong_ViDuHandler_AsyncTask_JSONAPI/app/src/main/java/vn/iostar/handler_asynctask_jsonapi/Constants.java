package vn.iostar.handler_asynctask_jsonapi;

public class Constants {
    public static String localhost = "app.iotstar.vn";
    //login và register API
    private static final String ROOT_URL = "http://" + localhost +
            "/shoppingapp/registrationapi.php?apicall=";
    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
}
