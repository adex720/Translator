package com.adex.translator;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Translator {

    public final JDA jda;
    public final Logger logger;
    public final LanguageDetector detector;

    public Translator(String token) throws LoginException, InterruptedException {
        detector = new LanguageDetector();

        logger = LoggerFactory.getLogger(Translator.class);

        jda = JDABuilder.createDefault(token)
                .setActivity(Activity.watching("-help"))
                .addEventListeners(detector)
                .build()
                .awaitReady();
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        String token = "";
        try {
            File prefixFile = new File("config.json");

            Object config = new JSONParser().parse(new FileReader(prefixFile));

            JSONObject configJson = (JSONObject) config;

            token = Objects.toString(configJson.get("token"));
        } catch (ParseException | IOException ignored) {
        }

        Translator bot = new Translator(token);

    }

}
