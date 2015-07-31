/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Rule;
import model.Components;
import utils.Constants;
import utils.FileReader;
import model.RulesDefinitions;

/**
 *
 * @author ivanmoya
 */
public class PoemController {

    private static Rule ruleTree = new Rule();
    private static StringBuilder builder = new StringBuilder();
    
    private RulesDefinitions ruleDefinitions = new RulesDefinitions();
    

    public void readLines(String path) {
        
        ArrayList<String> lines;
        Components[] components = Components.values();

            lines = FileReader.readFile(path);
            for (String line : lines) {
                for (Components component : components) {
                    Pattern linePattern = Pattern.compile("(?<=^" + component + ":).*");
                    Matcher matcher = linePattern.matcher(line);
                    if (matcher.find()) {
                        switch (component) {

                            case ADJECTIVE:
                                ruleDefinitions.setAdjectiveRule(matcher.group());
                                break;

                            case LINE:
                                ruleDefinitions.setLineRule(matcher.group());
                                break;

                            case NOUN:
                                ruleDefinitions.setNounRule(matcher.group());
                                break;

                            case POEM:
                                ruleDefinitions.setPoemRule(matcher.group());
                                break;

                            case PREPOSITION:
                                ruleDefinitions.setPrepositionRule(matcher.group());
                                break;

                            case VERB:
                                ruleDefinitions.setVerbRule(matcher.group());
                                break;

                            case PRONOUN:
                                ruleDefinitions.setPronounRule(matcher.group());
                                break;
                        }
                    }
                }

            }
        clearString();
        generatePoem(ruleTree, Components.POEM);
    }
    
    public static void clearString() {
        builder = new StringBuilder();
    }
    
    public static String getPoem() {
        return builder.toString();
    }

    public void generatePoem(Rule ruleTree, Components component) {
        /**
         * Begin recursive search for rules
         */
        String ruleString = "";

        if (ruleTree.isValue()) {
            switch (ruleTree.getContent()) {
                case Constants.FINAL_BREAK:
                    builder.append("\n");
                    break;
                case Constants.END:
                    builder.deleteCharAt(builder.length()-1).append(" ");
                    break;
                default:
                    builder.append(ruleTree.getContent()).append(" ");
                    break;
            }
            
        } else {
            
            switch (component) {
                case ADJECTIVE:
                    ruleString = ruleDefinitions.getAdjectiveRule();
                    break;

                case LINE:
                    ruleString = ruleDefinitions.getLineRule();
                    break;

                case NOUN:
                    ruleString = ruleDefinitions.getNounRule();
                    break;

                case POEM:
                    ruleString = ruleDefinitions.getPoemRule();
                    break;

                case PREPOSITION:
                    ruleString = ruleDefinitions.getPrepositionRule();
                    break;

                case VERB:
                    ruleString = ruleDefinitions.getVerbRule();
                    break;

                case PRONOUN:
                    ruleString = ruleDefinitions.getPronounRule();
                    break;

            }

            ruleString = ruleString.trim();
            
            /*
             * Select the mandatory rules
             */
            String[] splittedString = ruleString.split(" ");
           
            for (String mandatoryField : splittedString) {
                 /*
                  * Select the non mandatory options
                  */
                String[] pipeSplitted = mandatoryField.split("\\|");
                if (pipeSplitted.length != 0) {
                    /*
                     * Select one option randomly
                    */
                    Random randomGenerator = new Random();
                    int randomNumber = randomGenerator.nextInt(pipeSplitted.length);
                    String randomValue = pipeSplitted[randomNumber];

                    if (isReference(randomValue)) {
                        Rule rule = getInstance(randomValue);
                        ruleTree.getRules().add(rule);
                        generatePoem(rule, Components.valueOf(rule.getRuleName()));
                    } else {
                        Rule rule = new Rule();
                        rule.setContent(randomValue);
                        rule.setValue(true);
                        generatePoem(rule, null);
                    }
                }
            }
        }
    }

    private boolean isReference(String value) {
        Pattern linePattern = Pattern.compile("(?<=<)[A-Z]*");
        Matcher matcher = linePattern.matcher(value);
        return matcher.find();
    }

    private Rule getInstance(String ruleName) {
        Pattern linePattern = Pattern.compile("(?<=<)[A-Z]*");
        Matcher matcher = linePattern.matcher(ruleName);
        Rule referenceRule = null;

        if (matcher.find()) {
            referenceRule = new Rule();
            referenceRule.setValue(false);

            switch (Components.valueOf(matcher.group())) {

                case ADJECTIVE:
                    referenceRule.setRuleName(Components.ADJECTIVE.toString());
                    break;

                case LINE:
                    referenceRule.setRuleName(Components.LINE.toString());
                    break;

                case NOUN:
                    referenceRule.setRuleName(Components.NOUN.toString());
                    break;

                case POEM:
                    referenceRule.setRuleName(Components.POEM.toString());
                    break;

                case PREPOSITION:
                    referenceRule.setRuleName(Components.PREPOSITION.toString());
                    break;

                case VERB:
                    referenceRule.setRuleName(Components.VERB.toString());
                    break;

                case PRONOUN:
                    referenceRule.setRuleName(Components.PRONOUN.toString());
                    break;
            }
        }
        return referenceRule;
    }

}
