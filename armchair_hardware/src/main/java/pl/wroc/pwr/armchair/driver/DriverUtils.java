package pl.wroc.pwr.armchair.driver;

import Automation.BDaq.ErrorCode;

/**
 * Created by Pawel on 02.01.14.
 */
public class DriverUtils {

    public static boolean hasError(ErrorCode errorCode){
        return (errorCode.compareTo(ErrorCode.ErrorHandleNotValid) >= 0);
    }
}
