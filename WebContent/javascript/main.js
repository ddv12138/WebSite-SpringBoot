;var ddvudo = {};

ddvudo.getContextPath = function () {
    var contextPath = document.location.pathname;
    var index = contextPath.substr(1).indexOf("/");
    contextPath = contextPath.substr(0, index + 1) + "/";
    if (contextPath == "/") contextPath = "/";
    delete index;
    return window.location.origin + contextPath;
}