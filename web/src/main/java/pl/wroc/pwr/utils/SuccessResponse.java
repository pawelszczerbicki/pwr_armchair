package pl.wroc.pwr.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 10.11.13
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class SuccessResponse<T> extends JsonResponse<T>{

    public static final String SUCCESS = "success";

    private SuccessResponse(T data) {
        super(SUCCESS, data);
    }

    public static <T> SuccessResponse<T> create (T data){
        return new SuccessResponse<>(data);
    }

    public static <T> SuccessResponse<T> create (){
        return new SuccessResponse<>(null);
    }
}
