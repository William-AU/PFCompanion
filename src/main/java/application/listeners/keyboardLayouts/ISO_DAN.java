package application.listeners.keyboardLayouts;

import application.listeners.ListenerKey;
import application.listeners.ListenerModifier;

public class ISO_DAN implements KeyboardLayout {

    private char getChar(int code) {
        return switch (code) {
            case 57 -> ' ';
            // Numbers
            case 2 -> '1';
            case 3 -> '2';
            case 4 -> '3';
            case 5 -> '4';
            case 6 -> '5';
            case 7 -> '6';
            case 8 -> '7';
            case 9 -> '8';
            case 10 -> '9';
            case 11 -> '0';
            // Symbols
            case 12 -> '+';
            // Letters
            case 16 -> 'q';
            case 17 -> 'w';
            case 18 -> 'e';
            case 19 -> 'r';
            case 20 -> 't';
            case 21 -> 'y';
            case 22 -> 'u';
            case 23 -> 'i';
            case 24 -> 'o';
            case 25 -> 'p';
            case 30 -> 'a';
            case 31 -> 's';
            case 32 -> 'd';
            case 33 -> 'f';
            case 34 -> 'g';
            case 35 -> 'h';
            case 36 -> 'j';
            case 37 -> 'k';
            case 38 -> 'l';
            case 44 -> 'z';
            case 45 -> 'x';
            case 46 -> 'c';
            case 47 -> 'v';
            case 48 -> 'b';
            case 49 -> 'n';
            case 50 -> 'm';
            // ISO-DAN specific
            case 39 -> 'æ';
            case 40 -> 'ø';
            case 26 -> 'å';
            default -> '\0';
        };
    }

    @Override
    public char getChar(int code, ListenerModifier modifier) {
        return switch (modifier) {
            case ALT -> getCharWithALT(code);
            case CTRL -> getCharWithCTRL(code);
            case SHIFT -> getCharWithSHIFT(code);
            case NONE -> getChar(code);
        };
    }

    private char getCharWithSHIFT(int code) {
        return switch (code) {
            // Numbers
            case 2 -> '!';
            case 3 -> '"';
            case 4 -> '#';
            case 5 -> '€';
            case 6 -> '%';
            case 7 -> '&';
            case 8 -> '/';
            case 9 -> '(';
            case 10 -> ')';
            case 11 -> '=';
            // Symbols
            case 12 -> '?';
            // Letters
            case 16 -> 'Q';
            case 17 -> 'W';
            case 18 -> 'E';
            case 19 -> 'R';
            case 20 -> 'T';
            case 21 -> 'Y';
            case 22 -> 'U';
            case 23 -> 'I';
            case 24 -> 'O';
            case 25 -> 'P';
            case 30 -> 'A';
            case 31 -> 'S';
            case 32 -> 'D';
            case 33 -> 'F';
            case 34 -> 'G';
            case 35 -> 'H';
            case 36 -> 'J';
            case 37 -> 'K';
            case 38 -> 'L';
            case 44 -> 'Z';
            case 45 -> 'X';
            case 46 -> 'C';
            case 47 -> 'V';
            case 48 -> 'B';
            case 49 -> 'N';
            case 50 -> 'M';
            // ISO-DAN specific
            case 39 -> 'Æ';
            case 40 -> 'Ø';
            case 26 -> 'Å';
            default -> '\0';
        };
    }

    private char getCharWithCTRL(int code) {
        return '\0';
    }

    private char getCharWithALT(int code) {
        return '\0';
    }

    @Override
    public ListenerKey getKey(int code) {
        return switch (code) {
            case 57419 -> ListenerKey.LEFT;
            case 57416 -> ListenerKey.UP;
            case 57421 -> ListenerKey.RIGHT;
            case 57424 -> ListenerKey.DOWN;
            case 28 -> ListenerKey.ENTER;
            case 15 -> ListenerKey.TAB;
            case 14 -> ListenerKey.BACKSPACE;
            case 1 -> ListenerKey.ESCAPE;
            default -> ListenerKey.NONE;
        };
    }

    @Override
    public ListenerKey getKeyWithModifier(int code, ListenerModifier modifier) {
        // Super hacky solution because we only really care about SHIFT + TAB right now
        if (modifier != ListenerModifier.SHIFT) {
            return getKey(code);
        }
        if (code != 15) {
            return getKey(code);
        }
        return ListenerKey.SHIFT_TAB;
    }

    @Override
    public ListenerModifier getModifier(int code) {
        return switch (code) {
            case 42 -> ListenerModifier.SHIFT;
            case 29 -> ListenerModifier.CTRL;
            case 56 -> ListenerModifier.ALT;
            default -> ListenerModifier.NONE;
        };
    }
}
