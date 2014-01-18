package pl.wroc.pwr;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.apache.log4j.Logger.getLogger;

@Controller
@RequestMapping("/")
public class IndexController {

    private final Logger logger = getLogger(getClass());

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping("config")
    public String config() {
        return "config";
    }
}