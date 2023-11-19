package org.AnaliseSocioEconomica.Controller;

import org.AnaliseSocioEconomica.DAO.DAO;
import org.AnaliseSocioEconomica.DAO.DAOFactory;
import org.AnaliseSocioEconomica.DAO.Dados.DadosDAO;
import org.AnaliseSocioEconomica.Model.Dados;
import org.AnaliseSocioEconomica.Model.Pais;
import org.AnaliseSocioEconomica.Model.SerieAnoAtrib;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class DadosController {

    DAO<Pais> daoPais;
    DAO<Dados> daoDados;

    @GetMapping("/consulta-atrib/{atrib}/{sigla}")
    public String consultaAtrib(@PathVariable("atrib") String atrib,
                                @PathVariable("sigla") String sigla, Model model) {

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoDados = daoFactory.getDadosDAO();

            Dados dados = ((DadosDAO) daoDados).readBySigla(atrib, sigla);
//            for (Map.Entry<Integer, String> entry : dados.getSeries().getDuplaAnoAtributo().entrySet()) {
//                System.out.println("ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
//            }

            model.addAttribute("dados", dados);
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "atrib-table";
    }

    @GetMapping("/remover-dados/{id}/{indi}/{ano}")
    public String removerUmDadoDeAno(@PathVariable("id") String id, @PathVariable("indi") String indi,
                                      @PathVariable("ano") int ano, Model model) {

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoDados = daoFactory.getDadosDAO();
            // faltando acabar
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/form-editar-atrib/{id}/{indi}/{ano}")
    public String mostrarFormEditarDados(@PathVariable("id") String id, @PathVariable("indi") String indi,
                                         @PathVariable("ano") int ano, Model model) {

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoDados = daoFactory.getDadosDAO();

            SerieAnoAtrib serieAnoAtrib = ((DadosDAO) daoDados).readUniqueAno(indi, id, ano);
            String valor = null;
            for (Map.Entry<Integer, String> entry : serieAnoAtrib.getDuplaAnoAtributo().entrySet()) {
                valor = entry.getValue();
                System.out.println("TESTEEE " + indi + " ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }
            model.addAttribute("idPais", id);
            model.addAttribute("indi", indi);
            model.addAttribute("ano", ano);
            model.addAttribute("valorSerieAnoAtrib", valor);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "form-edicao-dados";
    }

    @PostMapping("/update-dados/{id}/{indi}/{ano}")
    public String updateDados(@PathVariable("id") String id, @PathVariable("indi") String indi,
                              @PathVariable("ano") int ano, @RequestParam("valor") String valorSerieAnoAtrib,
                              Model model) {

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoDados = daoFactory.getDadosDAO();

            SerieAnoAtrib serieAnoAtrib = new SerieAnoAtrib();
            serieAnoAtrib.setDuplaAnoAtributo(ano, valorSerieAnoAtrib);
            Dados d = new Dados();
            d.setSigla(id);
            d.setIndicador(indi);
            d.setSeries(serieAnoAtrib);
            for (Map.Entry<Integer, String> entry : d.getSeries().getDuplaAnoAtributo().entrySet()) {
                System.out.println("TESTEEE2222 " + d.getIndicador() + " ANO: " + entry.getKey() + " VALOR: " + entry.getValue());
            }
            daoDados.update(d);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "redirect:/";
//        return "redirect:/consulta-atrib/{" + indi + "}/{" + id + "}";
    }
}
