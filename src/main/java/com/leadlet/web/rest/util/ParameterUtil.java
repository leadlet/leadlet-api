package com.leadlet.web.rest.util;

import com.leadlet.repository.util.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for HTTP headers creation.
 */
public final class ParameterUtil {

    private static final Logger log = LoggerFactory.getLogger(ParameterUtil.class);

    private ParameterUtil() {
    }

    public static List<SearchCriteria> createCriterias(String filter) {

        // TODO check here
        filter = filter.replace("%20"," ");
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if (filter != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([\\w\\s]+?),");
            Matcher matcher = pattern.matcher(filter + ",");
            while (matcher.find()) {
                criteriaList.add(new SearchCriteria(matcher.group(1),
                    matcher.group(2), matcher.group(3)));
            }
        }

        return criteriaList;
    }

}
