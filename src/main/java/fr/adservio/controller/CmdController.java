package fr.adservio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.adservio.model.Connection;
import fr.adservio.service.CmdService;

@Controller
@RequestMapping("/")
public class CmdController {
	private final Logger LOGGER = LoggerFactory.getLogger(CmdController.class);
	private final CmdService cmdService;

	public CmdController(CmdService cmdService) {
		super();
		this.cmdService = cmdService;
	}

	@GetMapping()
	public String homePage(Model model) {
		model.addAttribute("connect", new Connection());
		return "home";

	}

	@PostMapping("connect")
	public String home(@ModelAttribute Connection connect, Model model) {
		cmdService.SSHManager(connect.getUsername(), connect.getPassword(), connect.getServerIp(),connect.getPort());
		if (cmdService.connect()) {
			connect.setStatus(true);
		}
		model.addAttribute("connect", connect);
		return "home";
	}
}
