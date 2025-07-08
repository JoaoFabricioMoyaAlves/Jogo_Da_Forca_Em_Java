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
        System.out.println(new HangmanGame(characters));

    }
}