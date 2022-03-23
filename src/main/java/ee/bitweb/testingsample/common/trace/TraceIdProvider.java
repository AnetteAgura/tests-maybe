package ee.bitweb.testingsample.common.trace;

import javax.servlet.http.HttpServletRequest;

public interface TraceIdProvider {

    String generate(HttpServletRequest request);
}
