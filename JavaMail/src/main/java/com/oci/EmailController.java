package com.oci;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class EmailController {
	
	@GetMapping(value = "/emailWrite.do")
	public String openWrite(Model model) {		
		return "email_write.html";
	}
	
	@PostMapping(value = "/emailSend.do")
	public String emailSend(EmailDTO emailDTO) {
		emailDTO.setBody((emailDTO.getBody()).replaceAll("\n", "<br/>"));
		
		OCIEmailSend emailSend = new OCIEmailSend();
		String emailSendRtn = emailSend.send(emailDTO);
		if("S".equals(emailSendRtn))
			System.out.println("성공!!!!!!!!!!!!");
		else
			System.out.println("실패!!!!!!!!!!!!");
		
		return "redirect:/emailWrite.do";
	}
}
