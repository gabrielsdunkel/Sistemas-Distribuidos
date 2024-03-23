package com.mycompany.numerosprimos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class VerificaPrimo {

    // Transformando a classe em estática
    public static class StaticVerificaPrimo {
        
        static List<String> strings;
        //Ler cada linha do arquivo de texto
        public static void lerArquivo() throws IOException {
            Path path = Path.of("C:\\Users\\gacla\\Desktop\\Entrada01.txt");
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\gacla\\Desktop\\Entrada01.txt"));
            strings = Files.readAllLines(path);
        
            for(String texto: strings){
                System.out.println(texto);
            }
        }
        
        public static boolean ehPrime(int number) {
            if (number <= 1) {
                return false;
            }
            if (numbe == 2){
                return true
            }
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
