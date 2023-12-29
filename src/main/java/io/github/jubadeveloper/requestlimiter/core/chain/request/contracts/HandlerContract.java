package io.github.jubadeveloper.requestlimiter.core.chain.request.contracts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerContract {
    boolean execute (HttpServletRequest httpServletRequest,  HttpServletResponse response);
}
