package com.company;

import java.util.Scanner;

public class Chat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String statement = scanner.nextLine();

        String[] repliesToIllegalRequestBot1 = {"what",
                "say I should say", "say what? " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER + "? what’s " +
                ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER + "?", "Are you kidding me? what is " +
                ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER + "?"};
        String[] repliesToIllegalRequestBot2 = {"whaaat", "say say", "What do you mean by saying " +
                ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER + "?"};

        String[] repliesToLegalRequestBot1 = {"What if I don't want to say " +
                ChatterBot.REQUESTED_PHRASE_PLACEHOLDER + "?", "OK OK, I will say " +
                ChatterBot.REQUESTED_PHRASE_PLACEHOLDER + " here: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};
        String[] repliesToLegalRequestBot2 = {"say " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER + "? okay:"
                + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER, "Although you're not Hertzel, i will say that: " +
                ChatterBot.REQUESTED_PHRASE_PLACEHOLDER, "Fine, " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};

        ChatterBot[] bots = new ChatterBot[2];
        bots[0] = new ChatterBot("Thiago", repliesToLegalRequestBot1, repliesToIllegalRequestBot1);
        bots[1] = new ChatterBot("Mar", repliesToLegalRequestBot2, repliesToIllegalRequestBot2);

        while (true) {
            for (ChatterBot bot : bots) {
                statement = bot.replyTo(statement);
                System.out.print(bot.getName() + ": " + statement);
                scanner.nextLine();
            }
        }

    }

}
