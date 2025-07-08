package forca;

import java.util.stream.Stream;

import forca.model.HangmanChar;
import forca.model.HangmanGame;

public class Main {
    public static void main(String... args) {
       var characters = Stream.of(args)
                    .map(a -> a.toLowerCase().charAt(0))
                    .map(HangmanChar::new).toList();
        
        System.out.println(characters);
        var hangmanGame = new HangmanGame(characters);
        System.out.println(new HangmanGame(characters));
        System.out.println(hangmanGame);
        hangmanGame.inputCharacter('a');
        System.out.println(hangmanGame);
        hangmanGame.inputCharacter('a');
        System.out.println(hangmanGame);
        hangmanGame.inputCharacter('c');
        System.out.println(hangmanGame);
        hangmanGame.inputCharacter('d');
        System.out.println(hangmanGame);
        hangmanGame.inputCharacter('h');
        System.out.println(hangmanGame);
        hangmanGame.inputCharacter('j');
        System.out.println(hangmanGame);
        hangmanGame.inputCharacter('k');

    }
}