package pl.wroc.pwr.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 10.11.13
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class FailResponse<T> extends JsonResponse<T>{
    public static final String FAIL = "fail";

    private FailResponse(T data) {
        super(FAIL, data);
    }

    public static <T> FailResponse<T> create (T data){
        return new FailResponse<T>(data);
    }

    public static <T> FailResponse<T> create (){
        return new FailResponse<T>(null);
    }

}
