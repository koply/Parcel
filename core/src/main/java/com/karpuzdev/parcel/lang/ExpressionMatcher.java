package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.compilers.TileCompiler;
import com.karpuzdev.parcel.lang.helpers.CompileInformation;
import com.karpuzdev.parcel.lang.helpers.CompileResult;
import com.karpuzdev.parcel.lang.helpers.MatchResult;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds the correct compiler from the given line. Finding
 * is done by matching the regex patterns.
 */
final class ExpressionMatcher {

    private ExpressionMatcher() { }

    // Regex -> TileExpression
    private static final Map<Pattern, TileCompiler> matcher = new HashMap<>();

    static void registerExpression(TileCompiler exp) {
        for (String regex : exp.getMatchers()) {
            matcher.put(Pattern.compile(regex), exp);
        }
    }

    static MatchResult match(String line) {
        for (Map.Entry<Pattern, TileCompiler> entry : matcher.entrySet()) {
            Matcher matcher = entry.getKey().matcher(line);
            if (matcher.matches()) {

                int count = matcher.groupCount();
                String[] groups = new String[count];

                for (int i = 0; i < count; i++) {

                    // group 0 of matcher is the whole match.
                    // So groups start at 1
                    groups[i] = matcher.group(i+1);
                }

                return new MatchResult(entry.getValue(), groups);
            }
        }

        return null;
    }

    static CompileResult compile(CompileInformation info) {
        MatchResult result = match(info.line);
        if (result == null) return null;

        return result.expression.compile(info, result.groups);
    }

}