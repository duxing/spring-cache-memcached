package scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scm.Application;
import scm.model.Data;
import scm.model.Ping;
import scm.processor.APIProcessor;

@RestController
@RequestMapping(value="/" + Application.NAME)
public class RestAPIController {
    private static final Logger LOG = LoggerFactory.getLogger(RestAPIController.class);

    @Autowired
    private APIProcessor processor;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        return "";
    }

    @RequestMapping(value="/ping", method=RequestMethod.GET)
    @ResponseBody
    public Ping ping() {
        LOG.info("ping");
        return Ping.get();
    }

    @RequestMapping(value="/data/{dataId}", method=RequestMethod.GET)
    @ResponseBody
    public Data getData(@PathVariable String dataId) {
        LOG.info("method=\"getData\" dataId=\"{}\"", dataId);
        return processor.getData(dataId);
    }
}
