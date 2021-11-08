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

import pe.edu.upc.entities.TallerMecanico;
import pe.edu.upc.serviceInterface.IDistritoService;
import pe.edu.upc.serviceInterface.ITallerMecanicoService;

@Controller
@RequestMapping("/taller")
public class TallerMecanicoController {

	@Autowired
	private ITallerMecanicoService tService;
	@Autowired
	private IDistritoService dService;

	@GetMapping("/new")
	public String newTaller(Model model) {
		model.addAttribute("listaDistrito", dService.list());
		model.addAttribute("taller", new TallerMecanico());
		return "taller/taller";
	}

	@GetMapping("/list")
	public String listTaller(Model model) {
		try {
			model.addAttribute("taller", new TallerMecanico());
			model.addAttribute("listaTaller", tService.list());
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("error", e.getMessage());
		}
		return "taller/listaTaller";
	}

	@PostMapping("/save")
	public String insertTaller(@Valid TallerMecanico taller, BindingResult binRes, Model model, SessionStatus status)
			throws Exception {
		if (binRes.hasErrors()) {
			model.addAttribute("listaDistrito", dService.list());
			model.addAttribute("listaTaller", tService.list());
			return "taller/taller";
		} else {
			int rpta = tService.insert(taller);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe");
				return "taller/taller";
			} else {
				model.addAttribute("mensaje", "Se guardo correctamente");
				status.setComplete();
			}
		}
		model.addAttribute("taller", new TallerMecanico());
		return "redirect:/taller/list";
	}

	@RequestMapping("/delete")
	public String deleteTaller(Model model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				tService.delete(id);
				model.addAttribute("mensaje", "Se eliminó correcto");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al eliminar distrito");
		}
		return "redirect:/taller/list";
	}

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute TallerMecanico taller) {
		tService.listId(taller.getIdTallerMecanico());
		return "taller/listaTaller";
	}

	@RequestMapping("/update/{id}")
	public String update(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<TallerMecanico> objTaller = tService.listId(id);
		if (objTaller == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrirá un error");
			return "taller/taller";

		} else {
			model.addAttribute("taller", objTaller);
			return "taller/taller";
		}

	}

}
