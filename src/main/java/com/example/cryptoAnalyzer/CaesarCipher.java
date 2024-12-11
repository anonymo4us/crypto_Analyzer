package com.example.cryptoAnalyzer;

// encode 2 pathIn pathOut

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {

    private static final String lat = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String cyr = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String symbols = ".,”’:;#@$^&*()[]{}<>-!?\"'—\\|/_+= ";
    private static final char[] ALPHABET = (lat + cyr + symbols).toCharArray();
    private static final Map<Character, Integer> ALPHABET_INDEX = new HashMap<>();

    static {
        for (int i = 0; i < ALPHABET.length; i++) {
            ALPHABET_INDEX.put(ALPHABET[i], i);
        }
    }

    public static void main(String[] args) {

        String action = args[0];
        int key = Integer.parseInt(args[1]);
        Path pathIn = Path.of(args[2]);
        Path pathOut = Path.of(args[3]);

        switch (action) {
            case "encode": {
                try (BufferedReader inputStream = Files.newBufferedReader(pathIn);
                     BufferedWriter outputStream = Files.newBufferedWriter(pathOut)) {

                    char newSymbol;

                    while (inputStream.ready()) {
                        char symbol = (char) inputStream.read();
                        int i = ALPHABET_INDEX.getOrDefault(symbol, -1);
                        if (i != -1) {
                            int nextKey = (i + key + ALPHABET.length) % ALPHABET.length;// int newIdx = (idx + key + len) % len;
                            newSymbol = ALPHABET[nextKey];
                        } else {
                            newSymbol = symbol;
                        }
                        outputStream.write(newSymbol);
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "decode": {
                try (BufferedReader inputStream = Files.newBufferedReader(pathIn);
                     BufferedWriter outputStream = Files.newBufferedWriter(pathOut)) {

                    char newSymbol;

                    while (inputStream.ready()) {
                        char symbol = (char) inputStream.read();
                        int i = ALPHABET_INDEX.getOrDefault(symbol, -1);
                        if (i != -1) {
                            int nextKey = (i + (-key) + ALPHABET.length) % ALPHABET.length;// int newIdx = (idx + key + len) % len;
                            newSymbol = ALPHABET[nextKey];
                        } else {
                            newSymbol = symbol;
                        }
                        outputStream.write(newSymbol);
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}