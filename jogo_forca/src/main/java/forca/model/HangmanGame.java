package forca.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import forca.exception.GameIsFinishedException;
import forca.exception.LetterAlreadyInputtedException;

import static forca.model.HangmanGameStatus.PENDING;
import static forca.model.HangmanGameStatus.WIN;
import static forca.model.HangmanGameStatus.LOSE;

public class HangmanGame {

    private final static int HANGMAN_INITIAL_LINE_LENGTH = 9;
     private final static int HANGMAN_INITIAL_LINE_LENGTH_WITH_LINE_SEPARATOR = 10;

     private final int lineSize;
     private final int hangmanInitialSize;
     private final List<HangmanChar> hangmanPaths;
     private final List<HangmanChar> characters;
     private final List<Character> failAttempts = new ArrayList<>();

    private String hangman;
    private HangmanGameStatus hangmanGameStatus;

    public HangmanGame(final List<HangmanChar> characters){
        var whiteSpaces = " ".repeat(characters.size());
        var characterSpace = "-".repeat(characters.size());
        this.lineSize = HANGMAN_INITIAL_LINE_LENGTH_WITH_LINE_SEPARATOR + whiteSpaces.length();
        this.hangmanGameStatus = PENDING;
        this.hangmanPaths = buildHangmanPathsPositions();
        buildHangmanDesign(whiteSpaces, characterSpace);
        this.characters = setCharacterSpacesPositionInGame(characters, whiteSpaces.length());
        this.hangmanInitialSize = hangman.length();
    }

    public void inputCharacter(final char character){
        if(this.hangmanGameStatus != PENDING){
            var message = this.hangmanGameStatus == WIN ?
                      "Parabéns você Ganhou!" :
                      "Você perdeu, tente novamente";
            throw new GameIsFinishedException(message);
        }

        var found = this.characters.stream().filter(c -> c.getCharacter() == character).toList();

        if (this.failAttempts.contains(character)){
           throw new LetterAlreadyInputtedException("A letra "+ character  + " já foi informada anteriormente");
       
        }

        if(found.isEmpty()){
            failAttempts.add(character);
            if(failAttempts.size() >= 6){
                this.hangmanGameStatus = LOSE;
            }
            rebuildHangman(this.hangmanPaths.removeFirst());
            return;
        }

        if(found.getFirst().isVisible()){
            throw new LetterAlreadyInputtedException("A letra "+ character  + " já foi informada anteriormente");
        }


        this.characters.forEach(c -> {
            if(c.getCharacter() == found.getFirst().getCharacter()){
                c.enableVisibility();
            }
        });
        if(this.characters.stream().noneMatch(HangmanChar::isTnvisible)){
            this.hangmanGameStatus =  WIN;
            rebuildHangman(found.toArray(HangmanChar[]::new));
        }
    }

    public HangmanGameStatus getHangmanGameStatus(){
        return hangmanGameStatus;
    }

    @Override
    public String toString(){
        return hangman;
    }

    private List<HangmanChar> buildHangmanPathsPositions(){
        final var HEAD_LINE = 3;
        final var BODY_LINE = 4;
        final var LEGS_LINE = 5;
        return new ArrayList<>(
            List.of(
                new HangmanChar('O', this.lineSize * HEAD_LINE + 6),
                new HangmanChar('|',this.lineSize * BODY_LINE +6),
                new HangmanChar('/',this.lineSize * BODY_LINE +5),
                new HangmanChar('\\',this.lineSize * BODY_LINE +7),
                 new HangmanChar('/',this.lineSize * LEGS_LINE +5),
                new HangmanChar('\\',this.lineSize * LEGS_LINE +7)
                
            )
        );
    }

    private List<HangmanChar> setCharacterSpacesPositionInGame(final List<HangmanChar> characters, final int whiteSpacesAmount){
         final var LINE_LETTER = 6;
         for (int i = 0; i< characters.size(); i++){
            characters.get(i).setPosition(this.lineSize * LINE_LETTER + HANGMAN_INITIAL_LINE_LENGTH + i);
         }
         return characters;
    }

    private void rebuildHangman(final HangmanChar... hangmanChars){

        var hangmanBuilder = new StringBuilder(this.hangman);
        Stream.of(hangmanChars).forEach(h -> hangmanBuilder.setCharAt(h.getPosition(), h.getCharacter()));
        var failMessage = this.failAttempts.isEmpty() ? " " : "tentativas" + this.failAttempts;
        this.hangman = hangmanBuilder.substring(0,hangmanInitialSize) + failAttempts;
    }


    private void buildHangmanDesign(final String whiteSpaces, final String characterSpaces){

         this.hangman = "  -----  "+whiteSpaces + System.lineSeparator()+
                        "  |   |  "+whiteSpaces + System.lineSeparator()+
                        "  |   |  "+whiteSpaces + System.lineSeparator()+
                        "  |      "+whiteSpaces + System.lineSeparator()+
                        "  |      "+whiteSpaces + System.lineSeparator()+
                        "  |      "+whiteSpaces + System.lineSeparator()+
                        "  |      "+whiteSpaces + System.lineSeparator()+
                        "========="+characterSpaces + System.lineSeparator();
            }

    
}
