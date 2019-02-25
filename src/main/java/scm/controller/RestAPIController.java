package scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scm.Application;
import scm.model.Data;
import scm.model.Ping;
import scm.processor.APIProcessor;

import java.util.Collection;

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

    @RequestMapping(value="/data", method=RequestMethod.POST)
    @ResponseBody
    public Data createData() {
        LOG.info("method=\"createData\"");
        return processor.create();
    }

    @RequestMapping(value="/data/{dataId}", method=RequestMethod.GET)
    @ResponseBody
    public Data getData(@PathVariable String dataId) {
        LOG.info("method=\"get\" dataId=\"{}\"", dataId);
        return processor.get(dataId);
    }

    @RequestMapping(value="/data", method=RequestMethod.GET)
    @ResponseBody
    public Collection<Data> getAllData() {
        LOG.info("method=\"getAllData\"");
        return processor.getAll();
    }

    @RequestMapping(value="/data/{dataId}", method=RequestMethod.PUT)
    @ResponseBody
    public Data updateData(@PathVariable String dataId, @RequestBody Data data) {
        LOG.info("method=\"update\" dataId=\"{}\"", dataId);
        return processor.update(dataId, data);
    }
}
