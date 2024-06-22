package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.models.Dados;
import br.com.alura.TabelaFipe.models.Modelos;
import br.com.alura.TabelaFipe.service.ConsumoAPI;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";


    public void exibeMenu(){
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar:""";
        System.out.println(menu);
        var resposta = scanner.nextLine();
        String endereco;
        if(resposta.toLowerCase().contains("carr")){
            endereco =  URL_BASE + "carros/marcas";
        } else if (resposta.toLowerCase().contains("mot")) {
            endereco =  URL_BASE + "motos/marcas";
        } else {
            endereco =  URL_BASE + "caminhoes/marcas";
        }
        var json = consumoAPI.obterDados(endereco);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = scanner.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumoAPI.obterDados(endereco);
        var listaModelos = conversor.ObterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca: ");
        listaModelos.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
    }
}
