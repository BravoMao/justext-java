package nl.wizenoze.justext.example;

import nl.wizenoze.justext.JusText;
import nl.wizenoze.justext.paragraph.Paragraph;
import nl.wizenoze.justext.util.StopWordsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class demo {
  public static void main(String[] args) {
    JusText jusText = new JusText();
    String url =
        "http://www.devx.com/wireless/remote-work-and-the-social-forces-and-technologies-that-enable-it.html";

    Set<String> stopWords = StopWordsUtil.getStopWords("en");
    List<Paragraph> paragraphs = jusText.extract(readTextFromUrl(url), stopWords);

    paragraphs.stream().forEach(System.out::print);
  }

  public static String readTextFromUrl(String urlString) {
    String out = null;
    try {

      URL url = new URL(urlString);

      // read text returned by server
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = in.readLine()) != null) {
        sb.append(line);
      }
      out = sb.toString();
      in.close();
    } catch (MalformedURLException e) {
      System.out.println("Malformed URL: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("I/O Error: " + e.getMessage());
    }
    return out;
  }
}
