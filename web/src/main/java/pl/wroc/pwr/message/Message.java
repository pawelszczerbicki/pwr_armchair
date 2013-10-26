package pl.wroc.pwr.message;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 24.10.13
 * Time: 22:38
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@XmlRootElement
public class Message {

    private String id;

    private String content;
}
