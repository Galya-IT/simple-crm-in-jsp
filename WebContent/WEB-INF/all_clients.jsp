<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>All Clients</title>
	
	<link rel="stylesheet" href="./view/css/general.css" media="screen" type="text/css" />
	<script type="text/javascript" src="./view/scripts/js/lib/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="./view/scripts/js/lib/jquery.tablesorter.min.js"></script>
</head>

<body>
	<c:import url="./include/top.jsp"></c:import>

	<div class="wrapper">
		<c:if test="${not empty param.error}">
			<div class="error">Something went wrong. Please try again
				later.</div>
		</c:if>
		
		<c:if test="${clientsList.size() == 0}">
			<h1>No clients.</h1>
		</c:if>

		<c:if test="${clientsList.size() != 0}">
			<table id="clients">
				<caption>All Clients</caption>
				<thead>
					<tr>
						<th>Logo</th>
						<th class="sortable headerSortDown">Company Name</th>
						<th class="sortable headerSortDown">Office Location</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${clientsList}" var="client">
						<c:if test="${!client.checkIfDeleted()}">
							<tr data-id="${client.getId()}">
								<td>
								<c:choose>
									<c:when test="${client.getLogoFileName() != null}">
										<img src="Image?name=${client.getLogoFileName()}" alt="${client.getLogoFileName()}" />
									</c:when>
									<c:otherwise>
										No logo.
							  		</c:otherwise>
								</c:choose>
								<td><c:choose>
										<c:when test="${client.getName() != null}">
											<a href="./ClientPreview?clientId=${client.getId()}">${client.getName()}</a>
										</c:when>
										<c:otherwise>
								Error occured!
							  </c:otherwise>
									</c:choose></td>
								<td>
								<c:choose>
									<c:when test="${client.getLocation() != null}">
										${client.getLocation()}
							  		</c:when>
									<c:otherwise>
										No location.
							  		</c:otherwise>
								</c:choose></td>
								<td><a class="edit" data-id="${client.getId()}">Edit</a></td>
								<td><a class="delete" data-id="${client.getId()}">Delete</a></td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	
	<script src="./view/scripts/js/database-operations-requester.js"></script>
	<script>
		$(function(){
		  $('#clients').tablesorter(); 
		});
	</script>
</body>
</html>