package org.AnaliseSocioEconomica.Controller;

import org.AnaliseSocioEconomica.DAO.DAO;
import org.AnaliseSocioEconomica.DAO.DAOFactory;
import org.AnaliseSocioEconomica.DAO.Dados.DadosDAO;
import org.AnaliseSocioEconomica.Model.Dados;
import org.AnaliseSocioEconomica.Model.Pais;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PaisController {
    DAO<Pais> daoPais;
    DAO<Dados> daoDados;

    @GetMapping("/teste")
    public String htmlCru(Model model) {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoPais = daoFactory.getPaisDAO();
            List<Pais> meusPaises = daoPais.all();

            model.addAttribute("paises", meusPaises);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "index";
    }

    @GetMapping(value = {"/", "/index"})
    public String mainInterface(Model model) {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoPais = daoFactory.getPaisDAO();
            List<Pais> meusPaises = daoPais.all();

            model.addAttribute("paises", meusPaises);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "main";
    }

    @GetMapping("/remover-pais/{id}")
    public String removerPais(@PathVariable("id") String id, Model model) {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoPais = daoFactory.getPaisDAO();

            daoPais.delete(id);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/form-editar-pais/{id}")
    public String mostrarFormEditarPais(@PathVariable("id") String id, Model model) {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoPais = daoFactory.getPaisDAO();

            Pais p = daoPais.read(id);
            model.addAttribute("pais", p);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "form-edicao-pais";
    }

    @PostMapping("/update-pais/{id}")
    public String updatePais(@PathVariable("id") String id, Pais pais, Model model) {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoPais = daoFactory.getPaisDAO();

            pais.setId(id);
            daoPais.update(pais);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/";
    }

    public Pais readPais(String id) {
        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoPais = daoFactory.getPaisDAO();

            Pais p = daoPais.read(id);
            return p;

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            System.out.println("Exception in readPais");
        }
        return null;
    }

    @GetMapping("/getNomesAtributos/{paisId}")
    public ResponseEntity<Map<String, List<String>>> getDados(@PathVariable("paisId") String paisId) {
        Pais p = readPais(paisId);

        List<String> dados = new ArrayList<>();
        dados.add(p.getPibTotal().getIndicador());
        dados.add(p.getPibPerCapita().getIndicador());
        dados.add(p.getTotalExportacao().getIndicador());
        dados.add(p.getTotalImportacao().getIndicador());
        dados.add(p.getInvestPesqDesenv().getIndicador());
        dados.add(p.getIndivAcesNet().getIndicador());
        dados.add(p.getIdh().getIndicador());
        dados.add(p.getImpComInter().getIndicador());
        dados.add(p.getImpExportacao().getIndicador());
        dados.add(p.getImpReceitaFiscal().getIndicador());
        dados.add(p.getImpAlfanImport().getIndicador());
        dados.add(p.getImpRenda().getIndicador());

        for (String s : dados) {
            System.out.println(s);
        }
        Map<String, List<String>> response = new HashMap<>();
        response.put("dados", dados);
        return ResponseEntity.ok(response);
    }
}
