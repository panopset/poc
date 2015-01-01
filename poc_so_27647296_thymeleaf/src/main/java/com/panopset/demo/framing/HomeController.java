package com.panopset.demo.framing;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.panopset.demo.bean.Store;
import com.panopset.demo.dao.StoreDAO;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    StoreDAO storeDAO;

    /**
     * Handle http request.
     *
     * @param model
     *            Model.
     * @param request
     *            Request.
     * @return index.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpServletRequest request) {
        model.addAttribute("serverInfo", request.getSession()
                .getServletContext().getServerInfo());
        List<Store> stores = storeDAO.getAllStores();
        if (logger.isDebugEnabled()) {
            for (Store s : stores) {
                logger.debug(s.toString());
            }
        }
        model.addAttribute("storeList", stores);
        return "index";
    }

    private final Logger logger = LoggerFactory.getILoggerFactory().getLogger(
            this.getClass().getName());
}
