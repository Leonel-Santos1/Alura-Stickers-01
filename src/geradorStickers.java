import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class geradorStickers {
    public void geraStickers(InputStream inputStream, String nomeArquivo, String frase) throws Exception {

        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        //criação de uma nova imagem em memória (com tamanho novo e transparência)
        int altura = imagemOriginal.getHeight();
        int largura = imagemOriginal.getWidth();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        //copiar imagem original para nova imagem(em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        //Configuração da fonte
        var fonte = new Font("Comic Sans MC", Font.BOLD, 62);
        graphics.setColor(Color.RED);
        graphics.setFont(fonte);

        //escrever uma legenda na nova imagem
        graphics.drawString(frase, 200, novaAltura - 100);

        //escrever a nova imagem no arquivo
        File diretorio = new File("saida/" + nomeArquivo);
        diretorio.getParentFile().mkdirs();
        ImageIO.write(novaImagem, "png", diretorio);
    }

}
