<%@ page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="v" uri="http://vdab.be/tags" %>

<!DOCTYPE HTML>
<html>

<head>
	<v:head title="Aantal docenten per wedde" />
</head>

<style>
td:first-child {
padding-right:0.5em;
text-align:right;
}
td:nth-child(2) div {
background: linear-gradient(to right, wheat, orange);
padding-left: 0.5em;
}
</style>

<body>
	<v:menu/>
	
	<h1>Aantal docenten per wedde</h1>

	<table>
		<thead>
			<tr><th>Wedde</th><th>Aantal docenten</th></tr>
		</thead>
		<tbody>
		<c:forEach items='${weddesEnAantallen}' var='weddeEnAantal'>
			<tr>
				<td><fmt:formatNumber value='${weddeEnAantal.wedde}' minFractionDigits='2'
					maxFractionDigits='2'/></td>
				<td><div style='width:${weddeEnAantal.aantal}em'>
			${weddeEnAantal.aantal}</div></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>


</html>