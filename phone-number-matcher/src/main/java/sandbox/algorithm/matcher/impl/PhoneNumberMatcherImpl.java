package sandbox.algorithm.matcher.impl;

import sandbox.algorithm.matcher.PhoneNumberMatcher;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PhoneNumberMatcherImpl implements PhoneNumberMatcher {

    private final Pattern anyDigitPattern = Pattern.compile("\\d");
    private final char[] sameDigitCharacter = {'A', 'B', 'C', 'D'};

    @Override
    public boolean matches(String mask, String phoneNumber) {
        if (mask.length() != phoneNumber.length()) {
            return false;
        }

        var indexBySameDigitMap = getIndexBySameDigitCharacter(mask);

        if (indexBySameDigitMap.isEmpty()) {
            return validatePhoneNumberByDigitCharactersMask(mask, phoneNumber);
        }


        return validatePhoneNumberBySameDigitCharacter(indexBySameDigitMap, mask, phoneNumber);
    }

    private boolean validatePhoneNumberBySameDigitCharacter(Map<Character, List<Integer>> indexBySameDigitMap,
                                                            String mask, String phoneNumber) {
        var repeatedDigits = new HashSet<Character>();

        for (var entry : indexBySameDigitMap.entrySet()) {
            var phoneNumberPart = entry.getValue().stream()
                    .map(phoneNumber::charAt)
                    .collect(Collectors.toSet());

            if (phoneNumberPart.size() != 1) {
                return false;
            }

            repeatedDigits.add(phoneNumberPart.iterator().next());
        }

        var unequalLetters = repeatedDigits.size() == indexBySameDigitMap.size();

        return unequalLetters && validatePhoneNumberByDigitCharactersMask(mask, phoneNumber);
    }

    private boolean validatePhoneNumberByDigitCharactersMask(String mask, String phoneNumber) {
        if (anyDigitPattern.matcher(mask).find()) {
            for (int i = 0; i < mask.length(); i++) {
                var maskCharacter = mask.charAt(i);
                if (Character.isDigit(maskCharacter) && phoneNumber.charAt(i) != maskCharacter) {
                    return false;
                }
            }
        }

        return true;
    }

    private Map<Character, List<Integer>> getIndexBySameDigitCharacter(String mask) {
        var indexObject = new HashMap<Character, List<Integer>>();

        for (var character : sameDigitCharacter) {
            Optional.of(getCharacterIndex(mask, character))
                    .filter(listIndexes -> !listIndexes.isEmpty())
                    .ifPresent(listIndexes -> indexObject.put(character, listIndexes));
        }

        return indexObject;
    }

    private List<Integer> getCharacterIndex(String mask, char character) {
        var indexList = new ArrayList<Integer>();

        var index = mask.indexOf(character);
        while (index >= 0) {
            indexList.add(index);
            index = mask.indexOf(character, index + 1);
        }

        return indexList;
    }
}
