package org.technicfox.emojify.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.technicfox.emojify.Emojify;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LocalizationUtil {
    private static final String DEFAULT_LANG = "en_us";
    private static final Map<String, YamlConfiguration> LANGUAGES = new HashMap<>();
    private final File langFolder;
    private final Logger logger = Emojify.getEmojifyLogger();
    private String currentLang = DEFAULT_LANG;

    public LocalizationUtil(File langFolder) {
        this.langFolder = langFolder;
        loadLanguageFile(DEFAULT_LANG);
    }

    private void loadLanguageFile(String lang) {
        if (LANGUAGES.containsKey(lang)) return;

        File langFile = new File(langFolder, lang + ".yml");
        if (langFile.exists()) {
            YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(langFile);
            LANGUAGES.put(lang, langConfig);
            logger.info("Loaded language: " + lang);
        } else {
            logger.warning("Language file not found for: " + lang);
        }
    }

    public String getTranslation(String path) {
        YamlConfiguration currentLangConfig = LANGUAGES.get(currentLang);
        if (currentLangConfig != null && currentLangConfig.contains(path)) {
            return currentLangConfig.getString(path);
        }

        YamlConfiguration defaultLangConfig = LANGUAGES.get(DEFAULT_LANG);
        if (defaultLangConfig != null && defaultLangConfig.contains(path)) {
            return defaultLangConfig.getString(path);
        }

        return path;
    }

    public void setCurrentLang(String lang) {
        if (!LANGUAGES.containsKey(lang)) {
            loadLanguageFile(lang);
        }
        this.currentLang = lang;
    }
}
