package org.bmSpring.servlet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class Cookie {
    private String key;
    private String value;
    private String expireTime;
    private String path;
    private boolean httpOnly;

    public StringBuilder cookieString(StringBuilder builder) {
        builder.append(key).append("=").append(value);

        // 만료 시간이 있으면 추가
        if (expireTime != null && !expireTime.isEmpty()) {
            builder.append("; Expires=").append(expireTime);
        }

        // 경로가 있으면 추가
        if (path != null && !path.isEmpty()) {
            builder.append("; Path=").append(path);
        }

        // HTTP 전용 여부가 true이면 추가
        if (httpOnly) {
            builder.append("; HttpOnly; ");
        }

        return builder;
    }

    public static CookieBuilder builder() {
        return new CookieBuilder();
    }

    public static final class CookieBuilder {
        private String key;
        private String value;
        private String expireTime;
        private String path;
        private boolean httpOnly;

        public CookieBuilder key(String key) {
            this.key = key;
            return this;
        }

        public CookieBuilder value(String value) {
            this.value = value;
            return this;
        }

        public CookieBuilder expireTime(LocalDateTime expireTime) {
            this.expireTime = expireTime.format(DateTimeFormatter.RFC_1123_DATE_TIME);
            return this;
        }

        public CookieBuilder path(String path) {
            this.path = path;
            return this;
        }

        public CookieBuilder httpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
            return this;
        }

        public Cookie build() {
            return new Cookie(key, value, expireTime, path, httpOnly);
        }
    }
}
