package pl.wroc.pwr.armchair.ws;

import org.apache.commons.io.FileUtils;
import pl.wroc.pwr.armchair.element.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 18.01.14.
 */
public class SettingsParser {

    private final Integer CODE = 0;
    private final Integer PORT = 1;
    private final Integer FORWARD = 2;
    private final Integer BACKWARD = 3;
    private final Integer COUNTER = 4;
    private final Integer MAX_POS = 5;

    private final String SEPARATOR = ";";
    private final Integer FIRST_DATA_ROW = 1;
    private final String DEFAULT_SETTINGS_FILE = "properties.csv";

    public List<Element> getElements(String csv) {
        List<Element> elements = new ArrayList<>();
        String[] lines = csv.split("\n");
        for (int i = FIRST_DATA_ROW; i < lines.length; i++) {
            String[] e = lines[i].split(SEPARATOR);
            elements.add(new Element(e[CODE], Integer.parseInt(e[PORT]), Integer.parseInt(e[FORWARD]), Integer.parseInt(e[BACKWARD]), Integer.parseInt(e[COUNTER]), Integer.parseInt(e[MAX_POS].trim())));
        }
        return elements;
    }
    public List<Element> getDefaultElements() {
        try {
            return getElements(FileUtils.readFileToString(new File(DEFAULT_SETTINGS_FILE)));
        } catch (IOException e) {

            return null;
        }
    }

}
