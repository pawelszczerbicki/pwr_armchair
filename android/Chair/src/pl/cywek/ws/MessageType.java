package pl.cywek.ws;

/**
 * Created by Pawel on 14.01.14.
 */
public enum MessageType {
    //ACTION - action performed on device  - clients should ignore it
    //CONFIG - configuration for device, clients should ignore it
    //RESPONSE - message from device to clients - client should service it
    //LOG - logs from device, clients could show it for user.
    //HEARTBEAT  - to keep connection alive - everyone should ignore it
    ACTION , CONFIG, RESPONSE, LOG, HEARTBEAT, CALIBRATE;
}
