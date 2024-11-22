package cn.edu.fdu.Lab1.service.impl;

import cn.edu.fdu.Lab1.constant.ErrorMessageEnum;
import cn.edu.fdu.Lab1.domain.CommandContext;
import cn.edu.fdu.Lab1.domain.HTMLElement;
import cn.edu.fdu.Lab1.service.Command;
import org.jetbrains.annotations.NotNull;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.spelling.SpellingCheckRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpellCheckCommand implements Command {
    private CommandContext context;

    public SpellCheckCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        spellCheck();
    }

    public void spellCheck() {
        try {
            List<String> misspelledWords = getErrorSpelling();

            if (misspelledWords.isEmpty()) {
                System.out.println("No spelling errors found.");
            } else {
                System.out.println("Spelling errors:");
                for (String error : misspelledWords) {
                    System.out.println(error);
                }
            }
        } catch (Exception e) {
            System.out.println("Error initializing LanguageTool: " + e.getMessage());
        }
    }

    private @NotNull List<String> getErrorSpelling() throws IOException {
        JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-US"));
        List<String> misspelledWords = new ArrayList<>();

        for (Rule rule : langTool.getAllActiveRules()) {
            if (rule instanceof SpellingCheckRule) {
                List<String> wordsToIgnore = Arrays.asList("specialword", "myotherword");
                ((SpellingCheckRule) rule).addIgnoreTokens(wordsToIgnore);
            }
        }

        for (HTMLElement element : context.getIdMap().values()) {
            String text = element.getTextContent();
            if (text != null && !text.isEmpty()) {
                List<RuleMatch> matches = langTool.check(text);
                for (RuleMatch match : matches) {
                    String errorText = "Error: " + match.getMessage() +
                            " at position " + match.getFromPos() +
                            " - " + match.getToPos();
                    misspelledWords.add(errorText);
                }
            }
        }

        return misspelledWords;
    }

    @Override
    public void undo() {
        System.out.println(ErrorMessageEnum.CANNOT_UNDO.getType());
    }
}
