package pl.wroc.pwr.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 10.11.13
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@ToString
public abstract class JsonResponse<T> {

    private String status;
    private T Data;

    protected JsonResponse(String status, T data) {
        this.status = status;
        Data = data;
    }
}
