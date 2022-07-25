import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static String ANSI_BLACK = "\u001B[30m";

    public static void main(String[] args) throws IOException, InterruptedException {

        //Realizar a conexão HTTP e buscar os top 250 filmes

        String url = "https://alura-filmes.herokuapp.com/conteudos";

        URI endereco = URI.create(url);

        var client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();

        //Pegar apenas os dados de interesse (Classificação, título, poster)

        JsonParser parser = new JsonParser();

        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        //Exibir e manipular os dados da forma que achar melhor

        for (Map<String, String> filme : listaDeFilmes) {

            System.out.println();

            System.out.println("\u001b[107m" + "\u001b[1m" + ANSI_BLACK + ("Título:") + filme.get("title") + ANSI_RESET);

            System.out.println(filme.get("image"));

            System.out.println("\u001b[3m" + "Classificação: " + filme.get("imDbRating") + ANSI_RESET);

            double notaDouble = Double.parseDouble(filme.get("imDbRating"));

            int notaInt = (int) notaDouble;

            for (int i = 0; i < notaInt; i++) {
                System.out.print("⭐️");
            }

            System.out.println();
        }

    }
}