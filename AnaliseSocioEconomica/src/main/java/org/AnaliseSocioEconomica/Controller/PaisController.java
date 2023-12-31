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
import java.util.Objects;

@Controller
public class PaisController {
    DAO<Pais> daoPais;
    DAO<Dados> daoDados;

    private static List<String> atribs = List.of("idh", "imp_alfan_import", "imp_com_inter", "imp_receita_fiscal",
            "imp_exportacao", "imp_renda", "indiv_aces_net", "pib_total", "pib_per_capita", "invest_pesq_desenv",
            "total_exportacao", "total_importacao");

    private static List<String> paises = List.of("AR", "AU", "BR", "CA", "CN", "DE", "FR", "GB", "ID",
            "IN", "IT", "JP", "KR", "MX", "RU", "SA", "TR", "US", "ZA");

    @GetMapping("/visualizar-dados")
    public String htmlCru(Model model) {

        try (DAOFactory daoFactory = DAOFactory.getInstance()) {
            daoPais = daoFactory.getPaisDAO();
            List<Pais> meusPaises = daoPais.all();

            model.addAttribute("paises", meusPaises);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "visualiza-dados";
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
        return "redirect:/visualizar-dados";
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

    @PostMapping("/monta-pais-ajax")
    public ResponseEntity<?> montarPais(@RequestBody Map<String, String> formData) {
//        System.out.println("ENTRANDO NA FUNCAO DA REQUISICAO AJAX");
        String paisAId = formData.get("paisAId");
        String atrib = formData.get("atrib");

        Pais p = readPais(paisAId);
        Dados d = readDados(atrib, paisAId);

        String paisBId = formData.get("paisBId");
        Pais p2 = null;
        Dados d2 = null;
        if (paisBId != null) {
            p2 = readPais(paisBId);
            d2 = readDados(atrib, paisBId);
//            System.out.println("Pais B da analise 2: " + p2.getNome() + " " + d2.getIndicador());
        }

        switch (atrib) {
            case "idh":
                p.setIdh(d);
                if (p2 != null) p2.setIdh(d2);
                break;
            case "imp_alfan_import":
                p.setImpAlfanImport(d);
                if (p2 != null) p2.setImpAlfanImport(d2);
                break;
            case "imp_com_inter":
                p.setImpComInter(d);
                if (p2 != null) p2.setImpComInter(d2);
                break;
            case "imp_receita_fiscal":
                p.setImpReceitaFiscal(d);
                if (p2 != null) p2.setImpReceitaFiscal(d2);
                break;
            case "imp_exportacao":
                p.setImpExportacao(d);
                if (p2 != null) p2.setImpExportacao(d2);
                break;
            case "imp_renda":
                p.setImpRenda(d);
                if (p2 != null) p2.setImpRenda(d2);
                break;
            case "indiv_aces_net":
                p.setIndivAcesNet(d);
                if (p2 != null) p2.setIndivAcesNet(d2);
                break;
            case "pib_total":
                p.setPibTotal(d);
                if (p2 != null) p2.setPibTotal(d2);
                break;
            case "pib_per_capita":
                p.setPibPerCapita(d);
                if (p2 != null) p2.setPibPerCapita(d2);
                break;
            case "invest_pesq_desenv":
                p.setInvestPesqDesenv(d);
                if (p2 != null) p2.setInvestPesqDesenv(d2);
                break;
            case "total_exportacao":
                p.setTotalExportacao(d);
                if (p2 != null) p2.setTotalExportacao(d2);
                break;
            case "total_importacao":
                p.setTotalImportacao(d);
                if (p2 != null) p2.setTotalImportacao(d2);
                break;
            default:
                break;
        }
        Map<String, Object> paisDados = new HashMap<>();
        paisDados.put("paisA", p);
        paisDados.put("dadosA", d);

        paisDados.put("paisB", p2);
        paisDados.put("dadosB", d2);
//        System.out.println("SAINDO DA FUNCAO DA REQUISICAO AJAX" + p.getNome() + d.getIndicador());
        return ResponseEntity.ok(paisDados);
    }

    @PostMapping("/monta-rank-analise3-ajax")
    public ResponseEntity<?> montarRanking(@RequestBody Map<String, String> formData) {
        List<Map<String, Object>> response = new ArrayList<>();
        List<Dados> todosIDHs = new ArrayList<>();

        for (String pais : paises) {
            todosIDHs.add(readDados("idh", pais));
        }

        for (int ano = 2010; ano <= 2021; ano++) {
            double maiorIdhPaisAno = 0;
            String siglaPais = null;
            for (Dados tI : todosIDHs) {
                double valorIdhPaisAno = Double.parseDouble(tI.getSeries().getDuplaAnoAtributo().get(ano));
                if (valorIdhPaisAno > maiorIdhPaisAno) {
                    maiorIdhPaisAno = valorIdhPaisAno;
                    siglaPais = tI.getSigla();
                }
            }

            Dados impImport = readDados("imp_alfan_import", siglaPais);
            double impImportPaisAno = Double.parseDouble(impImport.getSeries().getDuplaAnoAtributo().get(ano));

            Dados impReceitaFiscal = readDados("imp_receita_fiscal", siglaPais);
            double impReceitaFiscalPaisAno = Double.parseDouble(impReceitaFiscal.getSeries().getDuplaAnoAtributo().get(ano));

            double impImportDivReceita = 0;
            if (impReceitaFiscalPaisAno != 0) {
                impImportDivReceita = impImportPaisAno / impReceitaFiscalPaisAno;
            }

            Map<String, Object> result = new HashMap<>();
            result.put("nomePais", siglaPais);
            result.put("ano", ano);
            result.put("maiorIdhPaisAno", maiorIdhPaisAno);
            result.put("impImportDivReceita", impImportDivReceita * 100);
            response.add(result);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/monta-pais-analise3-ajax")
    public ResponseEntity<?> montarAnalise3(@RequestBody Map<String, String> formData) {
        List<Map<String, Object>> response = new ArrayList<>();
        String paisAId = formData.get("paisAId");

        Dados idhEspecifico = readDados("idh", paisAId);
        Map<String, Object> result = new HashMap<>();
        result.put("idhEspecifico", idhEspecifico);
        response.add(result);

        Dados impReceitaEspecifico = readDados("imp_receita_fiscal", paisAId);
        Dados impImportEspecifico = readDados("imp_alfan_import", paisAId);
        for (int ano = 2010; ano <= 2021; ano++) {
            double iRFE = Double.parseDouble(impReceitaEspecifico.getSeries().getDuplaAnoAtributo().get(ano));
            double iIE = Double.parseDouble(impImportEspecifico.getSeries().getDuplaAnoAtributo().get(ano));

            double percentual = 0;
            if (iRFE != 0) {
                percentual = iIE / iRFE;
            }
            Map<String, Object> resultadoDivisao = new HashMap<>();
            resultadoDivisao.put("percentualEspecifico", percentual);
            response.add(resultadoDivisao);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/monta-pais-analise6-ajax")
    public ResponseEntity<?> montarPaisAnalise6(@RequestBody Map<String, String> formData) {
//        System.out.println("ENTRANDO NA FUNCAO DA REQUISICAO AJAX");
        String paisAId = formData.get("paisAId");

        Pais p = readPais(paisAId);
        Dados idh = readDados("idh", paisAId);
        p.setIdh(idh);
        Dados net = readDados("indiv_aces_net", paisAId);
        p.setIndivAcesNet(net);
        Dados pesquisas = readDados("invest_pesq_desenv", paisAId);
        p.setInvestPesqDesenv(pesquisas);

        Map<String, Object> paisDados = new HashMap<>();
        paisDados.put("paisA", p);
        paisDados.put("idh", idh);
        paisDados.put("net", net);
        paisDados.put("pesquisas", pesquisas);

//        System.out.println("SAINDO DA FUNCAO DA REQUISICAO AJAX" + p.getNome() + d.getIndicador());
        return ResponseEntity.ok(paisDados);
    }

    @PostMapping("/monta-pais-analise4-ajax")
    public ResponseEntity<?> montarPaisAnalise4(@RequestBody Map<String, String> formData) {
        String paisAId = formData.get("paisAId");
        Pais pais = readPais(paisAId);

        Dados impImport = readDados("imp_alfan_import", paisAId);
        pais.setImpAlfanImport(impImport);
        Dados pibTotal = readDados("pib_total", paisAId);
        pais.setPibTotal(pibTotal);
        Dados impRenda = readDados("imp_renda", paisAId);
        pais.setImpRenda(impRenda);

        Map<String, Object> response = new HashMap<>();
        response.put("paisA", pais);
        response.put("impImport", impImport);
        response.put("pibTotal", pibTotal);
        response.put("impRenda", impRenda);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/monta-pais-analise5-ajax")
    public ResponseEntity<?> montarPaisAnalise5(@RequestBody Map<String, String> formData) {
        String paisAId = formData.get("paisAId");
        Pais pais = readPais(paisAId);

        Dados idh = readDados("idh", paisAId);
        pais.setIdh(idh);
        Dados impReceitaFiscal = readDados("imp_receita_fiscal", paisAId);
        pais.setImpReceitaFiscal(impReceitaFiscal);

        Map<String, Object> response = new HashMap<>();
        response.put("paisA", pais);
        response.put("idh", idh);
        response.put("impReceitaFiscal", impReceitaFiscal);

        return ResponseEntity.ok(response);
    }
}
