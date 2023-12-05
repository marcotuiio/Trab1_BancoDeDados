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
import org.springframework.web.bind.annotation.RequestBody;

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

    private static List<String> atribs = List.of("idh", "imp_alfan_import", "imp_com_inter", "imp_receita_fiscal",
            "imp_exportacao", "imp_renda", "indiv_aces_net", "pib_total", "pib_per_capita", "invest_pesq_desenv",
            "total_exportacao", "total_importacao");

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
            model.addAttribute("atribs", atribs);

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

    public Dados readDados(String indi, String sigla) {

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoDados = daoFactory.getDadosDAO();

            Dados dados = ((DadosDAO) daoDados).readBySigla(indi, sigla);
            return dados;

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            System.out.println("Exception in readDados");
        }

        return null;
    }

    @PostMapping("/teste-ajax")
    public ResponseEntity<?> montarPais(@RequestBody Map<String, String> formData) {
//        System.out.println("ENTRANDO NA FUNCAO DA REQUISICAO AJAX");
        String paisId = formData.get("paisId");
        String atrib = formData.get("atrib");
        String anoInicio = formData.get("anoInicio");
        String anoFim = formData.get("anoFim");

        Pais p = readPais(paisId);
        Dados d = readDados(atrib, paisId);

        switch (atrib) {
            case "idh":
                p.setIdh(d);
                break;
            case "imp_alfan_import":
                p.setImpAlfanImport(d);
                break;
            case "imp_com_inter":
                p.setImpComInter(d);
                break;
            case "imp_receita_fiscal":
                p.setImpReceitaFiscal(d);
                break;
            case "imp_exportacao":
                p.setImpExportacao(d);
                break;
            case "imp_renda":
                p.setImpRenda(d);
                break;
            case "indiv_aces_net":
                p.setIndivAcesNet(d);
                break;
            case "pib_total":
                p.setPibTotal(d);
                break;
            case "pib_per_capita":
                p.setPibPerCapita(d);
                break;
            case "invest_pesq_desenv":
                p.setInvestPesqDesenv(d);
                break;
            case "total_exportacao":
                p.setTotalExportacao(d);
                break;
            case "total_importacao":
                p.setTotalImportacao(d);
                break;
            default:
                break;
        }
        Map<String, Object> paisDados = new HashMap<>();
        paisDados.put("pais", p);
        paisDados.put("dados", d);
//        System.out.println("SAINDO DA FUNCAO DA REQUISICAO AJAX" + p.getNome() + d.getIndicador());
        return ResponseEntity.ok(paisDados);
    }


}
