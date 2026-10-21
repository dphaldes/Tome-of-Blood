package mod.tomeofblood.common.glyphs;

public class GlyphLibrary {
    public static final String EffectSentientHarm = prependGlyph("sentient_harm");
    public static final String EffectSentientWrath = prependGlyph("sentient_wrath");

    public static String prependGlyph(String glyph) {
        return "glyph_" + glyph;
    }
}
