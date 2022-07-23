import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {
  String subTitle = "";

  public void cria(InputStream inputStream, String fileName, String subTitle) throws Exception {
    this.subTitle = subTitle;
    cria(inputStream, fileName);
  }

  public void cria(InputStream inputStream, String fileName) throws Exception {
    // leitura da imagem
    BufferedImage imageOriginal = ImageIO.read(inputStream);

    // cria nova imagem em memória com transparência e com tamanho novo
    int largura = imageOriginal.getWidth();
    int altura = imageOriginal.getHeight();
    int novaAltura = altura + 200;
    String sTitle, sSubTitle;
    BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

    // define o texto que será usado
    sTitle = "#TOPZERA";
    if (!subTitle.trim().isEmpty()) {
      sSubTitle = subTitle;
    } else {
      sSubTitle = "COM ALURA";
    }

    // copiar a imagem original pra novo imagem (em memória)
    Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
    graphics.drawImage(imageOriginal, 0, 0, null);

    // configurar a fonte
    Font font;
    int fontTitle = 128;

    font = new Font(Font.SANS_SERIF, Font.BOLD, fontTitle);
    graphics.setFont(font);

    for (int iRepeat = fontTitle; iRepeat > 0; iRepeat--) {
      if (graphics.getFontMetrics().stringWidth(sTitle) > novaImagem.getWidth()) {
        graphics.setFont(font);
        font = new Font(Font.SANS_SERIF, Font.BOLD, --fontTitle);
      } else
        break;
    }

    graphics.setColor(Color.CYAN);

    // escrever uma frase na nova imagem... Centrralizando
    graphics.drawString(sTitle,
        (novaImagem.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(sTitle) / 2),
        novaImagem.getHeight() - 100);

    // adicionando textos
    int sizeFontSubTitle = (int) (fontTitle * 0.45);
    font = new Font(Font.SANS_SERIF, Font.BOLD, sizeFontSubTitle);
    graphics.setColor(Color.ORANGE);
    graphics.setFont(font);

    // Centrralizando, sub-título
    graphics.drawString(sSubTitle, (novaImagem.getWidth() / 2) - (graphics.getFontMetrics().stringWidth(sSubTitle) / 2),
        novaImagem.getHeight() - graphics.getFontMetrics().getHeight());

    // cria o novo arquivo, incluindo a pasta de destino se não existe
    var fileNewToWrite = new File(fileName);
    fileNewToWrite.mkdirs();

    // escrever a nova imagem em um arquivo
    ImageIO.write(novaImagem, "png", fileNewToWrite);
  }
}
