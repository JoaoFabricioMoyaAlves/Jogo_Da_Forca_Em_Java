package forca;

import java.util.Scanner;
import java.util.stream.Stream;

import forca.exception.GameIsFinishedException;
import forca.model.HangmanChar;
import forca.model.HangmanGame;

public class Main {

    private final static Scanner sc = new Scanner(System.in);
    public static void main(String... args) {
       var characters = Stream.of(args)
                    .map(a -> a.toLowerCase().charAt(0))
                    .map(HangmanChar::new).toList();
        
        System.out.println(characters);
        var hangmanGame = new HangmanGame(characters);
        System.out.println("Bem vindo ao jogo da forca tente adivinha a palavra: ");
        System.out.println(hangmanGame);
        System.out.println("Selecione uma das opções: ");
        System.out.println("1 - Informar uma letra");
        System.out.println("2 - Verificar Status do jogo");
        System.out.println("3 - Sair do Jogo");
        var option = -1;
        while(true){
             System.out.println("Selecione uma das opções: ");
             System.out.println("1 - Informar uma letra");
             System.out.println("2 - Verificar Status do jogo");
             System.out.println("3 - Sair do Jogo");
             option = sc.nextInt();
              switch (option) {
                case 1 -> inputCharacter(hangmanGame);
                case 2 -> showGameStatus(hangmanGame);
                case 3 -> System.exit(0);
    
                default -> System.out.println("Opção inválida");
                    
              }
        
        }
      

    }

    private static void showGameStatus(final HangmanGame hangmanGame){
        System.out.println(hangmanGame.getHangmanGameStatus());
        System.out.println(hangmanGame);
    }


     private static void inputCharacter(final HangmanGame hangmanGame){
         System.out.println("Informe uma leta: ");
                var character = sc.next().charAt(0);
                try{
                    hangmanGame.inputCharacter(character);
                }catch(LayerInstantiationException ex){
                    System.out.println(ex.getMessage());
                  
                }catch(GameIsFinishedException ex){
                    System.out.println(ex.getMessage());
                    System.exit(0);
                }
                System.out.println(hangmanGame);
     }

}