package com.autonomous.util;

public final class KeywordGenerator {

    public static String genStringQuerySearchAddress(String keyword, Long minPrice, Long maxPrice, String[] types, Integer numberOfBedrooms) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(genSolrQuerySearchAddress(keyword));
        appendPriceAndType(minPrice, maxPrice, types, stringBuilder);
        appendNumberOfBedrooms(numberOfBedrooms, stringBuilder);
        return stringBuilder.toString();
    }

    public static String genStringQuerySearchAddressAndTitle(String keyword, Long minPrice, Long maxPrice, String[] types, Integer numberOfBedrooms) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(genSolrQuerySearchAddressAndTitle(keyword));
        appendPriceAndType(minPrice, maxPrice, types, stringBuilder);
        appendNumberOfBedrooms(numberOfBedrooms, stringBuilder);
        return stringBuilder.toString();
    }

    private static void appendNumberOfBedrooms(Integer numberOfBedrooms, StringBuilder stringBuilder) {
        if (numberOfBedrooms != null) {
            stringBuilder.append(" AND numberOfBedrooms:").append(numberOfBedrooms);
        }
    }


    private static void appendPriceAndType(Long minPrice, Long maxPrice, String[] types, StringBuilder stringBuilder) {
        if (maxPrice != null || minPrice != null) {
            stringBuilder.append(" AND price:[");
            if (minPrice != null)
                stringBuilder.append(minPrice);
            else
                stringBuilder.append("*");
            stringBuilder.append(" TO ");
            if (maxPrice != null)
                stringBuilder.append(maxPrice);
            else
                stringBuilder.append("*");
            stringBuilder.append("]");
        }
        if (types != null) {
            stringBuilder.append(" AND type:(");
            for (int i = 0; i < types.length; i ++) {
                stringBuilder.append(types[i] + " ");
            }
            stringBuilder.append(")");
        }
    }
    private static String genSolrQuerySearchAddress(String keyword) {
        StringBuilder builder = new StringBuilder();
        builder.append("searchData:*").append(toSearchKeyword(keyword)).append("*");
        return builder.toString();
    }

    private static String genSolrQuerySearchAddressAndTitle(String keyword) {
        StringBuilder builder = new StringBuilder();
        builder.append("addressAndTitle:*").append(toSearchKeyword(keyword)).append("*");
        return builder.toString();
    }

    private static String toSearchKeyword(String keyword) {
        return keyword.replace(" ", "-").toLowerCase();
    }
}
