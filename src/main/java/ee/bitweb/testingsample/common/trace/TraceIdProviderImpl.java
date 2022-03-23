package ee.bitweb.testingsample.common.trace;

import javax.servlet.http.HttpServletRequest;

import ee.bitweb.testingsample.common.util.StringUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor//(access = AccessLevel.PACKAGE)
public class TraceIdProviderImpl implements TraceIdProvider {

    private final TraceIdCustomizer customizer;

    public String generate(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(customizer.getHeaderName());
        String trace = assemble();

        if (StringUtils.hasText(header)) {
            return header + customizer.getDelimiter() + trace;
        }

        return trace;
    }

    private String assemble() {
        if (customizer.getPrefix() == null) {
            return StringUtil.random(customizer.getLength());
        }

        return customizer.getPrefix() + StringUtil.random(customizer.getLength() - customizer.getPrefix().length());
    }
}
