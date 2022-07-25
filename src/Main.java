import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static String ANSI_BLACK = "\u001B[30m";

    public static void main(String[] args) throws Exception {

        var gerador = new geradorStickers();

        //Realizar a conexão HTTP e buscar os top 250 filmes

        String url = "https://api.mocki.io/v2/549a5d8b/MostPopularTVs";

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

            String titulo = filme.get("title");
            System.out.println("\u001b[107m" + "\u001b[1m" + ANSI_BLACK + ("Título:") + titulo + ANSI_RESET);

            System.out.println(filme.get("image"));
            String urlImage = filme.get("image");

            String nomeArquivo = titulo + ".png";
            InputStream inputStream = new URL(urlImage).openStream();
            gerador.geraStickers(inputStream, nomeArquivo);

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