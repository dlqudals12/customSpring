package org.bmSpring.servlet.enums;

public enum MediaType {
    ALL_VALUE("*/*"),
    APPLICATION_ATOM_XML_VALUE("application/atom+xml"),
    APPLICATION_CBOR_VALUE("application/cbor"),
    APPLICATION_FORM_URLENCODED_VALUE("application/x-www-form-urlencoded"),
    APPLICATION_GRAPHQL_VALUE("application/graphql+json"),
    APPLICATION_JSON_VALUE("application/json"),
    APPLICATION_OCTET_STREAM_VALUE("application/octet-stream"),
    APPLICATION_PDF_VALUE("application/pdf"),
    PPLICATION_PROBLEM_JSON_VALUE("application/problem+json"),
    APPLICATION_PROBLEM_XML_VALUE("application/problem+xml"),
    APPLICATION_RSS_XML_VALUE("application/rss+xml"),
    APPLICATION_NDJSON_VALUE("application/x-ndjson"),
    APPLICATION_XHTML_XML_VALUE("application/xhtml+xml"),
    APPLICATION_XML_VALUE("application/xml"),
    IMAGE_GIF_VALUE("image/gif"),
    IMAGE_JPEG_VALUE("image/jpeg"),
    IMAGE_PNG_VALUE("image/png"),
    MULTIPART_FORM_DATA_VALUE("multipart/form-data"),
    MULTIPART_MIXED_VALUE("multipart/mixed"),
    MULTIPART_RELATED_VALUE("multipart/related"),
    TEXT_EVENT_STREAM_VALUE("text/event-stream"),
    TEXT_HTML_VALUE("text/html"),
    TEXT_MARKDOWN_VALUE("text/markdown"),
    TEXT_PLAIN_VALUE("text/plain"),
    TEXT_XML_VALUE("text/xml"),
    PARAM_QUALITY_FACTOR("q");
    private final String contentName;

    MediaType(String contentName) {
        this.contentName = contentName;
    }

    public String getContentName() {
        return contentName;
    }
}
