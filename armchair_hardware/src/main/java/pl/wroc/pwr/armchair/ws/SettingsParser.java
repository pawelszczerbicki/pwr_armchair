package pl.wroc.pwr.armchair.ws;

import org.apache.commons.lang.ArrayUtils;
import pl.wroc.pwr.armchair.element.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 18.01.14.
 */
public class SettingsParser {

    public List<Element> getElements(String csv){
        List<Element> elements = new ArrayList<>();
        String [] lines = csv.split("\n");
        ArrayUtils.remove(lines, 0);
        for(String line: lines){
           elements.
        }
    }
}
