package com.panopset.demo.framing;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	@RequestMapping(method=RequestMethod.GET)
	public String handleRequest(ModelAndView mav, HttpServletRequest request) {
		mav.getModel().put("serverInfo", request.getSession()
				.getServletContext().getServerInfo());
		mav.addObject("storeList", storeDAO.getAllStores());
		return "index";
	}
}
