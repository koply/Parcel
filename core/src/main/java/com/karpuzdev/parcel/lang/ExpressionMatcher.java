package com.karpuzdev.parcel.lang;

import com.karpuzdev.parcel.lang.expressions.TileExpression;
import com.karpuzdev.parcel.lang.expressions.helpers.CompileResult;
import com.karpuzdev.parcel.lang.expressions.helpers.MatchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ExpressionMatcher {

    private ExpressionMatcher() { }

    // Regex -> TileExpression
    private static final Map<Pattern, TileExpression> matcher = new HashMap<>();

    static void registerExpression(TileExpression exp) {
        for (String regex : exp.getMatchers()) {
            matcher.put(Pattern.compile(regex), exp);
        }
    }

    static MatchResult match(String line) {
        for (Map.Entry<Pattern, TileExpression> entry : matcher.entrySet()) {
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

    static CompileResult compile(String line, int lineNumber) {
        MatchResult result = match(line);
        if (result == null) return null;

        return result.expression.compile(line, lineNumber, result.groups);
    }

}