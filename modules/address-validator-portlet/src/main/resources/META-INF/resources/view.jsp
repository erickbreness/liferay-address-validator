<%-- 
/**
 * Copyright (C) 2005-2017 Rivet Logic Corporation.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
--%>
<%@ include file="/init.jsp" %>

<liferay-ui:error key="addressvalidator.error.callback-functions-required" message="addressvalidator.error.callback-functions-required"/>

<c:choose>
    <c:when test="${fn:length(matchedAddresses) gt 0}">
        <%@ include file="/list_addresses.jspf"%>
    </c:when>
    <c:otherwise>
        <liferay-ui:message key="addressvalidator.message.no-matching-address" />
    </c:otherwise>
</c:choose>

