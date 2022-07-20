/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.enigmamachine;

import java.util.ArrayList;

/**
 *
 * @author Gebruiker
 */
public class Machine {

    ArrayList<char[]> rottors = new ArrayList<char[]>();
    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    char[] rightRottor = "BDFHJLCPRTXVZNYEIWGAKMUSQOBDFHJLCPRTXVZNYEIWGAKMUSQO".toCharArray();
    char[] middleRottor = "AJDKSIRUXBLHWTMCQGZNPYFVOEAJDKSIRUXBLHWTMCQGZNPYFVOE".toCharArray();
    char[] leftRottor = "EKMFLGDQVZNTOWYHXUSPAIBRCJEKMFLGDQVZNTOWYHXUSPAIBRCJ".toCharArray();
    char[] reflector = "ABCDEFGDIJKGMKMIEBFTCVVJAT".toCharArray();

    public Machine() {
        rottors.add(rightRottor);
        rottors.add(middleRottor);
        rottors.add(leftRottor);
    }

    public String translate(char[] letters, int[] initialPositions) {
        String translation = "";
        for (int i = 0; i < letters.length; i++) {
            if (isLetter(letters[i])) {
                int inputPosition = getInputPosition(letters[i]);
                for (int x = 0; x < 3; x++) {
                    if (inputPosition + initialPositions[x] > 51) {
                        inputPosition = inputPosition % 26;
                    }
                    inputPosition = getPosition(initialPositions[x], inputPosition, rottors.get(x));
                }
                inputPosition = reflectLetter(inputPosition);
                for (int x = 2; x >= 0; x--) {
                    if (inputPosition + initialPositions[x] > 51) {
                        inputPosition = inputPosition % 26;
                    }
                    inputPosition = getReturnPosition(initialPositions[x], inputPosition, rottors.get(x));

                }
                translation = translation + Character.toString(alphabet[inputPosition % 26]);
                initialPositions = resetInitialPosition(initialPositions);

            }

        }
        return translation;
    }

    int[] determineInitialPosition(char[] rotterPosition) {
        int[] initialPositions = new int[rotterPosition.length];
        for (int i = 0; i < rotterPosition.length; i++) {
            for (int x = 0; x < alphabet.length; x++) {
                if (rotterPosition[i] == alphabet[x]) {
                    if (i == 2) {
                        initialPositions[rotterPosition.length - 1 - i] = x + 1;
                        break;
                    } else {
                        initialPositions[rotterPosition.length - 1 - i] = x;
                        break;
                    }
                }
            }
        }
        return initialPositions;
    }

    private int getInputPosition(char letter) {
        return letter - 'A';
    }

    private int getPosition(int initialPosition, int inputPosition, char[] rottorArray) {
        int rottorPosition = initialPosition + inputPosition;
         if (rottorPosition > 51) {
            rottorPosition = rottorPosition % 26;
        }       
        if (initialPosition > 26){
            initialPosition = initialPosition - 26;
        }
        char c = rottorArray[rottorPosition];
        for (int i = initialPosition; i < rottorArray.length; i++) {
            if (alphabet[i] == c) {
                return i - initialPosition;
            }
        }

        System.out.println("Return 0");

        return 0;
    }

    private int reflectLetter(int inputPosition) {
        char c = reflector[inputPosition];
        for (int i = 0; i < reflector.length; i++) {
            if (i == inputPosition) {
                continue;
            } else if (reflector[i] == c) {
                return i;
            }

        }
        System.out.println("000");

        return 0;
    }

    private int getReturnPosition(int initialPosition, int inputPosition, char[] rottorArray) {
        int rottorPosition = initialPosition + inputPosition;
        if (rottorPosition > 51) {
            rottorPosition = rottorPosition % 26;
        }
        if (initialPosition > 26){
            initialPosition = initialPosition - 26;
        }
        char c = alphabet[rottorPosition];
        System.out.println(c);
        for (int i = initialPosition; i < rottorArray.length; i++) {
            if (rottorArray[i] == c) {
                System.out.println(rottorArray[i]);                
                return i - initialPosition;
            }
        }
        
        System.out.println("I return 000");

        return 0;
    }

    private boolean isLetter(char letter) {
        return (letter > 64 && letter < 91) || (letter > 96 && letter < 123);
    }

    private int[] resetInitialPosition(int[] initialPositions) {
        initialPositions[0]++;
        if (initialPositions[0] >= 52) {
            initialPositions[0] = initialPositions[0] - 52;
        }
        if (initialPositions[0] == 22 || initialPositions[0] == 48) {
            initialPositions[1]++;
            if (initialPositions[1] >= 52) {
                initialPositions[1] = initialPositions[1] - 52;
            }
            if (initialPositions[1] == 5 || initialPositions[1] == 31) {
                initialPositions[2]++;
                if (initialPositions[2] >= 52) {
                    initialPositions[2] = initialPositions[2] - 52;
                }
                if (initialPositions[2] == 17 || initialPositions[2] == 43) {
                    resetInitialPosition(initialPositions);
                }
            }
        }
        return initialPositions;
    }

}
