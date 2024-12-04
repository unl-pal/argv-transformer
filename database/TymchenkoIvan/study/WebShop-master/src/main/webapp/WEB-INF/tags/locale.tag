<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tags/tag.tld" prefix="util" %>

<c:set var="localeCode" value="${pageContext.request.locale}" />
${localeCode}

		<a href="${ util:changeLang(url, 'En') }">En</a> | 
		<a href="${ util:changeLang(url, 'Ru') }">Ru</a>