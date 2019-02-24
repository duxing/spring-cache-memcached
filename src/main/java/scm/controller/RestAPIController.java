package scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import scm.Application;

@RestController
@RequestMapping(value="/" + Application.NAME)
public class RestAPIController {
    private static final Logger LOG = LoggerFactory.getLogger(RestAPIController.class);

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        return "";
    }

    @RequestMapping(value="/ping", method=RequestMethod.GET)
    public String ping() {
        LOG.debug("ping");
        return "pong";
    }
}
