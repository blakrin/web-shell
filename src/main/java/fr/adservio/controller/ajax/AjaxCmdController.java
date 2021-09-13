package fr.adservio.controller.ajax;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fr.adservio.service.CmdService;

@Controller
public class AjaxCmdController {

	private final CmdService cmdService;
	
	
	public AjaxCmdController(CmdService cmdService) {
		super();
		this.cmdService = cmdService;
	}


	@PostMapping("/run-cmd")
	public ResponseEntity<String> ajaxRunCmd(@RequestBody String command) {
		String result = cmdService.runCmd(command);
		
		if (Objects.nonNull(result)) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
