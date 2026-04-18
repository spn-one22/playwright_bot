package org.example.core.geo;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserContext;

/**
 * 🌍 GeoApplier
 * Централизует ВСЁ, что связано с географией и локалью браузера
 */

public class GeoApplier {

    // =========================
    // LAUNCH LEVEL
    // =========================

    public static void applyLaunchArgs(BrowserType.LaunchOptions options, GeoData geo) {

        options.setArgs(java.util.List.of(
                "--lang=" + geo.locale,
                "--accept-lang=" + geo.locale + "," + geo.language
        ));
    }

    // =========================
    // CONTEXT LEVEL
    // =========================

    public static void applyContext(Browser.NewContextOptions options, GeoData geo) {

        options.setLocale(geo.locale);
        options.setTimezoneId(geo.timezone);
    }

    // =========================
    // JS LEVEL (navigator / Intl)
    // =========================

    public static String navigatorLanguage(GeoData geo) {
        return geo.language;
    }

    public static String[] navigatorLanguages(GeoData geo) {
        return new String[]{geo.locale, geo.language};
    }

    public static String navigatorLanguagesJs(GeoData geo) {
        return "['%s', '%s']".formatted(geo.locale, geo.language);
    }

    public static void applyNavigatorJs(BrowserContext context, GeoData geo) {

        context.addInitScript("""
(() => {

    const lang = '%s';

    // =========================
    // 🧠 REALISTIC VOICE SET
    // =========================

    const voicesRaw = [
        {
            name: "Microsoft Katja - German (Germany)",
            lang: "de-DE",
            localService: true,
            default: true,
            voiceURI: "Microsoft Katja - German (Germany)"
        },
        {
            name: "Google Deutsch",
            lang: "de-DE",
            localService: true,
            default: false,
            voiceURI: "Google Deutsch"
        }
    ];

    // =========================
    // 🧬 PROTOTYPE EMULATION
    // =========================

    function FakeVoice(v) {
        this.name = v.name;
        this.lang = v.lang;
        this.localService = v.localService;
        this.default = v.default;
        this.voiceURI = v.voiceURI;
    }

    FakeVoice.prototype = SpeechSynthesisVoice?.prototype || {};

    const voices = voicesRaw.map(v => new FakeVoice(v));

    // =========================
    // 🧠 OVERRIDE GETVOICES
    // =========================

    const synth = window.speechSynthesis;

    const original = synth.getVoices.bind(synth);

    Object.defineProperty(synth, 'getVoices', {
        value: () => voices,
        configurable: true
    });

    // =========================
    // ⏳ ASYNC LOADING BEHAVIOR
    // =========================

    let fired = false;

    const fire = () => {
        if (fired) return;
        fired = true;

        try {
            window.dispatchEvent(new Event('speechSynthesisVoicesChanged'));
            synth.dispatchEvent(new Event('voiceschanged'));
        } catch (e) {}
    };

    setTimeout(fire, 50);
    setTimeout(fire, 300);

})();
""".formatted(geo.locale));

        context.addInitScript(
                """
                // =========================
                // 🌍 GEO NAVIGATOR LAYER
                // =========================
    
                Object.defineProperty(navigator, 'language', {
                    get: () => '%s'
                });
    
                Object.defineProperty(navigator, 'languages', {
                    get: () => %s
                });
                """.formatted(
                        geo.language,
                        "['%s', '%s']".formatted(geo.locale, geo.language)
                )
        );

    }

}