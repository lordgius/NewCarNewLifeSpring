package pe.edu.upc.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entities.Distrito;
import pe.edu.upc.serviceInterface.IDistritoService;
import pe.edu.upc.serviceInterface.IProvinciaService;

@Controller
@RequestMapping("/distrito")
public class DistritoController {

	@Autowired
	private IDistritoService dService;
	@Autowired
	private IProvinciaService pService;

	@GetMapping("/new")
	public String newDistrito(Model model) {
		model.addAttribute("listaProvincia", pService.list());
		model.addAttribute("distrito", new Distrito());
		return "distrito/distrito";
	}

	@GetMapping("/list")
	public String listDistrito(Model model) {
		try {
			model.addAttribute("distrito", new Distrito());
			model.addAttribute("listaDistrito", dService.list());
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("error", e.getMessage());
		}
		return "distrito/listaDistrito";
	}

	@PostMapping("/save")
	public String insertDistrito(@Valid Distrito distrito, BindingResult binRes, Model model, SessionStatus status)
			throws Exception {
		if (binRes.hasErrors()) {
			model.addAttribute("listaDistrito", dService.list());
			model.addAttribute("listaProvincia", pService.list());
			return "distrito/distrito";
		} else {
			int rpta = dService.insert(distrito);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "distrito/distrito";
			} else {
				model.addAttribute("mensaje", "Se guardo correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("distrito", new Distrito());
		return "redirect:/distrito/list";
	}

	@RequestMapping("/delete")
	public String deleteDistrito(Model model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				dService.delete(id);
				model.addAttribute("mensaje", "Se eliminó correcto");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al eliminar distrito");
		}
		return "redirect:/distrito/list";
	}

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Distrito dis) {
		dService.listarId(dis.getIdDistrito());
		return "distrito/listaDistrito";
	}

	@RequestMapping("/update/{id}")
	public String update(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Distrito> objDis = dService.listarId(id);
		if (objDis == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrirá un error");
			return "distrito/distrito";

		} else {
			model.addAttribute("distrito", objDis);
			return "distrito/distrito";
		}

	}
}
